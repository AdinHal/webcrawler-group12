package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {
    private static FileWriter instance;
    private BufferedWriter writer;

    private FileWriter() {
        try {
            writer = new BufferedWriter(new java.io.FileWriter("report.md"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileWriter getInstance() {
        if (instance == null) {
            instance = new FileWriter();
        }
        return instance;
    }

    public void write(String content) {
        try {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void printHeaders(Document document, String prefix) {
        FileWriter fileWriter = FileWriter.getInstance();
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
}
