import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class ConfigTest {
/*
    @Test
    public void testConfigInit(){
        String testCrawlURL = "http://httpbin.org/html";
        int testCrawlDepth = 3;
        int testAdditionalDepth = 2;
        List<String> testDomains = Arrays.asList("httpbin.org,html");

        Config config = new Config(testCrawlURL,testCrawlDepth,testAdditionalDepth,testDomains);

        assertAll("Set:",
                () -> assertEquals((Object) testCrawlURL, config.getCrawlUrl(), "should match"),
                () -> assertEquals(testCrawlDepth, config.getCrawlDepth(), "should match"),
                () -> assertEquals(testAdditionalDepth, config.getCrawlAdditionalLinksDepth(), "should match"),
                () -> assertEquals(testDomains, config.getCrawlDomains(), "should match")
        );
    }*/
}
