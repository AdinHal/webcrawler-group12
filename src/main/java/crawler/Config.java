package crawler;

import java.util.List;

public class Config {

    private final String crawlUrl;
    private final int crawlDepth;
    private final List<String> crawlDomains;
    private final int crawlAdditionalLinksDepth;


    public Config(String crawlUrl, int crawlDepth, int crawlAdditionalLinksDepth, List<String> crawlDomains){
        this.crawlUrl = crawlUrl;
        this.crawlDepth = crawlDepth;
        this.crawlDomains = crawlDomains;
        this.crawlAdditionalLinksDepth = crawlAdditionalLinksDepth;
    }

    public String getCrawlUrl() {
        return crawlUrl;
    }

    public int getCrawlAdditionalLinksDepth(){return crawlAdditionalLinksDepth;}

    public int getCrawlDepth() {
        return crawlDepth;
    }

    public List<String> getCrawlDomains() {
        return crawlDomains;
    }
}
