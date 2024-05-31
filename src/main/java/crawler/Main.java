package crawler;

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
        String outputFilePath = InputHandler.getInput(args, 4, inputScanner, "Enter the path where the .md File should be stored. Will be stored under temp as per default", true);

        inputScanner.close();

        int additionalLinksCrawlDepth = additionalLinksDepth.isEmpty() ? 2 : Integer.parseInt(additionalLinksDepth);

        MarkdownGenerator markdownGenerator = new MarkdownGenerator(outputFilePath);
        markdownGenerator.init();

        List<String> crawlDomainsList = Arrays.asList(domainsToCrawl.split(","));
        Config crawlConfig = new Config(urlToCrawl, crawlDepth, additionalLinksCrawlDepth, crawlDomainsList);
        PageParser pageParser = new PageParser(markdownGenerator);
        LinkValidator linkValidator = new LinkValidator();
        CrawlerService crawlerService = new CrawlerService(crawlConfig, linkValidator, pageParser, markdownGenerator);

        pageParser.printSummary(crawlConfig.getCrawlUrl(), crawlConfig.getCrawlDepth());
        System.out.println("\nTraversing site...\n");
        crawlerService.startCrawling(crawlConfig.getCrawlUrl(), crawlConfig.getCrawlDepth());
        markdownGenerator.close();
    }
}