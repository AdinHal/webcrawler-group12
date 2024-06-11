package crawler;

import java.util.*;

public class Main {
    static Set<String> allowedDomains = new HashSet<>();
    public static int crawlDepth = 0;
    public static String urlsToCrawl;

    public static void main(String[] args) {
        try {
            InputValidator IV = new InputValidator();

            System.out.print("Enter the URLs to be crawled (comma separated): ");
            //THIS IS FOR TESTING!
            String urlsInput = "http://books.toscrape.com/";
            //String urlsInput = IV.inputURLs();

            String[] urls = urlsInput.split(",");

            System.out.print("Enter the crawling depth: ");
            IV.inputDepth();

            System.out.print("Enter allowed domains (comma separated, press Enter to skip): ");
            IV.inputDomains();

            System.out.println("\nTraversing site and writing file...\n");
            WebCrawler.startExecutorService(urls, crawlDepth);
        } finally {
            FileWriterSingleton.getInstance().close();
            System.out.println("File written successfully.\n");
        }
    }
}