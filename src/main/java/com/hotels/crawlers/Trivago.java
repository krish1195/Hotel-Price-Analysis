package com.hotels.crawlers;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class Trivago {
    public static ArrayList<Hotel> crawl(String location) {
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        WebDriver driver = new ChromeDriver();
        String url = "https://www.trivago.ca/en-CA/srl/hotels-windsor-canada?search=200-37652;dr-20240701-20240703";
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        List<WebElement> hotelTrivagoElements = driver.findElements(By.cssSelector("li[data-testid='accommodation-list-element']"));

        try {
            for(WebElement hotelTrivagoElement : hotelTrivagoElements) {
                String hotelTrivagoName = hotelTrivagoElement.findElement(By.cssSelector("span[itemprop='name']"))
						.getText();
                // String hotelTrivagoPrice = hotelTrivagoElement
                // .findElement(By.cssSelector("span[data-testid='recommended-price']")).getText();

                // hotels.add(new Hotel(hotelTrivagoName, "Address", location, hotelTrivagoPrice, "Rating", "Description"));
                hotels.add(new Hotel(hotelTrivagoName, "Address", location, "Price", "Rating", "Description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return hotels;
    }
    public static void main(String[] args) {
        ArrayList<Hotel> hotels = crawl("Windsor");
        for (Hotel hotel : hotels) {
            Hotel.print(hotel);
        }
    }
}
