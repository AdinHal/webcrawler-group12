package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLHandler {
    public Document connectToURL(String URL){
        try {
            return Jsoup.connect(URL).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Elements extractFromURL(Document document, String tag) {
        return document.select(tag);
    }

    public boolean isLinkReachable(String link) {
        try {
            URL url = new URI(link).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException | URISyntaxException e) {
            return false;
        }
    }

    public void checkURLExceptions(String URL) {
        if (URL == null) {
            throw new IllegalArgumentException("URL may not be null.");
        }
        if(URL.isEmpty()){
            throw new IllegalArgumentException("URL may not be empty.");
        }
    }
}