package com.hotels.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.hotels.crawlers.Hotel;

public class ReverseIndexing {
    public static void reverseIndexing() {
        while(true) {
            System.out.println("Enter search query:");
            String input = System.console().readLine();
            ArrayList<Hotel> hotels = CSVFile.load_hotels_description("assignment1.csv");
            // create a hashmap of words and their corresponding hotels from descriptions
            HashMap<String, ArrayList<Hotel>> word_hotel_map = new HashMap<String, ArrayList<Hotel>>();
            for (Hotel hotel : hotels) {
                if (hotel.description == null) {
                    continue;
                }
                String[] words = hotel.description.split(" ");
                for (String word : words) {
                    if (word_hotel_map.containsKey(word)) {
                        word_hotel_map.get(word).add(hotel);
                    } else {
                        ArrayList<Hotel> hotel_list = new ArrayList<Hotel>();
                        hotel_list.add(hotel);
                        word_hotel_map.put(word, hotel_list);
                    }
                }
            }
            ArrayList<Hotel> filtered_hotels = new ArrayList<Hotel>();
            String[] query_words = input.split(" ");
            for (String query_word : query_words) {
                if (word_hotel_map.containsKey(query_word)) {
                    for (Hotel hotel : word_hotel_map.get(query_word)) {
                        if (!filtered_hotels.contains(hotel)) {
                            filtered_hotels.add(hotel);
                        }
                    }
                }
            }
            // see if input is present in word_hotel_map
            if (filtered_hotels.size() == 0) {
                System.out.println("No hotels found with given search query");
            }
            else {
                for (Hotel hotel : filtered_hotels) {
                    Hotel.print(hotel);
                }
            }
        }
        // return filtered_hotels;
    }
}
