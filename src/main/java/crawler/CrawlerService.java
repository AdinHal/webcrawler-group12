package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

public class CrawlerService {

    private Config config;
    private HashSet<String> links;

    public CrawlerService(Config config){
        this.config = config;
        this.links = new HashSet<String>();
    }


    private void saveUrl(String url) throws IOException {
        // the assignment specification does not say if we have to write it inside the .md file or in a separate text file - path can be changed
        String filePath = "crawledURLs.txt";

        try(FileWriter fileWriter = new FileWriter(filePath,true)){
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(url);
        }catch (IOException ioException){
            System.err.println("URL could not be saved: "+url);
            ioException.printStackTrace();
        }

    }

    public void getPageLinks(String URL, int depth) {
        int i = 0;

        String userDomain = config.getCrawlDomains().get(i);

        if (!links.contains(URL)) {
            try {
                if (links.add(URL)) {
                    System.out.println(URL);
                }

                //2. Fetch the HTML code
                Document document = Jsoup.connect(URL).get();

                //3. Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select("a[href]");
                Elements headers = document.select("h1");

                for (Element page : linksOnPage) {
                    String absUrl = page.absUrl("href");
                    try {
                        URI uri = new URI(absUrl);
                        String domain = uri.getHost();

                        if(domain != null && domain.equals(userDomain)){
                            getPageLinks(page.attr("abs:href"), depth);
                        }
                    } catch (URISyntaxException URI) {
                        URI.printStackTrace();
                    }
                }
                for (Element header : headers){
                    //TODO: Find out why the headers print like 4 times
                    System.out.println(header.text());
                }
            } catch (IOException e) {
                System.out.println("Broken Link");
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
}
