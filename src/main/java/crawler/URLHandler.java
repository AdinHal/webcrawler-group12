package crawler;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URI;
import java.util.List;
import java.io.IOException;
import java.net.URISyntaxException;


public class URLHandler {

    public Elements extractFromURL(Document document, String tag) {
        return document.select(tag);
    }

    public static Document requestLinkAccess(String urlToCrawl, int depth, List<String> visited, boolean isInitialPage) {
        FileWriterSingleton writer = FileWriterSingleton.getInstance();
        try {
            Connection connection = Jsoup.connect(urlToCrawl);
            Document document = connection.get();

            String depthDash = "-";

            if (connection.response().statusCode() == 200) {
                if (isInitialPage) {
                    writer.write("input: <a>" + urlToCrawl + "</a>\n");
                    writer.write("depth: " + depth + "\n");
                    writer.write("summary:\n");
                    FileWriterSingleton.printHeaders(document, " ");
                    visited.add(urlToCrawl);
                } else {
                    writer.write("<br> --> link to <a>" + urlToCrawl + "</a>\n");

                    FileWriterSingleton.printHeaders(document, " " + depthDash.repeat(depth) + " > ");
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

    static boolean isDomainAllowed(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            for (String allowedDomain : Main.allowedDomains) {
                if (domain != null && domain.contains(allowedDomain)) {
                    return true;
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}