import crawler.URLHandler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class URLHandlerTest {

    @Mock
    Document mockDocument = Mockito.mock(Document.class);
    MockedStatic<URLHandler> mockedUrlHandler;

    @BeforeEach
    void setUp() {
        mockedUrlHandler = Mockito.mockStatic(URLHandler.class);
    }

    @AfterEach
    void tearDown() {
        mockedUrlHandler.close();
    }

    @Test
    void testRequestLinkAccessSuccess() {
        // Arrange
        String url = "http://quotes.toscrape.com";
        int currentDepth = 1;
        int maxDepth = 3;
        List<String> visited = List.of();
        boolean isInitialPage = true;

        mockedUrlHandler.when(() -> URLHandler.requestLinkAccess(anyString(), anyInt(), anyInt(), anyList(), anyBoolean()))
                .thenReturn(Optional.of(mockDocument));

        // Act
        Optional<Document> result = URLHandler.requestLinkAccess(url, currentDepth, maxDepth, visited, isInitialPage);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockDocument, result.get());
    }

    @Test
    void testRequestLinkAccessFailure() {
        // Arrange
        String url = "http://books.toscrape.com";
        int currentDepth = 1;
        int maxDepth = 3;
        List<String> visited = List.of();
        boolean isInitialPage = true;

        mockedUrlHandler.when(() -> URLHandler.requestLinkAccess(anyString(), anyInt(), anyInt(), anyList(), anyBoolean()))
                .thenReturn(Optional.empty());

        // Act
        Optional<Document> result = URLHandler.requestLinkAccess(url, currentDepth, maxDepth, visited, isInitialPage);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testIsDomainAllowedSuccess() {
        String url = "http://quotes.toscrape.com";

        mockedUrlHandler.when(() -> URLHandler.isDomainAllowed(anyString()))
                .thenReturn(true);

        boolean result = URLHandler.isDomainAllowed(url);

        assertTrue(result);
    }

    @Test
    void testIsDomainAllowedFailure() {
        String url = "http://not-allowed-domain.com";

        mockedUrlHandler.when(() -> URLHandler.isDomainAllowed(anyString()))
                .thenReturn(false);

        boolean result = URLHandler.isDomainAllowed(url);

        assertFalse(result);
    }

    @Test
    void testExtractFromURL() {
        String cssQuery = "h1, h2, h3, h4";
        Elements expectedElements = new Elements(new Element("h1").text("Header 1"));

        when(mockDocument.select(anyString())).thenReturn(expectedElements);

        Elements result = new URLHandler().extractFromURL(mockDocument, cssQuery);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Header 1", result.get(0).text());
    }
}
