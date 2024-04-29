import static org.junit.Assert.*;

import crawler.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class MarkdownGeneratorTest {

    static MarkdownGenerator markdownGenerator;

    @Test
    public void testMDGeneratorInit() throws IOException {
        File testFile = File.createTempFile("testFile",".md");

        markdownGenerator = new MarkdownGenerator(testFile.getAbsolutePath());

        markdownGenerator.init();

        assertNotNull("PrintWriter must be initialized.",markdownGenerator.printWriter);
    }

   @Test
    public void testInitMDGeneratorWithNull() throws IOException {
        markdownGenerator = new MarkdownGenerator(null);

        markdownGenerator.init();

        assertNotNull(markdownGenerator.filePath, "File path should not be null");
        assertTrue("The file should exist",new File(markdownGenerator.filePath).exists());
    }

    @Test
    public void testInitWithNoFile() throws IOException{
       File testParentFile = Files.createTempDirectory("testDirectory").toFile();
       String testFilePath = new File(testParentFile,"testFile.md").getAbsolutePath();

       markdownGenerator = new MarkdownGenerator(testFilePath);

       markdownGenerator.init();

       File testFile = new File(testFilePath);
       assertTrue("File should be created if non-existing",testFile.exists());
    }

    @Test
    public void testWriteEntriesWithEmptyString() throws IOException {
        File testFile = Files.createTempDirectory("testDirectory").resolve("testOut.md").toFile();
        markdownGenerator = new MarkdownGenerator(testFile.getAbsolutePath());

        markdownGenerator.writeEntries("");

        assertTrue("File is empty because an empty string was passed.",testFile.length() == 0);
    }

    @Test
    public void testWriteEntriesWithNullString() throws IOException {
        File testFile = Files.createTempDirectory("testDirectory").resolve("testOut.md").toFile();
        markdownGenerator = new MarkdownGenerator(testFile.getAbsolutePath());

        markdownGenerator.writeEntries(null);

        assertTrue("File should be empty since an empty string was written.",testFile.length() == 0);
    }


}
