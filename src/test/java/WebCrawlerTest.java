import crawler.URLHandler;
import crawler.WebCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

class WebCrawlerTest {

    private WebCrawler webCrawler;
    private List<String> visitedLinks;
    private String urlToCrawl;

    @BeforeEach
    public void setUp() {
        urlToCrawl = "http://books.toscrape.com";
        visitedLinks = new ArrayList<>();
        webCrawler = new WebCrawler(urlToCrawl, 3, visitedLinks, true);
    }

    @Test
    public void testRun(){
        WebCrawler webCrawlerSpy = Mockito.spy(webCrawler);

        Document mockDocument = mock(Document.class);
        Element mockElement = mock(Element.class);

        Elements mockElements = new Elements();
        mockElements.add(mockElement);

        when(mockDocument.select("a[href]")).thenReturn(mockElements);
        when(mockElement.absUrl("href")).thenReturn("https://books.toscrape.com/catalogue/a-light-in-the-attic_1000/index.html");

        mockStatic(URLHandler.class);

        when(URLHandler.requestLinkAccess(anyString(),anyInt(),anyInt(),anyList(),anyBoolean())).thenReturn(mockDocument);
        when(URLHandler.isDomainAllowed(anyString())).thenReturn(true);

        webCrawlerSpy.run();

        assertTrue(visitedLinks.contains(urlToCrawl));
    }

    @Test
    public void testCrawlExceedsMaxDepth(){
        Document mockDocument = mock(Document.class);

        mockStatic(URLHandler.class);
        when(URLHandler.requestLinkAccess(anyString(),anyInt(),anyInt(),anyList(),anyBoolean())).thenReturn(mockDocument);

        WebCrawler.crawl(urlToCrawl,4,3,visitedLinks,true);

        assertFalse(visitedLinks.contains(urlToCrawl));
    }

    @Test
    public void testStartExecutorService(){
        String[] urls = {"https://books.toscrape.com/", "https://quotes.toscrape.com/"};

        ExecutorService mockExecutorService = mock(ExecutorService.class);
        mockStatic(Executors.class);
        when(Executors.newFixedThreadPool(anyInt())).thenReturn(mockExecutorService);

        WebCrawler.startExecutorService(urls,3);

        verify(mockExecutorService,times(urls.length)).submit(any(Runnable.class));
    }

    @Test
    public void testStopExecutorService() throws InterruptedException {
        ExecutorService mockExecutorService = mock(ExecutorService.class);
        when(mockExecutorService.awaitTermination(anyLong(),any(TimeUnit.class))).thenReturn(true);

        WebCrawler.endExecutorService(mockExecutorService);

        verify(mockExecutorService,times(1)).shutdown();
    }
}
