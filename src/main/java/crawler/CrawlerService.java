package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class CrawlerService {

    private final Config config;
    private final PageParser pageParser;
    private final LinkValidator validator;
    private Map<String,String> linksAndMessages;
    private MarkdownGenerator markdownGenerator;

    public CrawlerService(Config config, LinkValidator validator, PageParser pageParser, String filePath){
        this.config = config;
        this.validator = validator;
        this.pageParser = pageParser;
        this.linksAndMessages = new HashMap<>();
        setupCrawler(filePath);
    }

    public void getPageLinks(String URL, int depth) {
       if (depth > config.getCrawlDepth() + config.getCrawlAdditionalLinksDepth() || linksAndMessages.containsKey(URL)) {
            return;
        }

        //System.out.println("Fetching from: " + URL);
        linksAndMessages.put(URL,"Fetching URL...");

        try {
            Document document = Jsoup.connect(URL).get();
            Elements linksOnPage = document.select("a[href]");
            String baseDomain = new URI(URL).getHost();

            for (Element page : linksOnPage) {
                String absUrl = page.absUrl("href");
                try {
                    URI uri = new URI(absUrl);
                    String domain = uri.getHost();

                    if (domain != null && domain.equals(baseDomain) && !linksAndMessages.containsKey(absUrl)) {
                        getPageLinks(absUrl, depth + 1);
                    }
                } catch (URISyntaxException URI) {
                    URI.printStackTrace();
                    linksAndMessages.put(absUrl, "Invalid URL.");
                }
            }
            System.out.print("<br>--> link to <a>"+URL+"</a>\n"+pageParser.getHeaders(URL, depth, false));
        } catch (IOException | URISyntaxException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
            linksAndMessages.put(URL, "For '" + URL + "': " + e.getMessage());
        }
    }

    private void writeMDEntries() {
        linksAndMessages.forEach((url, message) -> {
            //markdownGenerator.writeEntries(url, message);
        });
        linksAndMessages.clear();
    }


    public void setupCrawler(String filePath) {
        this.markdownGenerator = new MarkdownGenerator(filePath);
        try {
            this.markdownGenerator.init();
        } catch (IOException e) {
            System.err.println("Failed to initialize MDGenerator. Error: " + e.getMessage());
        }
    }
}