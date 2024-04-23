package crawler;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the URL:");
        String crawl_url = scanner.nextLine();

        System.out.println("Please enter the Crawl depth");
        int crawl_depth = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Please enter the domains to be crawled (comma-separated, no spaces): ");
        String crawl_domains = scanner.nextLine();
        List<String> crawlDomainsList = Arrays.asList(crawl_domains.split(","));

        System.out.println("Please enter the language the text should be translated to (Optional* -> Press 'Enter' if You want to skip):");
        String crawl_lang = scanner.nextLine();

        scanner.close();

        Config config = new Config(crawl_url,crawl_depth,crawlDomainsList,crawl_lang);
        PageParser pageParser = new PageParser(config);
        CrawlerService crawlerService = new CrawlerService(config, pageParser);

        pageParser.printSummary(config.getCrawlUrl(), config.getCrawlLang());

        System.out.println("\nTraversing site...\n");

        crawlerService.getPageLinks(config.getCrawlUrl(), config.getCrawlDepth());
    }
}
