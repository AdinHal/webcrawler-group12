package crawler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String crawl_url = getInput(args, 0, scanner, "Enter the URL to be crawled:");
        int crawl_depth = Integer.parseInt(getInput(args, 1, scanner, "Enter the crawl depth: "));
        String crawl_domains = getInput(args, 2, scanner, "Enter the domains to be crawled (comma-separated, no spaces):");
        String crawledURLs_Path = getInput(args,3,scanner,"Enter the path where the .md File should be stored. Will be stored under temp as per default",true);
        String crawlAdditionalLinks_depth_input = getInput(args, 4, scanner, "Define the depth for additional links", true);

        scanner.close();

        int crawlAdditionalLinks_depth = crawlAdditionalLinks_depth_input.isEmpty() ? 2 : Integer.parseInt(crawlAdditionalLinks_depth_input);

        List<String> crawlDomainsList = Arrays.asList(crawl_domains.split(","));
        Config config = new Config(crawl_url, crawl_depth, crawlAdditionalLinks_depth, crawlDomainsList);
        PageParser pageParser = new PageParser();
        LinkValidator validator = new LinkValidator();
        CrawlerService crawlerService = new CrawlerService(config, validator, pageParser, crawledURLs_Path);

        pageParser.printSummary(config.getCrawlUrl());
        System.out.println("\nTraversing site...\n");
        crawlerService.getPageLinks(config.getCrawlUrl(), config.getCrawlDepth());
    }

    private static String getInput(String[] args, int index, Scanner scanner, String message) {
        return getInput(args, index, scanner, message, false);
    }

    private static String getInput(String[] args, int index, Scanner scanner, String message, boolean optional) {
        if (args.length > index && !args[index].isEmpty()) {
            return args[index];
        }
        if (optional) {
            System.out.println(message + " (Optional* -> Press 'Enter' if you want to skip):");
        } else {
            System.out.println(message);
        }
        return scanner.nextLine();
    }
}