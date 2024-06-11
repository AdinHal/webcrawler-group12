package crawler;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.List;

public class FileWriterSingleton {
    private static FileWriterSingleton instance;
    private static BufferedWriter writer;

    private FileWriterSingleton() {
        try {
            writer = new BufferedWriter(new FileWriter("report.md"));
        } catch (IOException e) {
            throw new RuntimeException("ERROR: Report file is unable to be created.");
        }
    }

    public static synchronized FileWriterSingleton getInstance() {
        if (instance == null) {
            instance = new FileWriterSingleton();
        }
        return instance;
    }

    public synchronized void write(String content) {
        try {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("ERROR: Contents could not be written to file.");
        }
    }

    public synchronized void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("ERROR: The file writer couldn't be closed.");
        }
    }

    static void printHeaders(Document document, String prefix) {
        FileWriterSingleton fileWriter = FileWriterSingleton.getInstance();
        URLHandler urlHandler = new URLHandler();
        Elements headers = urlHandler.extractFromURL(document, "h1, h2, h3, h4");

        String summaryHeaderTags = "#";

        //Prints routes depending on the number of the header
        for (Element header : headers){
            int headerRouteCount =
                    (header.is("h1")) ? 1
                            : (header.is("h2")) ? 2
                            : (header.is("h3")) ? 3
                            : (header.is("h4")) ? 4 : 1;
            fileWriter.write(summaryHeaderTags.repeat(headerRouteCount) + prefix + header.text() + "\n");
        }
    }

    public static void writeToFile(String urlToCrawl, int depth, int maxDepth, List<String> visited, boolean isInitialPage, Document document) throws IOException {
        String depthDash = "-";

        if (isInitialPage) {
            writer.write("input: <a>" + urlToCrawl + "</a>\n" +
                    "depth: " + maxDepth + "\n" +
                    "summary:\n");
            FileWriterSingleton.printHeaders(document, " ");
            visited.add(urlToCrawl);
        } else {
            writer.write("<br> --> link to <a>" + urlToCrawl + "</a>\n");

            FileWriterSingleton.printHeaders(document, " " + depthDash.repeat(depth) + " > ");
            writer.write("<br>\n");
            visited.add(urlToCrawl);
        }
    }
}
