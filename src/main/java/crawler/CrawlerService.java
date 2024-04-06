package main.java.crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CrawlerService {

    private Config config;

    public CrawlerService(Config config){
        this.config = config;
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

}
