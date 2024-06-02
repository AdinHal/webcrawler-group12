package crawler.models;

import java.util.List;

public class CrawledPage {
    private final String url;
    private final String title;
    private final List<String> headings;
    private final List<String> links;

    public CrawledPage(String url, String title, List<String> headings, List<String> links) {
        this.url = url;
        this.title = title;
        this.headings = headings;
        this.links = links;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getHeadings() {
        return headings;
    }

    public List<String> getLinks() {
        return links;
    }
}
