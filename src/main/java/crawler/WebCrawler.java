package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import static crawler.URLHandler.requestLinkAccess;

public class WebCrawler implements Runnable {

    private final String urlToCrawl;
    private final int maxDepth;
    private final List<String> visited;
    private final boolean isInitialPage;

    private static final int MAX_ADDITIONAL_DEPTH = 3;


    public WebCrawler(String urlToCrawl, int maxDepth, List<String> visited, boolean isInitialPage) {
        this.urlToCrawl = urlToCrawl;
        this.maxDepth = maxDepth;
        this.visited = visited;
        this.isInitialPage = isInitialPage;
    }

    private static void crawl(String urlToCrawl, int currentDepth, int maxDepth, List<String> visited, boolean isInitialPage) {
        if (currentDepth <= maxDepth) {
            System.out.println(Thread.currentThread().getName() + " is crawling URL: " + urlToCrawl + " at depth: " + currentDepth);
            Document document = requestLinkAccess(urlToCrawl, currentDepth, visited, isInitialPage);
            if (document != null) {
                for (Element link : document.select("a[href]")) {
                    String hrefValue = link.absUrl("href");
                    if (!visited.contains(hrefValue) && currentDepth < MAX_ADDITIONAL_DEPTH && URLHandler.isDomainAllowed(hrefValue)) {
                        crawl(hrefValue, currentDepth + 1, maxDepth, visited, false);
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Starting crawl for URL: " + urlToCrawl);
        crawl(urlToCrawl, 0, maxDepth, visited, isInitialPage);
        System.out.println("Finished crawl for URL: " + urlToCrawl);
    }

    public static void startCrawling(String[] urls, int crawlDepth){
        try {
            List<Thread> threads = new ArrayList<>();
            for (String url : urls) {
                List<String> visited = new ArrayList<>();
                WebCrawler webCrawler = new WebCrawler(url.trim(), crawlDepth, visited, true);
                Thread thread = new Thread(webCrawler);
                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            FileWriterSingleton.getInstance().close();
        } finally {
            FileWriterSingleton.getInstance().close();
            System.out.println("\nFile written successfully.\n");
        }
    }
}
