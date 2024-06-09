import crawler.URLHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class URLHandlerTest {

    private static Document mockDocument;
    private static URLHandler urlHandler;

    @BeforeAll
    public static void setUp() {
        urlHandler = new URLHandler();
        mockDocument = Jsoup.parse("<html><head><title>Test</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>");
    }

    @Test
    public void testExtractFromURL() {
        Elements elements = urlHandler.extractFromURL(mockDocument, "p");
        assertNotNull(elements);
        assertEquals(1, elements.size());
        assertEquals("Parsed HTML into a doc.", elements.first().text());
    }

}
