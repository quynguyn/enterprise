package com.enterprise.enterprise;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static void main(String[] args) throws IOException {
        //initialize();
        System.out.println(getPriceMemoryZone("NZXT H7 Elite White CM-H71EW-01"));
    }

    public static void initialize() throws IOException{
        // Gear VN getting data 
        // CPU
            getData("https://gearvn.com/collections/cpu-bo-vi-xu-ly?page=");
        // VGA
        getData("https://gearvn.com/collections/vga-card-man-hinh?page=");
        // SSD
            getData("https://gearvn.com/collections/ssd-o-cung-the-ran?page=");
        //Mother board 
            getData("https://gearvn.com/collections/mainboard-bo-mach-chu?page=");
        // RAM
            getData("https://gearvn.com/collections/ram-pc?page=");
        // Cooler 
            getData("https://gearvn.com/collections/fan-rgb-tan-nhiet-pc?page=");
        // Power Supply 
            getData("https://gearvn.com/collections/psu-nguon-may-tinh?page=");
        // Case 
            getData("https://gearvn.com/collections/case-thung-may-tinh?page=");
    }


    public static void getData(String urlLink) throws IOException{
        String baseUrl = urlLink;
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

            // Extract product names and prices and IMG
            for (Element productRow : productRows) {
                Element productNameElement = productRow.selectFirst(".product-row-name");
                String productName = productNameElement.text();
                Element productPriceElement = productRow.selectFirst(".product-row-sale");
                String productPrice = "";
                Element productImageElement = productRow.selectFirst("img.product-row-thumbnail");
                String productImage = productImageElement.attr("src");
                // System.out.println(productImage);

                if (productPriceElement != null) {
                    productPrice = productPriceElement.text();
                }

                System.out.println("Name: " + productName + " | Price: " + productPrice+ " | IMG: " + productImage);
            }

            currentPage++;
        }
    }

    public static String getPriceMemoryZone(String productName) throws IOException{
        String urlName = nameToURL(productName);
        String urlProduct = "https://memoryzone.com.vn/search?query=" + urlName + "&variantquery=variants_nested.title:(%27%27)%20AND%20variants_nested.variant_available:true";
        String price = "";

        Document currentPageDoc = Jsoup.connect(urlProduct).get();
        Elements productRows = currentPageDoc.select(".row .content_col");
        Element elementPrice = productRows.get(0).selectFirst(".special-price");
        price = elementPrice.text();
        return price;
    }

    public static String nameToURL(String name) {
        String url = name;
        url = url.replaceAll(" ", "%20");
        return url;
    }

}
