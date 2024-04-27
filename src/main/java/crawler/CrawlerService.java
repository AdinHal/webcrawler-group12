package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

public class CrawlerService {

    private final Config config;
    private final LinkValidator validator;
    private final PageParser pageParser;
    private final HashSet<String> links;
    private MarkdownGenerator markdownGenerator;

    public CrawlerService(Config config, LinkValidator validator, PageParser pageParser, String filePath){
        this.config = config;
        this.validator = validator;
        this.pageParser = pageParser;
        this.links = new HashSet<String>();
        this.markdownGenerator = new MarkdownGenerator(filePath);
    }

    public void getPageLinks(String URL) {
        int depth = 0;
        int maxdepth = config.getCrawlDepth();

        if (depth > maxdepth || links.contains(URL)) {
            return;
        }

        String userDomain = config.getCrawlDomains().get(0);
        System.out.println("Fetching from: " + URL);

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
                        if (validator.isLinkReachable(absUrl)) {
                            pageParser.getHeaders(URL, maxdepth, false);
                            getPageLinks(absUrl);
                        }
                        else{
                            System.out.println("Broken link: " + absUrl);
                        }

                    }
                } catch (URISyntaxException URI) {
                    URI.printStackTrace();
                }
            }
            pageParser.getHeaders(URL, depth, false);
        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
        }
    }
}