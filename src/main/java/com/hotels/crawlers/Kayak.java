package com.hotels.crawlers;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.time.Duration;

public class Kayak {
    public static ArrayList<Hotel> crawl(String location) {
        ArrayList<Hotel> hotels = new ArrayList<>();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ca.kayak.com/hotels/Windsor,Ontario,Canada-p63083/2024-07-26/2024-07-27/2adults;map?sort=rank_a");
        // driver.get("https://www.ca.kayak.com/hotels/Windsor,Ontario,Canada-p63083/2024-07-01/2024-07-02/2adults?sort=rank_a");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        List<WebElement> hotel_name_web_element_list = driver.findElements(By.cssSelector("div.FLpo-hotel-name > a.FLpo-big-name"));
        List<WebElement> price_web_element_list = driver.findElements(By.cssSelector("div.zV27-price-section > div.c1XBO"));
        List<WebElement> image_web_element_list = driver.findElements(By.cssSelector("img.e9fk-photo"));

        if(price_web_element_list.size() == hotel_name_web_element_list.size() && hotel_name_web_element_list.size() == image_web_element_list.size()) {
            for (int web_element_index = 0; web_element_index < hotel_name_web_element_list.size(); web_element_index++) {
                String hotel_name_text = hotel_name_web_element_list.get(web_element_index).getText();
                String price_text = price_web_element_list.get(web_element_index).getText();
                String image_source_url_text = image_web_element_list.get(web_element_index).getAttribute("src");
                String image_alt_text_value = image_web_element_list.get(web_element_index).getAttribute("alt");
                hotels.add(new Hotel(hotel_name_text, "Address", location, price_text, "Rating", "Description"));
            }
        }
        driver.quit();
        return hotels;
    }
    public static void main(String[] args) {
        ArrayList<Hotel> hotels = crawl("windsor");
        for (Hotel hotel : hotels) {
            Hotel.print(hotel);
        }
    }
}
