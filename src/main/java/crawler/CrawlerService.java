package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrawlerService {

    private final Config config;
    private final PageParser pageParser;
    private final LinkValidator validator;
    private final List<String>markdownEntries;
    public  Map<String,String> visitedLinks;
    private final MarkdownGenerator markdownGenerator;

    public CrawlerService(Config config, LinkValidator validator, PageParser pageParser, MarkdownGenerator markdownGenerator){
        this.config = config;
        this.validator = validator;
        this.pageParser = pageParser;
        this.visitedLinks = new HashMap<>();
        this.markdownEntries = new ArrayList<>();
        this.markdownGenerator = markdownGenerator;
    }

    public void startCrawling(String URL, int depth){
        getPageLinks(URL, depth);
        writeResults();
    }

    public void getPageLinks(String URL, int depth) {
       if (depth > config.getCrawlDepth() + config.getCrawlAdditionalLinksDepth() || visitedLinks.containsKey(URL)) {
            return;
        }

        visitedLinks.put(URL,"Fetching URL...");

        try {
            Document document = Jsoup.connect(URL).get();
            Elements linksOnPage = document.select("a[href]");
            String baseDomain = new URI(URL).getHost();

            for (Element page : linksOnPage) {
                String absUrl = page.absUrl("href");
                try {
                    URI uri = new URI(absUrl);
                    String domain = uri.getHost();
                    if (domain != null && domain.equals(baseDomain) && !visitedLinks.containsKey(absUrl) && validator.isLinkReachable(absUrl)) {
                        getPageLinks(absUrl, depth + 1);
                    }
                } catch (URISyntaxException URI) {
                    /* Commented out error print for now, will be used in the next phase of the development.
                    URI.printStackTrace();
                     */
                    markdownEntries.add("<br>--> broken link <a>" + URL + "</a>");
                }
            }
            String headersMarkdown = pageParser.getHeaders(URL, depth, false);
            markdownEntries.add("<br>--> link to <a>" + URL + "</a>\n"+headersMarkdown);
        } catch (IOException | URISyntaxException e) {
            /* Commented out error print for now, will be used in the next phase of the development.
                e.printStackTrace();
             */
            markdownEntries.add("<br>--> broken link <a>" + URL + "</a>");
        }
    }

    public void writeResults() {
        for (String entry : markdownEntries) {
            markdownGenerator.writeEntries(entry);
        }
    }
}