package crawler.util;

import java.util.Scanner;

public class InputHandler {
    public static String getInput(String[] args, int index, Scanner scanner, String message) {
        return getInput(args, index, scanner, message, false);
    }

    public static String getInput(String[] args, int index, Scanner scanner, String message, boolean optional) {
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
