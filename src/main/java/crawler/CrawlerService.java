package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class CrawlerService {

    private final Config config;
    private final LinkValidator validator;
    private final PageParser pageParser;
    private final HashSet<String> links;
    private String filePath;

    public CrawlerService(Config config, LinkValidator validator, PageParser pageParser){
        this.config = config;
        this.validator = validator;
        this.pageParser = pageParser;
        this.links = new HashSet<String>();
        this.filePath = "";
    }

    public void initFilePath() throws IOException{
        if(filePath.trim().isEmpty() || filePath == null){
            System.out.println("No path provided by user. Creating temp directory.");
            Path folderPath = Files.createTempDirectory("tempFolder");
            File tempFilePath = new File(folderPath.toFile(),"urls.md");
            filePath = tempFilePath.getAbsolutePath();
            System.out.println("File path: "+filePath);
        }else{
            File file = new File(filePath);
            if(!file.exists()){
                file.getParentFile().mkdir();
                file.createNewFile();
            }
        }
    }

    private void saveUrl(String url) throws IOException {
        try(FileWriter fileWriter = new FileWriter(filePath,true)){
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(url);
        }catch (IOException ioException){
            System.err.println("URL could not be saved: "+url);
            ioException.printStackTrace();
        }

    }

    public void getPageLinks(String URL) {
        int depth = 0;

        int maxdepth = config.getCrawlDepth();
        if (depth > maxdepth || links.contains(URL)) {
            return;
        }

        String userDomain = config.getCrawlDomains().get(0);

        System.out.println("Fetching from: " + URL);
        try {
            links.add(URL);

            Document document = Jsoup.connect(URL).get();

            Elements linksOnPage = document.select("a[href]");

            for (Element page : linksOnPage) {
                String absUrl = page.absUrl("href");
                try {
                    URI uri = new URI(absUrl);
                    String domain = uri.getHost();

                    if(domain != null && domain.equals(userDomain)){
                        if (validator.isLinkReachable(absUrl)) {
                            pageParser.getHeaders(URL, maxdepth, false);
                            getPageLinks(absUrl);
                        }
                        else{
                            System.out.println("Broken link: " + absUrl);
                        }

                    }
                } catch (URISyntaxException URI) {
                    URI.printStackTrace();
                }
            }
            pageParser.getHeaders(URL, depth, false);
        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}