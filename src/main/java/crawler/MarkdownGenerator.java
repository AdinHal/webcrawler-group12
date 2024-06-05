package crawler;

import java.io.*;
import java.nio.file.Paths;

public class MarkdownGenerator {

    public PrintWriter printWriter;
    private BufferedWriter bufferedWriter;
    private FileWriter fileWriter;
    public String filePath;

    public MarkdownGenerator(){}

    public void init() throws IOException {
        filePath = Paths.get(System.getProperty("user.dir"), "report.md").toString();
        System.out.println("File path: " + filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        fileWriter = new FileWriter(file, true);
        bufferedWriter = new BufferedWriter(fileWriter);
        printWriter = new PrintWriter(bufferedWriter, true);
    }

    public void writeEntries(String entry){
        if (entry != null && !entry.isEmpty()) {
            try {
                printWriter.println(entry);
                printWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        if (printWriter != null) {
            printWriter.close();
        }
        System.out.println("File closed");
    }
}
