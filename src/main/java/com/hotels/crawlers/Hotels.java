package com.hotels.crawlers;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;

public class Hotels {
    public static ArrayList<Hotel> crawl(String location) {
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        WebDriver driver = new ChromeDriver();

        String checkinDate = "2024-07-12";
        String checkoutDate = "2024-07-15";

        int adults = 2;
        int rooms = 1;
        String hotelsComUri = buildHotelsComUrl(location, checkinDate, checkoutDate, adults, rooms);
        driver.get(hotelsComUri.toString());
        // List<Hotel> hotels = new ArrayList<Hotel>();
        try {
            WebElement destinationInput = driver.findElement(By.className("uitk-form-field-trigger"));
            destinationInput.click();
            // sleep for 1 sec
            Thread.sleep(1000);
            WebElement destinationInput2 = driver.findElement(By.id("destination_form_field"));
            destinationInput2.sendKeys(location);
            Thread.sleep(1000);

            // click on autocomplete suggestion
            WebElement autoCompleteInput = driver
                .findElement(By.className("destination_form_field-result-item-button"));
            autoCompleteInput.click();
            Thread.sleep(1000);

            // click on search button
            WebElement searchButton = driver.findElement(By.id("search_button"));
            searchButton.click();

            // waut for page to load results to be visible
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            Thread.sleep(5000);
            WebElement propertyListingResults = driver
            .findElement(By.xpath("//div[@data-stid='property-listing-results']"));

            // find all child elements representing individual hotel listings
            List<WebElement> hotelCards = propertyListingResults
                            .findElements(By.xpath(".//div[@data-stid='lodging-card-responsive']"));
            
            // need this to scroll page, otherwise the browser will not fully load all hotel cards
            JavascriptExecutor js = (JavascriptExecutor) driver;
            int hotel_count = 0;
            // iterate through each hotel card and extract the hotel name
            for (WebElement hotelCard : hotelCards) {
                hotel_count++;
                if (hotel_count > 10) {
                    break;
                }
                // System.out.println("test");
                js.executeScript("window.scrollBy(0, 350);");
                String hotelName = "";
                try {
                    WebElement hotelNameElement = hotelCard
                                    .findElement(By.xpath(".//h3[contains(@class, 'uitk-heading')]"));
                            hotelName = hotelNameElement.getText();
                } catch (Exception e) {
                    // do nothing
                }
                String price = "";
                try {
                    WebElement priceElement = hotelCard
                                    .findElement(By.xpath(".//span[@aria-hidden='true']/div[contains(@class, 'uitk-text-emphasis-theme')]"));
                            String fullPriceText = priceElement.getText(); // This will get the text "CA $259"
                            price = fullPriceText.replaceAll("[^\\d]", ""); // Remove non-numeric characters to get "259"
                } catch (Exception e) {
                    // do nothing
                }

                if(price.equals("")) {
                }
                
                // String imageUrl = "";

                // Extract Hotel location
                String location_temp = "";
                try {
                    WebElement locationElement = hotelCard.findElement(
                                    By.xpath(".//div[contains(@class, 'uitk-type-300') and not(@aria-hidden='true')]"));
                    location_temp = locationElement.getText();
                } catch (Exception e) {
                    // Handle the exception or log it
                }

                String descriptionHeading = "";
                String description = "";
                String rating = "";
                try {
                    WebElement ratingElement = hotelCard.findElement(
                            By.xpath(".//span[contains(@class, 'is-visually-hidden') and contains(text(), 'out of')]"));
                    String ratingText = ratingElement.getText(); // This gets the text "8.4 out of 10"
                    String numericRating = ratingText.split(" ")[0]; // Extract "8.4"
                    double ratingValue = Double.parseDouble(numericRating);
                    int percentageRating = (int) (ratingValue * 10); // Convert to percentage
                    rating = percentageRating + "%"; // "84%"
                } catch (Exception e) {
                    // Handle the exception or log it
                }
                hotels.add(new Hotel(hotelName, price, location_temp, rating));
                js.executeScript("window.scrollBy(0, 250);");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
        return hotels;
    }
    public static String buildHotelsComUrl(String city, String checkinDate, String checkoutDate, int adults,
            int rooms) {
        String baseUrl = "https://ca.hotels.com/Hotel-Search";
        return String.format(
                "%s?regionId=&latLong=&flexibility=0_DAY&d1=%s&startDate=%s&d2=%s&endDate=%s&adults=%d&rooms=%d",
                baseUrl, checkinDate, checkinDate, checkoutDate, checkoutDate, adults, rooms);
    }
    public static void main(String[] args) {
        ArrayList<Hotel> hotels = crawl("Windsor");
        for (Hotel hotel : hotels) {
            Hotel.print(hotel);
        }
    }
}
