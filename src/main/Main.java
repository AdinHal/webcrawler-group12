package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the URL:");
        String crawl_url = scanner.nextLine();

        System.out.println("(Optional|Press Enter if You want to skip) Enter a language code:");
        String crawl_lang = scanner.nextLine();

        if(crawl_lang.length() == 0){
            //Execute WebCrawler without Language Parameter
        }else{
            //Execute WebCrawler with Language Parameter
        }

        scanner.close();
    }
}
