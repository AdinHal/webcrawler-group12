import crawler.URLHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class URLHandlerTest {

    private static Document mockDocument;
    private static URLHandler urlHandler;
    public static List<String> visited;
    static Set<String> allowedDomains = new HashSet<>();

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
    public void testIncorrectInput() {
        String urlToCrawl = "&/&/%/%(&(&(&(/&/&(";

        assertThrows(IllegalArgumentException.class, () -> {
            URLHandler.requestLinkAccess(urlToCrawl, 0, 4, visited, false);
        });
    }

    //TODO: Darf auch keine Exception werfen, wie wird das getestet?
    @Test
    public void testDomainAllowedFail() {
        allowedDomains.add("orf.at");

        URLHandler.isDomainAllowed("https://www.regex101.com");
    }

    //TODO: Wie nach einem broken link testen wenn Methode keine Exception wirft/werfen darf? Gleiches gilt für requestLinkAccessTest
    public void testDeadLink(){
        String urlToCrawl = "https://www.hsuhfsfkjhdfj.com";
    }
}
