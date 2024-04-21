package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class PageParser {
    private Config config;
    private HashSet<String> h1headers;

    public PageParser(Config config) {
        this.config = config;
        this.h1headers = new HashSet<String>();
    }

    public void getH1Headers(String URL, String lang) {
        try {
            Document document = Jsoup.connect(URL).get();

            Elements headers = document.select("h1");

            for (Element header : headers){
                String text = header.text();

                if(!h1headers.contains(text)){
                    System.out.println("# --> " + text);
                    h1headers.add(text);
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

    public void getH2Headers(String URL, String lang) {
        try {
            Document document = Jsoup.connect(URL).get();

            Elements headers = document.select("h2");

            for (Element header : headers){
                String text = header.text();

                if(!h1headers.contains(text)){
                    System.out.println("## --> " + text);
                    h1headers.add(text);
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

        getH1Headers(URL, lang);
        /* Unneeded until I figure out how to sort subheaders below headers
        getH2Headers(URL, lang);*/
    }

}
