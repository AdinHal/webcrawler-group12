package crawler;

import java.util.List;

public class Config {

    private String crawlUrl;
    private int crawlDepth;
    private List<String> crawlDomains;
    private String crawlLang;

    public Config(String crawlUrl, int crawlDepth, List<String> crawlDomains, String crawlLang){
        this.crawlUrl = crawlUrl;
        this.crawlDepth = crawlDepth;
        this.crawlDomains = crawlDomains;
        this.crawlLang = crawlLang;
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

    public String getCrawlLang() {
        return crawlLang;
    }
}
