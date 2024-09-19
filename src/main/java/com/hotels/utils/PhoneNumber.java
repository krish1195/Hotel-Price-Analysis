package com.hotels.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumber {
    public static void main(String[] args) {
        try {
            // Read the file
            FileReader in = new FileReader("hotel_details.csv");
            BufferedReader reader = new BufferedReader(in);
            String inputLine;
            StringBuilder finalContents = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                finalContents.append("\n").append(inputLine);
            }
            String s = finalContents.toString();

            String[] lines = s.split("\n");
            String regex = "^(\\+\\d{1,2}\\s?)?(\\(\\d{3}\\)|\\d{3})[-\\.\\s]?\\d{3}[-\\.\\s]?\\d{4}$";
            Pattern pattern = Pattern.compile(regex);

            for (int i = 1; i < lines.length; i++) { // Skip the header
                String[] columns = lines[i].split(",");
                String phoneNumber = columns[4].trim();
                Matcher matcher = pattern.matcher(phoneNumber);
                if (matcher.matches()) {
                    System.out.println("Yes: " + phoneNumber);
                } else {
                    System.out.println("No: " + phoneNumber);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
