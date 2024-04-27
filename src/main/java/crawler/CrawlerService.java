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
    private final LinkValidator validator;
    private final PageParser pageParser;
    private Map<String,String> linksAndMessages;
    private MarkdownGenerator markdownGenerator;

    public CrawlerService(Config config, LinkValidator validator, PageParser pageParser, String filePath){
        this.config = config;
        this.validator = validator;
        this.pageParser = pageParser;
        this.linksAndMessages = new HashMap<>();
        setupCrawler(filePath);
    }

    public void getPageLinks(String URL) {
        int depth = 0;
        int maxdepth = config.getCrawlDepth();

        if (depth > maxdepth || linksAndMessages.containsKey(URL)) {
            return;
        }

        String userDomain = config.getCrawlDomains().get(0);
        System.out.println("Fetching from: " + URL);
        linksAndMessages.put(URL,"Fetching URL...");

        try {
            Document document = Jsoup.connect(URL).get();
            Elements linksOnPage = document.select("a[href]");

            for (Element page : linksOnPage) {
                String absUrl = page.absUrl("href");
                try {
                    URI uri = new URI(absUrl);
                    String domain = uri.getHost();

                    if(domain != null && domain.equals(userDomain)){
                        if (validator.isLinkReachable(absUrl)) {
                            pageParser.getHeaders(URL, maxdepth, false);
                            linksAndMessages.put(absUrl, "Successful fetch and processing.");
                            getPageLinks(absUrl);
                        }
                        else{
                            linksAndMessages.put(absUrl, "Broken link found.");
                        }

                    }
                } catch (URISyntaxException URI) {
                    URI.printStackTrace();
                    linksAndMessages.put(absUrl, "Invalid URL.");
                }
            }
            pageParser.getHeaders(URL, depth, false);
        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
            linksAndMessages.put(URL, "For '" + URL + "': " + e.getMessage());
        }finally{
            if(depth == 0){
                try{
                    writeMDEntries();
                    markdownGenerator.close();
                } catch (IOException ioException) {
                    System.err.println("Error closing Markdown Generator: " + ioException.getMessage());
                }
            }
        }
    }

    private void writeMDEntries() {
        linksAndMessages.forEach((url, message) -> {
            markdownGenerator.writeEntries(url, message);
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