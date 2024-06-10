import crawler.URLHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class URLHandlerTest {
    private static Document mockDocument;
    private static URLHandler urlHandler;
    private static List<String> visited;

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

    @Test
    void testInvalidLinkRequest() {
        assertThrows(IllegalArgumentException.class, () ->
                mockDocument = URLHandler.requestLinkAccess(")&§/)/()/)/", 1, 2, visited, false));
    }

    @Test
    void testBrokenLinkRequest() {
        assertThrows(IOException.class, () ->
                mockDocument = URLHandler.requestLinkAccess("https://hdssjhsjhsjf.com", 1, 2, visited, false));
    }
}
