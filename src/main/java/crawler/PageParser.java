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

    public void getHeaders(String URL, String lang, int depth, boolean isSummary) {
        try {
            Document document = Jsoup.connect(URL).get();

            Elements headers = document.select("h1, h2, h3");

            for (Element header : headers){
                String text = header.text();

                if(!headerlist.contains(text) && isSummary){
                    routePrinter(URL);
                    System.out.print(text);
                    headerlist.add(text);
                }
                else if(!headerlist.contains(text) && !isSummary){
                    routePrinter(URL);
                    for (int i = 0; i < depth; i++) {
                        System.out.print("-");
                    }
                    System.out.println("> " + text);
                    headerlist.add(text);
                }

                /*if(lang != null){
                    System.out.println(TranslatorService.Translate(header.text(), lang));
                }
                else{
                    System.out.println(header.text());
                }*/
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printSummary(String URL, String lang){
        System.out.println("Summary:\n");

        getHeaders(URL, lang, 0, true);
    }

    public void routePrinter(String URL){
        //Temp code, finishing this tomorrow because I don't feel like doing stuff anymore for 2day LOL
        System.out.print("#");

        /*Document document;

        try {
            document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements headers = document.select("h1, h2, h3");
        */
    }
}
