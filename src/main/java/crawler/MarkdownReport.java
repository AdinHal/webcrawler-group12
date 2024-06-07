package crawler;

/*import java.io.FileWriter;
import java.io.IOException;

public class MarkdownReport {
    private StringBuilder report;

    public MarkdownReport(String startURL, int maxDepth) {
        report = new StringBuilder();
        report.append("input: <a>").append(startURL).append("</a> \n<br>depth: ")
                .append(maxDepth).append("\n<br>summary: \n");
    }

    public void addHeading(String heading, int depth) {
        report.append("\n").append(repeat("#", depth + 1)).append(" ").append(heading);
    }

    public void addLink(String url) {
        report.append("\n\n<br>----> link to <a>").append(url).append("</a>");
    }

    public void addBrokenLink(String url) {
        report.append("\n\n<br>--> broken link <a>").append(url).append("</a>");
    }

    public void saveReport(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(report.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String repeat(String str, int times) {
        StringBuilder repeated = new StringBuilder();
        for (int i = 0; i < times; i++) {
            repeated.append(str);
        }
        return repeated.toString();
    }
}*/

import crawler.models.CrawledPage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MarkdownReport {
    private static final String DEFAULT_OUTPUT_FILE = "report.md";

    public void write(List<CrawledPage> pages, String inputUrl, int depth, int additionalLinksCrawlDepth) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEFAULT_OUTPUT_FILE))) {
            writer.write("input: <a>" + inputUrl + "</a> \n<br>depth: " + depth + "\n<br>additional links depth: " + additionalLinksCrawlDepth + "\n");
            for (CrawledPage page : pages) {
                if (page != null) {
                    writePage(writer, page, 0);
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + DEFAULT_OUTPUT_FILE);
            e.printStackTrace();
        }
    }

    private void writePage(BufferedWriter writer, CrawledPage page, int depth) throws IOException {
        String indentation = repeat("-", depth * 4);
        writer.write("\n" + indentation + "# " + page.getTitle() + "\n");
        for (String heading : page.getHeadings()) {
            String[] headingParts = heading.split("##");
            int level = Integer.parseInt(headingParts[0]);
            writer.write(indentation + repeat("#", level) + " " + headingParts[1] + "\n");
        }
        for (String link : page.getLinks()) {
            if (isBrokenLink(link)) {
                writer.write("\n" + indentation + "--> broken link <a>" + link + "</a>\n");
            } else {
                writer.write("\n" + indentation + "--> link to <a>" + link + "</a>\n");
            }
        }
        writer.write("\n<br>\n");
    }

    private boolean isBrokenLink(String url) {
        // TODO: Link Validation sollte hier gecalled werden
        return url.contains("broken");
    }

    private String repeat(String str, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(str);
        }
        return builder.toString();
    }
}



