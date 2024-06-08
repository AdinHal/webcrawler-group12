package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static crawler.URLHandler.requestLinkAccess;

public class WebCrawler {

    private static final int MAX_ADDITIONAL_DEPTH = 3;
    static final HashSet<String> allowedDomains = new HashSet<>();

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            //System.out.print("Enter the URL to be crawled: ");
            //String url = scanner.nextLine();

           // System.out.print("Enter the crawling depth: ");
            //int depth = scanner.nextInt();
            // System.out.println("Enter the URL to be crawled: ");
            String urlToCrawl = "https://books.toscrape.com/index.html";
            // System.out.println("Enter the crawling depth: ");
            int crawlDepth = 3;

            System.out.print("Enter allowed domains (comma separated): ");
            String domainsInput = scanner.nextLine();
            String[] domains = domainsInput.split(",");
            for (String domain : domains) {
                allowedDomains.add(domain.trim());
            }

            ArrayList<String> visited = new ArrayList<>();
            System.out.println("\nTraversing site...\n");
            System.out.println("Writing File...\n");
            crawl(urlToCrawl,0, crawlDepth, visited, true);
        } finally {
            FileWriter.getInstance().close();
            System.out.println("File written successfully.\n");
        }
    }

    private static void crawl(String urlToCrawl, int currentDepth, int maxDepth, ArrayList<String> visited, boolean isInitialPage) {
        if (currentDepth <= maxDepth) {
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
}
