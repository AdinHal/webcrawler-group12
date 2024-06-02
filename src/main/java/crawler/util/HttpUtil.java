package crawler.util;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpUtil {
    public static String getDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost();
        } catch (URISyntaxException e) {
            System.err.println("Invalid URL: " + url);
            return null;
        }
    }
}
