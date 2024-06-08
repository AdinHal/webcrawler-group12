import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import crawler.FileWriterSingleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileWriterSingletonTest {
    private FileWriterSingleton fileWriterSingleton;
    private BufferedWriter mockBufferedWriter;

    @BeforeEach
    void setUp() throws IOException {
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
    void tearDown() throws IOException {
        try {
            var field = FileWriterSingleton.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to reset the singleton instance", e);
        }
    }

    @Test
    void testSingletonInstance() {
        FileWriterSingleton instance1 = FileWriterSingleton.getInstance();
        FileWriterSingleton instance2 = FileWriterSingleton.getInstance();
        assertSame(instance1, instance2, "Both instances should be the same");
    }
}
