import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import crawler.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class CrawlerServiceTest {
    private static Config config;
    private static LinkValidator validator;
    private static PageParser pageParser;
    private static CrawlerService crawler;
    private static MarkdownGenerator markdownGenerator;

    @BeforeClass
    public static void testSetUp() throws IOException {
        config = new Config("https://webscraper.io/test-sites/e-commerce/allinone",2,1, Arrays.asList("allinone"));
        validator = Mockito.mock(LinkValidator.class);
        pageParser = Mockito.mock(PageParser.class);
        markdownGenerator = Mockito.mock(MarkdownGenerator.class);

        Mockito.doNothing().when(markdownGenerator).init();
        Mockito.doNothing().when(markdownGenerator).writeEntries(Mockito.anyString());
        Mockito.doNothing().when(markdownGenerator).close();

        crawler = new CrawlerService(config,validator,pageParser,markdownGenerator);
    }

    @Test
    public void testSuccessfulCrawl() throws IOException {
        Mockito.when(validator.isLinkReachable(Mockito.anyString())).thenReturn(true);
        Mockito.when(pageParser.getHeaders(Mockito.anyString(),Mockito.anyInt(),Mockito.anyBoolean())).thenReturn("MockHeader");

        crawler.startCrawling(config.getCrawlUrl(),0);

        Mockito.verify(pageParser).getHeaders(config.getCrawlUrl(),0,false);
    }

    @Test
    public void testFailedCrawl(){
        Mockito.when(validator.isLinkReachable(Mockito.anyString())).thenReturn(false);
        Mockito.when(pageParser.getHeaders(Mockito.anyString(),Mockito.anyInt(),Mockito.anyBoolean())).thenThrow(new IOException("Failed to fetch due to network error"));

        Exception exception = assertThrows(IOException.class, ()-> {crawler.startCrawling(config.getCrawlUrl(), 0);});

        Mockito.verify(pageParser, Mockito.never()).getHeaders(Mockito.anyString(), Mockito.anyInt(), Mockito.anyBoolean());

        assertEquals("Failed to fetch due to network error", exception.getMessage());
    }


    @AfterClass
    public static void testCleanUp() {

    }
}
