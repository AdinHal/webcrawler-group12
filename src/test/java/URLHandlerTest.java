import crawler.URLHandler;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class URLHandlerTest {

    URLHandler urlHandler = new URLHandler();

    @Test
    public void testLVSuccess() {
        String URL = "https://www.orf.at";

        assertTrue(urlHandler.isLinkReachable(URL));
    }

    @Test
    public void testLVFailure() {
        String URL = "https://www.hdajsdhjkashfkahkfjhjafhs.at";

        assertFalse(urlHandler.isLinkReachable(URL));
    }
}
