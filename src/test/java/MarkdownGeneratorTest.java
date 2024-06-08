/*import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import static org.junit.Assert.*;


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

        assertEquals("File is empty because an empty string was passed.", 0, testFile.length());
    }

    @Test
    public void testWriteEntriesWithNullString() throws IOException {
        File testFile = Files.createTempDirectory("testDirectory").resolve("testOut.md").toFile();
        markdownGenerator = new MarkdownGenerator(testFile.getAbsolutePath());

        markdownGenerator.writeEntries(null);

        assertEquals("File should be empty since an empty string was written.", 0, testFile.length());
    }

    @Test
    public void testCloseMDGenerator() throws IOException {
        File testFile = Files.createTempDirectory("testDirectory").resolve("testOut.md").toFile();
        markdownGenerator = new MarkdownGenerator(testFile.getAbsolutePath());
        markdownGenerator.init();

        PrintWriter testPrintWriter = markdownGenerator.printWriter;
        testPrintWriter.println("TEST");
        assertFalse("No error",testPrintWriter.checkError());

        markdownGenerator.close();

        testPrintWriter.println("TEST Post close");
        testPrintWriter.flush();

        assertTrue("PW Indicate error",testPrintWriter.checkError());
    }

}
*/
