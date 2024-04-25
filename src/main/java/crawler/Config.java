package crawler;

import java.util.List;

public class Config {

    private final String crawlUrl;
    private final int crawlDepth;
    private final List<String> crawlDomains;


    public Config(String crawlUrl, int crawlDepth, List<String> crawlDomains){
        this.crawlUrl = crawlUrl;
        this.crawlDepth = crawlDepth;
        this.crawlDomains = crawlDomains;
    }

    public String getCrawlUrl() {
        return crawlUrl;
    }

    public int getCrawlDepth() {
        return crawlDepth;
    }

    public List<String> getCrawlDomains() {
        return crawlDomains;
    }
}
