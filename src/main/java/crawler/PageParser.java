package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class PageParser {
    private final HashSet<String> headerList;

    public PageParser() {
        this.headerList = new HashSet<>();
    }

    public String getHeaders(String URL, int depth, boolean isSummary) {
        StringBuilder result = new StringBuilder();
        try {
            Document document = Jsoup.connect(URL).get();
            Elements headers = document.select("h1, h2, h3");

            int index = 0;

            for (Element header : headers) {
                String text = header.text();

                if (!headerList.contains(text)) {
                    result.append(routePrinter(document, index));
                    if (isSummary) {
                        result.append(text).append("\n");
                    } else {
                        for (int i = 0; i <= depth; i++) {
                            result.append("-");
                        }
                        result.append("> ").append(text).append("\n");
                    }
                    headerList.add(text);
                }
                index++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }

    public void printSummary(String URL){
        System.out.println("input: <a>"+URL+"</a> ");
        System.out.println("<br>depth:");
        System.out.println("<br>summary:\n");
        System.out.println(getHeaders(URL, 0, true));
        System.out.print("\n");
    }

    private String routePrinter(Document document, int index) {
        Elements headers = document.select("h1, h2, h3");
        if (index < headers.size()) {
            Element header = headers.get(index);

            if (header.is("h3")) {
                return "### ";
            } else if (header.is("h2")) {
                return "## ";
            } else if (header.is("h1")) {
                return "# ";
            }
        }
        return "X ";
    }
}
