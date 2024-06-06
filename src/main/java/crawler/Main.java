package crawler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String crawl_url = getInput(args, 0, scanner, "Enter the URL to be crawled:", false);
        int crawl_depth = Integer.parseInt(getInput(args, 1, scanner, "Enter the crawl depth: ", false));
        String crawl_domains = getInput(args, 2, scanner, "Enter the domains to be crawled (comma-separated, no spaces):", false);
        String additionalLinksDepth = getInput(args, 3, scanner, "Define the depth for additional links", true);
        String crawledURLsPath = getInput(args,4,scanner,"Enter the path where the .md File should be stored. Will be stored under temp as per default",true);

        scanner.close();

        int crawlAdditionalLinks_depth = additionalLinksDepth.isEmpty() ? 2 : Integer.parseInt(additionalLinksDepth);

        MarkdownGenerator markdownGenerator = new MarkdownGenerator(crawledURLsPath);
        markdownGenerator.init();

        List<String> crawlDomainsList = Arrays.asList(crawl_domains.split(","));
        Config config = new Config(crawl_url, crawl_depth, crawlAdditionalLinks_depth, crawlDomainsList);
        PageParser pageParser = new PageParser(markdownGenerator);
        URLHandler urlHandler = new URLHandler();
        CrawlerService crawlerService = new CrawlerService(config, urlHandler, pageParser, markdownGenerator);

        pageParser.printSummary(config.getCrawlUrl(), config.getCrawlDepth());
        System.out.println("\nTraversing site...\n");
        crawlerService.startCrawling(config.getCrawlUrl(), config.getCrawlDepth());
        markdownGenerator.close();
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