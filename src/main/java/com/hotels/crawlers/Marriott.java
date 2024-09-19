package com.hotels.crawlers;
import java.util.ArrayList;
import java.util.List;
import org.openqa.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
// Naman's Crawler
public class Marriott {
    public static ArrayList<Hotel> crawl(String location) {
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        WebDriver driver = new ChromeDriver();
        // driver.get("https://www.marriott.com")  
        driver.get("https://www.marriott.com/search/findHotels.mi?fromDate=08/01/2024&toDate=08/02/2024&destinationAddress.address="+location);
        // https://www.marriott.com/search/findHotels.mi?fromDate=08/01/2024&toDate=08/02/2024&destinationAddress.city=Windsor&view=list&isInternalSearch=true&vsInitialRequest=false&searchType=InCity&for-hotels-nearme=Near&collapseAccordian=is-hidden&singleSearch=true&singleSearchAutoSuggest=Unmatched&flexibleDateSearchRateDisplay=false&isSearch=true&isRateCalendar=true&recordsPerPage=40&destinationAddress.latitude=42.3149367&destinationAddress.location=Windsor,+ON,+Canada&destinationAddress.stateProvince=ON&searchRadius=50&destinationAddress.placeId=ChIJa4hPtcEqO4gRUdz8J5XUbLY&destinationAddress.country=CA&destinationAddress.address=Windsor,+ON,+Canada&destinationAddress.secondaryText=ON,+CA&destinationAddress.mainText=Windsor&isTransient=true&destinationAddress.longitude=-83.03636329999999&initialRequest=true&fromToDate=07/24/2024&fromToDate_submit=08/02/2024&toDateDefaultFormat=08/02/2024&fromDateDefaultFormat=08/01/2024&flexibleDateSearch=false&isHideFlexibleDateCalendar=false&t-start=2024-08-01&t-end=2024-08-02&isFlexibleDatesOptionSelected=false&lengthOfStay=1&roomCount=1&numAdultsPerRoom=1&childrenCount=0&clusterCode=none&numberOfRooms=1&destinationAddress.destination=Windsor,+ON,+Canada#/6/
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        List<WebElement> hotel_Elements = driver.findElements(By.className("property-card"));
        for (WebElement hotel_Element : hotel_Elements) {
            // Extracting name of hotel
            String hotel_Name = hotel_Element.findElement(By.className("title-primary")).getText();

            // Extracting price of hotel
            String hotel_Price = hotel_Element.findElement(By.className("m-price")).getText();
            // t-font-alt-xs description-container
            String hotel_description = hotel_Element.findElement(By.className("description-container")).getText();
            hotels.add(new Hotel(hotel_Name, location, location, hotel_Price, "unknown", hotel_description));
            // Writing data of hotel to XLSX file
            // Row row = sheet.createRow(row_Index[0]++);
            // row.createCell(0).setCellValue(hotel_Name);
            // row.createCell(1).setCellValue(hotel_Price);

            // Printing data of hotel in the specified format
            // printStatement("Hotel Name: " + hotel_Name);
            // printStatement("Hotel Price: " + hotel_Price + " USD");
            // printStatement("");
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
