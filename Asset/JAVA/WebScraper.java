package Asset.JAVA;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebScraper {
    public static void main(String[] args) {
        try {
            // Connect to the website and get the HTML content
            Document doc = Jsoup.connect("https://www.example.com/").get();
            
            // Get the page title and print it out
            Element title = doc.select("title").first();
            System.out.println("Page title: " + title.text());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}