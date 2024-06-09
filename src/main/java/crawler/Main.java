package crawler;

import java.util.*;

public class Main {

    static Set<String> allowedDomains = new HashSet<>();

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
}
