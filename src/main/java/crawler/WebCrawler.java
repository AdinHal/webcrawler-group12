package crawler;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class WebCrawler {

    private static final int MAX_ADDITIONAL_DEPTH = 3;
    private static HashSet<String> allowedDomains = new HashSet<>();

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
            FileWriterSingleton.getInstance().close();
            System.out.println("File written successfully.\n");
        }
    }

    private static boolean isDomainAllowed(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            for (String allowedDomain : allowedDomains) {
                if (domain != null && domain.contains(allowedDomain)) {
                    return true;
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void crawl(String urlToCrawl, int currentDepth, int maxDepth, ArrayList<String> visited, boolean isInitialPage) {
        if (currentDepth <= maxDepth) {
            Document document = requestLinkAccess(urlToCrawl, currentDepth, visited, isInitialPage);

            if (document != null) {
                isInitialPage = false;
                for (Element link : document.select("a[href]")) {
                    String hrefValue = link.absUrl("href");
                    if (!visited.contains(hrefValue) && currentDepth < MAX_ADDITIONAL_DEPTH && isDomainAllowed(hrefValue)) {
                        crawl(hrefValue, currentDepth + 1, maxDepth, visited, isInitialPage);
                    }
                }
            }
        }
    }


    private static void printHeaders(Document document, String prefix, boolean isInitialPage) {
        FileWriterSingleton fileWriter = FileWriterSingleton.getInstance();
        String summaryHeaderTags = "#";

        Elements h1Elements = document.select("h1");
        for (Element element : h1Elements) {
            fileWriter.write(summaryHeaderTags + prefix + element.text() + "\n");
        }

        Elements h2Elements = document.select("h2");
        for (Element element : h2Elements) {
            fileWriter.write(summaryHeaderTags.repeat(2) + prefix + element.text() + "\n");
        }

        Elements h3Elements = document.select("h3");
        for (Element element : h3Elements) {
            fileWriter.write(summaryHeaderTags.repeat(3) + prefix + element.text() + "\n");
        }

        Elements h4Elements = document.select("h4");
        for (Element element : h4Elements) {
            fileWriter.write(summaryHeaderTags.repeat(4) + prefix + element.text() + "\n");
        }
    }


    private static Document requestLinkAccess(String urlToCrawl, int depth, ArrayList<String> visited, boolean isInitialPage) {
        FileWriterSingleton writer = FileWriterSingleton.getInstance();
        try {
            Connection connection = Jsoup.connect(urlToCrawl);
            Document document = connection.get();

            if (connection.response().statusCode() == 200) {
                if (isInitialPage) {
                    writer.write("input: <a>" + urlToCrawl + "</a>\n");
                    writer.write("depth: " + depth + "\n");
                    writer.write("summary:\n");
                    printHeaders(document, "", isInitialPage);
                    visited.add(urlToCrawl);
                } else {
                    writer.write("<br> --> link to <a>" + urlToCrawl + "</a>\n");
                    printHeaders(document, " --> ", isInitialPage);
                    writer.write("<br>\n");
                    visited.add(urlToCrawl);
                }
                return document;
            }
        } catch (IOException e) {
            writer.write("<br>--> broken link <a>" + urlToCrawl + "</a>\n");
        }
        return null;
    }

}
