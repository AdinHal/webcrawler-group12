package crawler;

import crawler.models.CrawledPage;
import crawler.util.InputHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner inputScanner = new Scanner(System.in);

        String urlToCrawl = InputHandler.getInput(args, 0, inputScanner, "Enter the URL to be crawled:");
        int crawlDepth = Integer.parseInt(InputHandler.getInput(args, 1, inputScanner, "Enter the crawl depth: "));
        String domainsToCrawl = InputHandler.getInput(args, 2, inputScanner, "Enter the domains to be crawled (comma-separated, no spaces):");
        String additionalLinksDepth = InputHandler.getInput(args, 3, inputScanner, "Define the depth for additional links", true);

        inputScanner.close();

        int additionalLinksCrawlDepth = additionalLinksDepth.isEmpty() ? 2 : Integer.parseInt(additionalLinksDepth);

        CrawlerConfig config = new CrawlerConfig(urlToCrawl, crawlDepth, Arrays.asList(domainsToCrawl.split(",")), additionalLinksCrawlDepth);
        URLHandler urlProcessor = new URLHandler(config);
        List<CrawledPage> crawledPages = urlProcessor.process();

        MarkdownReport writer = new MarkdownReport();
        writer.write(crawledPages, config.getStartUrl(), config.getMaxDepth(), config.getAdditionalLinksCrawlDepth());
    }
}