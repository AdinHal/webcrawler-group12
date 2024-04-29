import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import crawler.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;


public class PageParserTest {
    private static PageParser pageParser;
    private static Document document;
    private static String dummyHTML;

    @BeforeClass
    public static void testSetUp() throws IOException {
        dummyHTML = "<html><body><h1>H1</h1><h2>H2</h2><h3>H3</h3></body></html>";
        document = Jsoup.parse(dummyHTML);
        pageParser = new PageParser(null);
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
    public void testPrintSummary() throws IOException{

    }

    @After
    public void testCleanUp(){
        pageParser = null;
        document = null;
        dummyHTML = null;
    }
}
