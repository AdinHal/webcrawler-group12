package crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterSingleton {
    private static FileWriterSingleton instance;
    private BufferedWriter writer;

    private FileWriterSingleton() {
        try {
            writer = new BufferedWriter(new FileWriter("report.md"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileWriterSingleton getInstance() {
        if (instance == null) {
            instance = new FileWriterSingleton();
        }
        return instance;
    }

    public void write(String content) {
        try {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
