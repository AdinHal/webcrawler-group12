import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import crawler.FileWriterSingleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.BufferedWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FileWriterSingletonTest {
    private FileWriterSingleton fileWriterSingleton;
    private BufferedWriter mockBufferedWriter;

    @BeforeEach
    void setUp() {
        fileWriterSingleton = FileWriterSingleton.getInstance();
        mockBufferedWriter = Mockito.mock(BufferedWriter.class);

        try {
            var field = FileWriterSingleton.class.getDeclaredField("writer");
            field.setAccessible(true);
            field.set(fileWriterSingleton, mockBufferedWriter);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set up the mock BufferedWriter", e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            var field = FileWriterSingleton.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to reset the singleton instance", e);
        }
    }

    @Test
    void testInstanceCreation() {
        FileWriterSingleton instance1 = FileWriterSingleton.getInstance();
        FileWriterSingleton instance2 = FileWriterSingleton.getInstance();
        assertSame(instance1, instance2, "Both instances should be the same");
    }

    @Test
    void testWriteContent() {
        String testContent = "Test content\n";
        fileWriterSingleton.write(testContent);
        try {
            verify(mockBufferedWriter, times(1)).write(testContent);
            verify(mockBufferedWriter, times(1)).flush();
        } catch (IOException e) {
            fail("IOException should not occur", e);
        }
    }

    @Test
    void testCloseWriter() {
        fileWriterSingleton.close();
        try {
            verify(mockBufferedWriter, times(1)).close();
        } catch (IOException e) {
            fail("IOException should not occur", e);
        }
    }

    @Test
    void testWriteInMultipleThreads() throws InterruptedException {
        Runnable writeTask = () -> fileWriterSingleton.write("Test content\n");

        Thread thread1 = new Thread(writeTask);
        Thread thread2 = new Thread(writeTask);
        Thread thread3 = new Thread(writeTask);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        try {
            verify(mockBufferedWriter, times(3)).write("Thread content\n");
            verify(mockBufferedWriter, times(3)).flush();
        } catch (IOException e) {
            fail("IOException should not occur", e);
        }
    }

    @Test
    void testWriteExceptionHandling() {
        String testContent = "Test content\n";
        try {
            doThrow(new IOException("Mock IOException")).when(mockBufferedWriter).write(testContent);
        } catch (IOException e) {
            fail("Setup for IOException mocking failed", e);
        }

        // This will only print the stack trace
        fileWriterSingleton.write(testContent);

        try {
            verify(mockBufferedWriter, times(1)).write(testContent);
            verify(mockBufferedWriter, times(0)).flush();
        } catch (IOException e) {
            fail("IOException should not occur during verification", e);
        }
    }

    @Test
    void testCloseExceptionHandling() {
        try {
            doThrow(new IOException("Mock IOException")).when(mockBufferedWriter).close();
        } catch (IOException e) {
            fail("Setup for IOException mocking failed", e);
        }

        fileWriterSingleton.close();

        try {
            verify(mockBufferedWriter, times(1)).close();
        } catch (IOException e) {
            fail("IOException should not occur during verification", e);
        }
    }
}
