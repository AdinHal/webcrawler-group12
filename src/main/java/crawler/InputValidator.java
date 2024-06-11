package crawler;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class InputValidator {
    public final Scanner scanner = new Scanner(System.in);

    public void splitURLs(){
        String userInput = scanner.nextLine();
        String[] urls;

        if (userInput.contains(",")) {
            urls = userInput.split(",");
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
                Main.urlsToCrawl[index] = u;
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
            Main.crawlDepth = scanner.nextInt();
        } while (Main.crawlDepth <= 0);
    }

    public void inputDomains(){
        String domainsInput = scanner.nextLine();
        String[] domains = domainsInput.split(",");
        for (String domain : domains) {
            Main.allowedDomains.add(domain.trim());
        }
    }
}
