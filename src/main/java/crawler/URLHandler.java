package crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class URLHandler {
    public static FileWriterSingleton writer = FileWriterSingleton.getInstance();
    public static Document document;

    public Elements extractFromURL(Document document, String tag) {
        return document.select(tag);
    }

    public static Optional<Document> requestLinkAccess(String urlToCrawl, int depth, int maxDepth, List<String> visited, boolean isInitialPage) {
        try {
            Connection connection = Jsoup.connect(urlToCrawl);
            document = connection.get();

            if (connection.response().statusCode() == 200) {
                writeInfoToFile(urlToCrawl, depth, maxDepth, visited, isInitialPage);
                return Optional.of(document);
            }
        } catch (IOException e) {
            writer.write("<br> --> broken link <a>" + urlToCrawl + "</a>\n");
        } catch (IllegalArgumentException IAE) {
            throw new IllegalArgumentException("One or more URLs are malformed or null.");
        }
        return Optional.ofNullable(document);
    }

    public static boolean isDomainAllowed(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            for (String allowedDomain : Main.allowedDomains) {
                if (domain != null && domain.contains(allowedDomain)) {
                    return true;
                }
            }
        } catch (URISyntaxException e) {
            System.out.print("One or more invalid domains have been specified.");
        }
        return false;
    }

    public static void writeInfoToFile(String urlToCrawl, int depth, int maxDepth, List<String> visited, boolean isInitialPage){
        String depthDash = "-";

        if (isInitialPage) {
            writer.write("input: <a>" + urlToCrawl + "</a>\n" +
                    "depth: " + maxDepth + "\n" +
                    "summary:\n");
            FileWriterSingleton.printHeaders(document, " ");
            visited.add(urlToCrawl);
        } else {
            writer.write("<br> --> link to <a>" + urlToCrawl + "</a>\n");

            FileWriterSingleton.printHeaders(document, " " + depthDash.repeat(depth) + " > ");
            writer.write("<br>\n");
            visited.add(urlToCrawl);
        }
    }
}