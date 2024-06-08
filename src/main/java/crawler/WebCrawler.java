package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import static crawler.URLHandler.requestLinkAccess;

public class WebCrawler implements Runnable {

    private final String urlToCrawl;
    private final int maxDepth;
    private final List<String> visited;
    private final boolean isInitialPage;

    private static final int MAX_ADDITIONAL_DEPTH = 3;
    static final HashSet<String> allowedDomains = new HashSet<>();

    public WebCrawler(String urlToCrawl, int maxDepth, List<String> visited, boolean isInitialPage) {
        this.urlToCrawl = urlToCrawl;
        this.maxDepth = maxDepth;
        this.visited = visited;
        this.isInitialPage = isInitialPage;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the URLs to be crawled (comma separated): ");
            String urlsInput = scanner.nextLine();
            String[] urls = urlsInput.split(",");

            System.out.print("Enter the crawling depth: ");
            int crawlDepth = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter allowed domains (comma separated): ");
            String domainsInput = scanner.nextLine();
            String[] domains = domainsInput.split(",");
            for (String domain : domains) {
                allowedDomains.add(domain.trim());
            }

            System.out.println("\nTraversing site and writing file...\n");

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
            System.out.println("File written successfully.\n");
        }
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
}
