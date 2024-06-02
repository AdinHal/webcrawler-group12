package crawler;

import crawler.models.CrawledPage;
import crawler.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class URLHandler {
    private final CrawlerConfig config;
    private final List<CrawledPage> crawledPages;

    public URLHandler(CrawlerConfig config) {
        this.config = config;
        this.crawledPages = new ArrayList<>();
    }

    public List<CrawledPage> process() {
        crawl(config.getStartUrl(), 0);
        return crawledPages;
    }

    private void crawl(String url, int depth) {
        if (depth > config.getMaxDepth()) return;

        CrawledPage page = PageParser.parse(url, config.getMaxDepth());
        if (page != null) {
            crawledPages.add(page);
            if (depth < config.getMaxDepth()) {
                for (String link : page.getLinks()) {
                    if (config.getDomains().contains(HttpUtil.getDomain(link))) {
                        crawl(link, depth + 1);
                    }
                }
            }
        }
    }
}
