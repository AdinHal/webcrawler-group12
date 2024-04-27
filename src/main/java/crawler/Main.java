package crawler;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String crawl_url = getInput(args, 0, scanner, "Please enter the URL:");
        int crawl_depth = Integer.parseInt(getInput(args, 1, scanner, "Please enter the Crawl depth"));
        String crawl_domains = getInput(args, 2, scanner, "Please enter the domains to be crawled (comma-separated, no spaces):");

        scanner.close();

        List<String> crawlDomainsList = Arrays.asList(crawl_domains.split(","));
        Config config = new Config(crawl_url, crawl_depth, crawlDomainsList);
        PageParser pageParser = new PageParser();
        LinkValidator validator = new LinkValidator();
        CrawlerService crawlerService = new CrawlerService(config, validator, pageParser);

        pageParser.printSummary(config.getCrawlUrl());
        System.out.println("\nTraversing site...\n");
        crawlerService.getPageLinks(config.getCrawlUrl());
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
