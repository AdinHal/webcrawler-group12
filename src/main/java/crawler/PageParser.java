package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class PageParser {
    private final HashSet<String> headerlist;

    public PageParser() {
        this.headerlist = new HashSet<>();
    }

    public void getHeaders(String URL, int depth, boolean isSummary) {
        try {
            Document document = Jsoup.connect(URL).get();

            Elements headers = document.select("h1, h2, h3");

            int index = 0;

            for (Element header : headers){
                String text = header.text();

                if(!headerlist.contains(text) && isSummary){
                    routePrinter(URL, index);
                    System.out.print(text);
                    System.out.print("\n");
                    headerlist.add(text);
                    index++;
                }
                else if(!headerlist.contains(text) && !isSummary){
                    routePrinter(URL, index);
                    for (int i = 0; i <= depth; i++) {
                        System.out.print("-");
                    }
                    System.out.println("> " + text);
                    headerlist.add(text);
                    index++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printSummary(String URL){
        System.out.println("Summary:\n");

        getHeaders(URL, 0, true);
        System.out.print("\n");
    }

    public void routePrinter(String URL, int index){
        Document document;

        try {
            document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements headers = document.select("h1, h2, h3");
        Element header = headers.get(index);

        if(header.is("h3")){
            System.out.print("### ");
        }
        else if(header.is("h2")){
            System.out.print("## ");
        }
        else if(header.is("h1")){
            System.out.print("# ");
        }
        else{
            System.out.print("X ");
        }
    }
}
