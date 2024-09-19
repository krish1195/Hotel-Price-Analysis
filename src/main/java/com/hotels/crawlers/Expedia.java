package com.hotels.crawlers;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.openqa.selenium.WebElement;

// Harjot's Crawler
public class Expedia {
    public static ArrayList<Hotel> dummycrawl(String location) {
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        hotels.add(new Hotel("Hotel 1", "Address 1", "Location 1", "Price 1", "Rating 1", "Description 1"));
        hotels.add(new Hotel("Hotel 2", "Address 2", "Location 2", "Price 2", "Rating 2", "Description 2"));
        hotels.add(new Hotel("Hotel 3", "Address 3", "Location 3", "Price 3", "Rating 3", "Description 3"));
        hotels.add(new Hotel("Hotel 4", "Address 4", "Location 4", "Price 4", "Rating 4", "Description 4"));
        hotels.add(new Hotel("Hotel 5", "Address 5", "Location 5", "Price 5", "Rating 5", "Description 5"));
        return hotels;
    }
    public static ArrayList<Hotel> crawl(String location) {
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        WebDriver driver = new ChromeDriver();
        // driver.maanage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.expedia.ca/Hotel-Search?destination=" + location);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement showMore = driver.findElement(By.cssSelector(".uitk-layout-grid .uitk-layout-grid-item .uitk-layout-flex-justify-content-center .uitk-button"));
        showMore.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 5; i++) {
            js.executeScript("window.scrollBy(0, 600);");
            // explicit wait for 1 sec
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        WebElement hotelList = driver.findElement(By.cssSelector("[data-stid='property-listing-results']"));
        List<WebElement> hotels_crawl = hotelList.findElements(By.className("uitk-card"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        int count = 0;
        for (WebElement hotel : hotels_crawl) {
            try {
                count++;
                if (count > 20) {
                    break;
                }
                js.executeScript("window.scrollBy(0, 350);");
                // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                WebElement hotelCard = hotel.findElement(By.className("uitk-card-content-section"));
                // hotel name is an h3 element
                WebElement hotelName = hotelCard.findElement(By.tagName("h3"));
                // System.out.println("Property Name: " + hotelName.getText());

                // hotel location is hotelCard -> div[0] -> div[0] -> div[1]
                WebElement tlocation = hotelCard.findElement(By.cssSelector("div:nth-child(1) div:nth-child(1) div:nth-child(1) div:nth-child(2)"));
                // System.out.println("Location: " + tlocation.getText());
                // hotel price = data-test-id="price-summary-message-line"
                WebElement price = hotelCard.findElement(By.cssSelector("[data-test-id='price-summary-message-line']"));
                // System.out.println(price.getText());
                // System.out.println("-------------------------------------------------");
                // hotel_strings.add(hotelName.getText() + " " + tlocation.getText() + " " + price.getText());
                hotels.add(new Hotel(hotelName.getText(), tlocation.getText(), price.getText()));
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        driver.quit();
        return hotels;

    }
    public static void main(String[] args) {
        ArrayList<Hotel> hotels = crawl("Windsor");
        for (Hotel hotel : hotels) {
            Hotel.print(hotel);
        }
    }
}