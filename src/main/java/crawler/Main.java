package main.java.crawler;

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

        System.out.println("Please enter a language code (Optional* -> Press 'Enter' if You want to skip):");
        String crawl_lang = scanner.nextLine();

        scanner.close();


    }
}
