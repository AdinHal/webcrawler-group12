package crawler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class MarkdownGenerator {

    private PrintWriter printWriter;
    private BufferedWriter bufferedWriter;
    private FileWriter fileWriter;
    private String filePath;

    public MarkdownGenerator(String filePath){
        this.filePath = filePath;
    }

    public void init() throws IOException {
        if(filePath.trim().isEmpty() || filePath == null){
            System.out.println("No path provided by user. Creating temp directory.");

            Path folderPath = Files.createTempDirectory("tempFolder");
            File tempFilePath = new File(folderPath.toFile(),"urls.md");
            filePath = tempFilePath.getAbsolutePath();

            System.out.println("File path: "+filePath);
        }else{
            File file = new File(filePath);
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        }
        fileWriter = new FileWriter(filePath, true);
        bufferedWriter = new BufferedWriter(fileWriter);
        printWriter = new PrintWriter(bufferedWriter);
    }

    public void writeEntries(Map<String, List<String>> crawlHeadersAndUrls, boolean isSummary){

    }


    public void close() throws IOException{
        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();
    }
}
