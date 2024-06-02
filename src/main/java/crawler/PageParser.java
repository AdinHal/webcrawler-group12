package crawler;

import crawler.models.CrawledPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageParser {
    public static CrawledPage parse(String url, int maxHeadingLevel) {
        try {
            Connection.Response response = Jsoup.connect(url).execute();
            String contentType = response.contentType();
            if (contentType == null || !contentType.contains("text/html")) {
                System.err.println("Skipping non-HTML content: " + url);
                return null;
            }

            Document doc = response.parse();
            String title = doc.title();
            List<String> headingTexts = new ArrayList<>();

            for (int level = 1; level <= maxHeadingLevel; level++) {
                Elements headings = doc.select("h" + level);
                for (Element heading : headings) {
                    headingTexts.add(level + "##" + heading.text());
                }
            }

            Elements links = doc.select("a[href]");
            List<String> linkHrefs = new ArrayList<>();
            links.forEach(link -> linkHrefs.add(link.attr("abs:href"))); // Use abs:href to get absolute URLs

            return new CrawledPage(url, title, headingTexts, linkHrefs);
        } catch (IOException e) {
            System.err.println("Error parsing URL: " + url);
            return null;
        }
    }
}
