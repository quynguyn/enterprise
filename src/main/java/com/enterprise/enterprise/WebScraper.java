import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class WebScraper {
    public static void main(String[] args) throws IOException {
        initialize();
    }

    public static void initialize() throws IOException {
        // Gear VN getting data
        // CPU
        Gson gson = new Gson();

        FileWriter CpuWriter = new FileWriter("cpu.json");
        FileWriter GpuWriter = new FileWriter("gpu.json");
        FileWriter MainWriter = new FileWriter("main.json");
        FileWriter RamWriter = new FileWriter("ram.json");
        FileWriter ssdWriter = new FileWriter("ssd.json");
        FileWriter airCoolerWriter = new FileWriter("airCooler.json");
        FileWriter liquidCoolerWriter = new FileWriter("liquidCooler.json");
        FileWriter psuWriter = new FileWriter("psu.json");
        FileWriter caseWriter = new FileWriter("case.json");
        gson.toJson(getDataTechZone("https://techzones.vn/cpu?pagenumber="), CpuWriter);
        // VGA
        gson.toJson(getDataTechZone("https://techzones.vn/vga?pagenumber="), GpuWriter);
        // SSD
        gson.toJson(getDataTechZone("https://techzones.vn/ssd-gan-trong?pagenumber="), ssdWriter);
        // Mother board
        gson.toJson(getDataTechZone("https://techzones.vn/mainboard?pagenumber="), MainWriter);
        // RAM
        gson.toJson(getDataTechZone("https://techzones.vn/memory-ram?pagenumber="), RamWriter);
        // Cooler
        gson.toJson(getDataTechZone("https://techzones.vn/tan-nhiet-khi?pagenumber="), airCoolerWriter);
        gson.toJson(getDataTechZone("https://techzones.vn/tan-nhiet-nuoc-all-in-one?pagenumber="), liquidCoolerWriter);
        // Power Supply
        gson.toJson(getDataTechZone("https://techzones.vn/psu?pagenumber="), psuWriter);
        // Case
        gson.toJson(getDataTechZone("https://techzones.vn/case?pagenumber="), caseWriter);

        CpuWriter.close();
        GpuWriter.close();
        MainWriter.close();
        RamWriter.close();
        ssdWriter.close();
        airCoolerWriter.close();
        liquidCoolerWriter.close();
        psuWriter.close();
        caseWriter.close();
    }

    public static List<ResultModel> getDataTechZone(String urlLink) throws IOException {
        String baseUrl = urlLink;
        int currentPage = 1;

        // Get the total number of pages
        Document firstPage = Jsoup.connect(baseUrl + currentPage).get();
        Element pagination = firstPage.selectFirst(".ajaxresponsewrap");
        List<ResultModel> item = new ArrayList<ResultModel>();

        // Iterate through all pages
        while (pagination.selectFirst(".btn-loadmore") != null || currentPage < 6) {
            Document currentPageDoc = Jsoup.connect(baseUrl + currentPage).get();
            Elements productItems = currentPageDoc.select(".product-item");

            // Extract product names and prices
            for (Element productItem : productItems) {
                Element productNameElement = productItem.selectFirst(".product-name").selectFirst("a");
                String productName = productNameElement.text();
                Element productPriceElement = productItem.selectFirst(".product-price").selectFirst("div");
                String[] productPrice;
                if (productPriceElement != null) {
                    productPrice = productPriceElement.text().split(" ");
                    String price = productPrice[0];
                    if (productPrice.length < 4) {
                        continue;
                    }
                    if (productPrice[0].equals("Giá:")) {
                        price = productPrice[3];
                    }
                    item.add(new ResultModel("techzones", productName, Integer.parseInt(price.replaceAll("\\.", ""))));
                }
                // System.out.println("Name: " + productName + " | Price: " + productPrice + " |
                // Current page: " + currentPage);

            }

            currentPage++;
            pagination = Jsoup.connect(urlLink + currentPage).get().selectFirst(".ajaxresponsewrap");
        }

        return item;

    }

}

class ResultModel {
    public String Web;
    public String Name;
    public Integer Price;

    public ResultModel(String web, String name, Integer price) {
        Name = name;
        Web = web;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public Integer getPrice() {
        return Price;
    }

    public String getWeb() {
        return Web;
    }
}