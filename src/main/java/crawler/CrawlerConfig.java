package crawler;

import java.util.Arrays;
import java.util.List;

public class CrawlerConfig {
    private final String startUrl;
    private final int maxDepth;
    private final List<String> domains;
    private final int additionalLinksCrawlDepth;

    public CrawlerConfig(String startUrl, int maxDepth, List<String> domains, int additionalLinksCrawlDepth) {
        this.startUrl = startUrl;
        this.maxDepth = maxDepth;
        this.domains = domains;
        this.additionalLinksCrawlDepth = additionalLinksCrawlDepth;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public List<String> getDomains() {
        return domains;
    }

    public int getAdditionalLinksCrawlDepth() {
        return additionalLinksCrawlDepth;
    }
}
