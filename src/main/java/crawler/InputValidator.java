package crawler;

import java.util.Scanner;

public class InputValidator {
    public final Scanner scanner = new Scanner(System.in);

    public String inputURLs(){
        String URLRegEx = "\\b((https?|ftp):\\/\\/[^\\s,]+(?:\\/[^\\s,]*)?)\\b(?:,\\s*\\b((https?|ftp):\\/\\/[^\\s,]+(?:\\/[^\\s,]*)?)\\b)*";

        Main.urlsToCrawl = scanner.nextLine();

        while(!Main.urlsToCrawl.matches(URLRegEx)) {
            System.out.print("One or more URLs are malformed. Please re-enter your URL(s): ");
            Main.urlsToCrawl = scanner.next();
        }
        return Main.urlsToCrawl;
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
