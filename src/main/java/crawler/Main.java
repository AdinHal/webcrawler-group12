package crawler;

import java.util.*;

public class Main {
    static Set<String> allowedDomains = new HashSet<>();
    public static int crawlDepth = 0;
    public static String[] urlsToCrawl = new String[10];

    public static void main(String[] args) {
        InputValidator IV = new InputValidator();

        System.out.print("Enter the URLs to be crawled (comma separated, maximum of 10 URLs): ");
        IV.splitURLs();

        System.out.print("Enter the crawling depth: ");
        IV.inputDepth();

        System.out.print("Enter allowed domains (comma separated, press Enter to skip): ");
        IV.inputDomains();

        System.out.println("\nTraversing site and writing file...\n");
        WebCrawler.startExecutorService(urlsToCrawl, crawlDepth);

        FileWriterSingleton.getInstance().close();
        System.out.println("File written successfully.\n");
    }
}