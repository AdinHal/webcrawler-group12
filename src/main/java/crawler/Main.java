package crawler;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static HashSet<String> allowedDomains = new HashSet<>();
    public static int crawlDepth;
    public static String urlsToCrawl;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the URLs to be crawled (comma separated): ");

        InputValidator.inputURLs(scanner);
        String[] urls = urlsToCrawl.split(",\\s*");

        System.out.print("Enter desired crawl depth: ");

        InputValidator.inputDepth(scanner);

        System.out.print("Enter allowed domains (comma separated): ");
        String domainsInput = scanner.nextLine();
        String[] domains = domainsInput.split(",");
        for (String domain : domains) {
            allowedDomains.add(domain.trim());
        }

        System.out.println("\nTraversing site and writing file...\n");

        WebCrawler.startCrawling(urls, crawlDepth);
    }
}