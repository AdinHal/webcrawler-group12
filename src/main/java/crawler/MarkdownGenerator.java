package crawler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public void writeEntries(String url, String results){
        printWriter.println("URL: "+url);
        printWriter.println("Results: ");
        printWriter.println(results);
        printWriter.println("#########################################################");
    }

    public void close() throws IOException{
        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();
    }
}
