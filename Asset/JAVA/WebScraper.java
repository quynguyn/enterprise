package Asset.JAVA;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static void main(String[] args) throws IOException {
        String baseUrl = "https://gearvn.com/collections/cpu-bo-vi-xu-ly?page=";
        int currentPage = 1;
        int totalPages = 1;

        // Get the total number of pages
        Document firstPage = Jsoup.connect(baseUrl + currentPage).get();
        Element pagination = firstPage.selectFirst(".pagination");
        if (pagination != null) {
            Elements pageLinks = pagination.select("a[href]");
            if (pageLinks.size() > 0) {
                String lastPageUrl = pageLinks.last().attr("href");
                String[] lastPageUrlParts = lastPageUrl.split("=");
                totalPages = Integer.parseInt(lastPageUrlParts[lastPageUrlParts.length - 1]);
            }
        }

        // Iterate through all pages
        while (currentPage <= totalPages) {
            Document currentPageDoc = Jsoup.connect(baseUrl + currentPage).get();
            Elements productRows = currentPageDoc.select(".product-row");

            // Extract product names and prices
            for (Element productRow : productRows) {
                Element productNameElement = productRow.selectFirst(".product-row-name");
                String productName = productNameElement.text();
                Element productPriceElement = productRow.selectFirst(".product-row-sale");
                String productPrice = "";
                if (productPriceElement != null) {
                    productPrice = productPriceElement.text();
                }
                System.out.println("Name: " + productName + " | Price: " + productPrice);
            }

            currentPage++;
        }
    }
}
