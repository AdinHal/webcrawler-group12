import crawler.MarkdownGenerator;
import crawler.PageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class PageParserTest {
    private static PageParser pageParser;
    private static Document document;
    private static String dummyHTML;
    private static MarkdownGenerator mockMarkdownGenerator;

    @Before
    public void testSetUp() {
        dummyHTML = "<html><body><h1>H1</h1><h2>H2</h2><h3>H3</h3></body></html>";
        document = Jsoup.parse(dummyHTML);
        pageParser = new PageParser(null);

        mockMarkdownGenerator= Mockito.mock(MarkdownGenerator.class);
    }

    @Test
    public void testRoutePrinterSuccess(){
        assertEquals("# ",pageParser.routePrinter(document,0));
        assertEquals("## ",pageParser.routePrinter(document,1));
        assertEquals("### ",pageParser.routePrinter(document,2));
    }

    @Test
    public void testRoutePrinterFail(){
        assertEquals("X ", pageParser.routePrinter(document,5));
    }

    @Test
    public void testRoutePrinterWithNullDoc(){
        document = null;
        assertThrows(NullPointerException.class, ()-> {pageParser.routePrinter(document, 0);});
    }

    @Test
    public void testRoutePrinterWithBadHTML(){
        String badHTML = "<html><body><a>H1<p>H2</body></html>";
        document = Jsoup.parse(badHTML);

        assertEquals("X ", pageParser.routePrinter(document,0));
    }

    @Test
    public void testPrintSummarySuccess(){
        PageParser pParser = new PageParser(mockMarkdownGenerator);
        /* https://stackoverflow.com/a/29612190/13667327
         A spy is needed when an argument passed to when is not a mock.*/
        PageParser parserSpy = spy(pParser);

        String expectedGetHeadersResult = "Header\nHeaderToo\n";
        Mockito.doReturn(expectedGetHeadersResult).when(parserSpy).getHeaders(anyString(), anyInt(), anyBoolean());

        String testURL = "http://httpbin.org/html";
        int testDepth = 0;
        parserSpy.printSummary(testURL,testDepth);

        verify(mockMarkdownGenerator).writeEntries("input: <a>" + testURL + "</a> ");
        verify(mockMarkdownGenerator).writeEntries("<br>depth:" + testDepth);
        verify(mockMarkdownGenerator).writeEntries("<br>summary:\n");
        verify(mockMarkdownGenerator).writeEntries(expectedGetHeadersResult + "\n");
    }

    @Test
    public void testPrintSummaryWithInvalidURL(){
        PageParser pParser = new PageParser(mockMarkdownGenerator);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, ()-> {pParser.printSummary(null,0);});
        assertEquals("Must supply a valid URL", thrown.getMessage());
    }


    @Test
    public void testGetHeadersSuccess(){
        String url = "http://httpbin.org/html";

        String getHeadersResponse = pageParser.getHeaders(url,0,false);
        assertEquals("# -> Herman Melville - Moby-Dick\n",getHeadersResponse);
    }

    @Test
    public void testGetHeadersThrowsWithInvalidURL(){
        IllegalArgumentException nullThrown = assertThrows(IllegalArgumentException.class, ()-> {pageParser.getHeaders(null,0,false);});
        assertEquals("URL may not be null.", nullThrown.getMessage());

        IllegalArgumentException emptyThrown = assertThrows(IllegalArgumentException.class, ()-> {pageParser.getHeaders("",0,false);});
        assertEquals("URL may not be empty.", emptyThrown.getMessage());

        String malformedURL = "http://a b.com";
        IllegalArgumentException urlNotReachableThrown = assertThrows(IllegalArgumentException.class, ()-> {pageParser.getHeaders(malformedURL,0,false);});
        assertEquals(urlNotReachableThrown.getMessage(), "URL is malformed: " + malformedURL);
    }

    @After
    public void testCleanUp(){
        pageParser = null;
        document = null;
        dummyHTML = null;
    }
}
