import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import crawler.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    public void testSuccessfulCrawl() {
        when(validator.isLinkReachable(Mockito.anyString())).thenReturn(true);
        when(pageParser.getHeaders(Mockito.anyString(),Mockito.anyInt(),Mockito.anyBoolean())).thenReturn("MockHeader");

        crawler.startCrawling(config.getCrawlUrl(),0);

        Mockito.verify(pageParser).getHeaders(config.getCrawlUrl(),0,false);
    }

    @Test
    public void testExceedsDepthFailedCrawl(){
        String testUrl = "http://httpbin.org/html";
        Config testConfig = Mockito.spy(config);

        when(testConfig.getCrawlDepth()).thenReturn(3);
        when(testConfig.getCrawlAdditionalLinksDepth()).thenReturn(2);

        crawler.startCrawling(testUrl, 4);

        assertTrue("Should be empty since no links are visited",crawler.visitedLinks.isEmpty());
    }

    @Test
    public void testAlreadyVisitedURL(){
        String testUrl = "http://httpbin.org/html";

        Map<String, String> visitedLinks = new HashMap<>();
        visitedLinks.put(testUrl, "Visited");

        crawler.visitedLinks = visitedLinks;

        crawler.startCrawling(testUrl, 1);

        assertTrue("No new links should be added",visitedLinks.containsKey(testUrl) && visitedLinks.size() == 1);
    }


    @AfterClass
    public static void testCleanUp() {

    }
}
