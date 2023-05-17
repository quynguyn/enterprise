import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static void main(String[] args) throws IOException {
        /// initialize();
        System.out.println(getPriceTechZones("Intel Core i5-11400 - 6C/12T 12MB Cache 4.40 GHz"));
        System.out.println(getPriceTechZones("MSI B560M PRO-E"));
        System.out.println(getPriceTechZones("Samsung DDR4 Desktop 16GB 2666MHz 1.2v"));
        System.out.println(getPriceTechZones("KimTigo M.2 NVMe PCIe 3×4 - 256GB"));
        System.out.println(getPriceTechZones("DeepCool PK450D"));
        System.out.println(getPriceTechZones("GIGABYTE AORUS Radeon RX 7900 XTX ELITE 24G"));
        System.out.println(getPriceTechZones("DeepCool CH510 Black"));

    }

    public static void initialize() throws IOException {
        // Gear VN getting data
        // CPU
        getData("https://gearvn.com/collections/cpu-bo-vi-xu-ly?page=");
        getDataTechZone("https://techzones.vn/cpu?pagenumber=");
        // VGA
        getData("https://gearvn.com/collections/vga-card-man-hinh?page=");
        getDataTechZone("https://techzones.vn/vga?pagenumber=");
        // SSD
        getData("https://gearvn.com/collections/ssd-o-cung-the-ran?page=");
        getDataTechZone("https://techzones.vn/ssd-gan-trong?pagenumber=");
        // Mother board
        getData("https://gearvn.com/collections/mainboard-bo-mach-chu?page=");
        getDataTechZone("https://techzones.vn/mainboard?pagenumber=");
        // RAM
        getData("https://gearvn.com/collections/ram-pc?page=");
        getDataTechZone("https://techzones.vn/memory-ram?pagenumber=");
        // Cooler
        getData("https://gearvn.com/collections/fan-rgb-tan-nhiet-pc?page=");
        getDataTechZone("https://techzones.vn/tan-nhiet?pagenumber=");
        // Power Supply
        getData("https://gearvn.com/collections/psu-nguon-may-tinh?page=");
        getDataTechZone("https://techzones.vn/psu?pagenumber=");
        // Case
        getData("https://gearvn.com/collections/case-thung-may-tinh?page=");
        getDataTechZone("https://techzones.vn/case?pagenumber=");
    }

    public static void getData(String urlLink) throws IOException {
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

                System.out.println("Name: " + productName + " | Price: " + productPrice + " | IMG: " + productImage);
            }

            currentPage++;
        }
    }

    public static String getPriceMemoryZone(String productName) throws IOException {
        String urlName = nameToURL(productName);
        String urlProduct = "https://memoryzone.com.vn/search?query=" + urlName
                + "&variantquery=variants_nested.title:(%27%27)%20AND%20variants_nested.variant_available:true";
        String price = "";

        Document currentPageDoc = Jsoup.connect(urlProduct).get();
        Elements productRows = currentPageDoc.select(".row .content_col");
        Element elementPrice = productRows.get(0).selectFirst(".special-price");
        price = elementPrice.text();
        return price;
    }

    public static String getPriceTechZones(String productName) throws IOException {
        String urlName = productName.toLowerCase().replaceAll(" - ", "-").replaceAll(" ", "-").replaceAll("/", "")
                .replaceAll("\\.", "").replaceAll("×", "");
        String urlProduct = "https://techzones.vn/" + urlName;
        System.out.println(urlProduct);
        Document currentPageDoc = Jsoup.connect(urlProduct).get();
        Element productRows = currentPageDoc.selectFirst(".price-option");
        String price = productRows.text();

        return price;
    }

    public static String nameToURL(String name) {
        String url = name;
        url = url.replaceAll(" ", "%20");
        return url;
    }

    public static void getDataTechZone(String urlLink) throws IOException {
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
            Elements productItems = currentPageDoc.select(".product-item");

            // Extract product names and prices and IMG
            for (Element productItem : productItems) {
                Element productNameElement = productItem.selectFirst(".product-name").selectFirst("a");
                String productName = productNameElement.text();
                Element productPriceElement = productItem.selectFirst(".product-price").selectFirst("div");
                String productPrice = "";
                Element productImageElement = productItem.selectFirst(".product-img").selectFirst("img");
                String productImage = productImageElement.attr("src");
                // System.out.println(productImage);

                if (productPriceElement != null) {
                    productPrice = productPriceElement.text();
                }

                System.out.println("Name: " + productName + " | Price: " + productPrice + " | IMG: " + productImage);
            }

            currentPage++;
        }
    }

}