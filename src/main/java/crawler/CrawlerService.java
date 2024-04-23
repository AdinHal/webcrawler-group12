package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;

public class CrawlerService {

    private Config config;
    private PageParser pageParser;
    private HashSet<String> links;
    private final int maxdepth = 3;

    public CrawlerService(Config config, PageParser pageParser){
        this.config = config;
        this.pageParser = pageParser;
        this.links = new HashSet<String>();
    }


    private void saveUrl(String url) throws IOException {
        // the assignment specification does not say if we have to write it inside the .md file or in a separate text file - path can be changed
        String filePath = "crawledURLs.txt";

        try(FileWriter fileWriter = new FileWriter(filePath,true)){
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(url);
        }catch (IOException ioException){
            System.err.println("URL could not be saved: "+url);
            ioException.printStackTrace();
        }

    }

    public void getPageLinks(String URL, int depth) {

        if (depth > maxdepth || links.contains(URL)) {
            return;
        }

        String userDomain = config.getCrawlDomains().get(0);

        System.out.println("Depth: " + depth + " - " + URL);
        try {
            links.add(URL);

            Document document = Jsoup.connect(URL).get();

            Elements linksOnPage = document.select("a[href]");

            for (Element page : linksOnPage) {
                String absUrl = page.absUrl("href");
                try {
                    URI uri = new URI(absUrl);
                    String domain = uri.getHost();

                    if(domain != null && domain.equals(userDomain)){
                        if (isLinkReachable(absUrl)) {
                            pageParser.getH1Headers(URL, config.getCrawlLang());
                            getPageLinks(absUrl, depth+ 1);
                        }
                        else{
                            System.out.println("Broken link: " + absUrl);
                        }

                    }
                } catch (URISyntaxException URI) {
                    URI.printStackTrace();
                }
            }
            pageParser.getH1Headers(URL, config.getCrawlLang());
        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
        }
    }

    boolean isLinkReachable(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }
}
