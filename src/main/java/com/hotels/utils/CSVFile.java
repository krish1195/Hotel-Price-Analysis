package com.hotels.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import com.hotels.crawlers.Hotel;

public class CSVFile {
    //read the file hotel_details.csv and return all the words in that file
    public static String[] getWords(String fileData) {
        //split the file data into words based on commas spaces hiphen dot and new line and slash
        return fileData.split("[,\\s\\-\\.\\n\\/]+");
    }
    //read file function with specifc file name
    public static String readFile(String fileName){
        byte[] bytes = null;
        try{
            bytes = Files.readAllBytes(Paths.get(fileName));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static ArrayList<Hotel> load_hotels_description(String fileName) {
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        try {
            // csv structure = name, price, location, rating, phone number
            String fileData = readFile(fileName);
            String[] lines = fileData.split("\n");
            for (int i = 1; i < lines.length; i++) {
                String[] columns = lines[i].split(",");
                String name = columns[0].trim();
                String price = columns[1].trim();
                String location = columns[2].trim();
                String rating = columns[3].trim();
                String about = columns[4].trim();
                hotels.add(new Hotel(name, price, location, rating, about, true));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public static ArrayList<Hotel> load_hotels(String fileName) {
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        try {
            String fileData = readFile(fileName);
            String[] lines = fileData.split("\n");
            for (int i = 1; i < lines.length; i++) {
                String[] columns = lines[i].split(",");
                String name = columns[0].trim();
                String price = columns[1].trim();
                String location = columns[2].trim();
                String rating = columns[3].trim();
                String phone = columns[4].trim();
                hotels.add(new Hotel(name, price, location, rating, phone));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotels;
    } 

    public static void saveToCSV(List<Hotel> hotels, String fileName) {
        boolean fileExists = new File(fileName).exists();
        try (FileWriter writer = new FileWriter(fileName, true)) { // true to append
            if (!fileExists) {
                writer.append(
                        "Hotel Name,Description Heading,Description,Price,Image URL,Location,Rating,Review Count\n");
            }
            for (Hotel hotel : hotels) {
                writer.append(hotel.name).append(',')
                        .append(hotel.descriptionHeading).append(',')
                        .append(hotel.description).append(',')
                        .append(hotel.price).append(',')
                        .append(hotel.imageUrl).append(',')
                        .append(hotel.location).append(',')
                        .append(hotel.rating).append(',')
                        .append(hotel.reviewCount).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToCSVEssentials(List<Hotel> hotels, String fileName) {
        boolean fileExists = new File(fileName).exists();
        try (FileWriter writer = new FileWriter(fileName, true)) { // true to append
            if (!fileExists) {
                writer.append(
                        "name,price,location,rating\n");
            }
            for (Hotel hotel : hotels) {
                writer.append(hotel.name).append(',')
                        .append(hotel.price).append(',')
                        .append(hotel.location).append(',')
                        .append(hotel.rating).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
