package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrawlerService {

    private final Config config;
    private final PageParser pageParser;
    private final List<String>markdownEntries;
    public  Map<String,String> visitedLinks;
    private final MarkdownGenerator markdownGenerator;
    private final URLHandler urlHandler;

    public CrawlerService(Config config, URLHandler urlHandler, PageParser pageParser, MarkdownGenerator markdownGenerator){
        this.config = config;
        this.pageParser = pageParser;
        this.urlHandler = urlHandler;
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
            Document document = urlHandler.connectToURL(URL);
            Elements linksOnPage = urlHandler.extractFromURL(document, "a[href]");

            String baseDomain = new URI(URL).getHost();

            for (Element page : linksOnPage) {
                String absUrl = page.absUrl("href");
                try {
                    URI uri = new URI(absUrl);
                    String domain = uri.getHost();
                    if (domain != null && domain.equals(baseDomain) && !visitedLinks.containsKey(absUrl) && urlHandler.isLinkReachable(absUrl)) {
                        getPageLinks(absUrl, depth + 1);
                    }
                } catch (URISyntaxException URI) {
                    markdownEntries.add("<br>--> broken link <a>" + URL + "</a>");
                }
            }
            String headersMarkdown = pageParser.getHeaders(URL, depth, false);
            markdownEntries.add("<br>--> link to <a>" + URL + "</a>\n"+headersMarkdown);
        } catch (URISyntaxException e) {
            markdownEntries.add("<br>--> broken link <a>" + URL + "</a>");
        }
    }

    public void writeResults() {
        for (String entry : markdownEntries) {
            markdownGenerator.writeEntries(entry);
        }
    }
}