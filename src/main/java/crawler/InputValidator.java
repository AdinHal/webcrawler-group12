package crawler;

import java.util.Scanner;

public class InputValidator {
    public static void inputURLs(Scanner scanner){
        String URLRegEx = "\\b((https?|ftp)://[^\\s,]+(?:/[^\\s,]*)?)\\b(?:,\\s*\\b((https?|ftp)://[^\\s,]+(?:/[^\\s,]*)?)\\b)*\n";

        Main.urlsToCrawl = scanner.nextLine();

        while(!Main.urlsToCrawl.matches(URLRegEx)) {
            System.out.print("One or more URLs are malformed. Please re-enter your URL(s): ");
            scanner.next();
        }
    }
    public static void inputDepth(Scanner scanner){
        do {
            while(!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a positive integer: ");
                scanner.next();
            }
            Main.crawlDepth = scanner.nextInt();
        } while (Main.crawlDepth <= 0);
    }
}
