package crawler;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import static crawler.Main.*;

public class InputValidator {
    public final Scanner scanner = new Scanner(System.in);

    public void splitURLs(){
        String userInput = scanner.nextLine();
        String[] urls;

        if (userInput.contains(",")) {
            urls = userInput.split(",");

            if (urls.length > urlsToCrawl.length) {
                System.out.print("Too many URLs specified. Try with less URLs: ");
                splitURLs();
            }
        }
        else{
            urls = new String[]{userInput};
        }

        inputURLs(urls);
    }

    public void inputURLs(String[] urls) {
        try {
            int index = 0;

            for (String u : urls) {
                new URL(u).toURI();
                urlsToCrawl[index] = u;
                index++;
            }
        } catch (MalformedURLException | URISyntaxException e) {
            System.out.print("One or more URLs are malformed or null. Try writing them again: ");
            splitURLs();
        }
    }

    public void inputDepth(){
        do {
            while(!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a positive integer: ");
                scanner.next();
            }
            crawlDepth = scanner.nextInt();
            scanner.nextLine();
        } while (crawlDepth <= 0);
    }

    public void inputDomains(){
        String domainsInput = scanner.nextLine();
        String[] domains = domainsInput.split(",");
        for (String domain : domains) {
            allowedDomains.add(domain.trim());
        }
    }
}
