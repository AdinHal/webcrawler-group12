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
        String crawl_lang = getInput(args, 3, scanner, "Please enter the language the text should be translated to (Optional* -> Press 'Enter' if You want to skip):", true);

        scanner.close();

        List<String> crawlDomainsList = Arrays.asList(crawl_domains.split(","));
        Config config = new Config(crawl_url, crawl_depth, crawlDomainsList, crawl_lang);
        PageParser pageParser = new PageParser(config);
        CrawlerService crawlerService = new CrawlerService(config, pageParser);

        pageParser.printSummary(config.getCrawlUrl(), config.getCrawlLang());
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
