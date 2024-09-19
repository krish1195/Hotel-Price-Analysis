package com.hotels.utils;

import java.io.*;
import java.util.*;

public class SearchFrequencyTracker {
    // HashMap to store the search frequency of each word
    private HashMap<String, Integer> searchFrequencyMap;
    // Constant for the data file to store search frequencies
    private static final String DATA_FILE = "search_data.txt";

    // Constructor to initialize the frequency tracker
    public SearchFrequencyTracker() {
        searchFrequencyMap = new HashMap<>();
        loadSearchData(); // Load existing search data from file
    }

    // Method to track a search for a given word
    public void search(String word) {
        // Check if the input word is empty
        if (word.trim().isEmpty()) {
            printStatement("Empty input is not allowed. Please enter a valid word.");
            return; // Exit if the input is empty
        }
        
        // Increment the search frequency for the word
        searchFrequencyMap.put(word, searchFrequencyMap.getOrDefault(word, 0) + 1);
        // Print the current frequency of the word
        printStatement("Frequency of '" + word + "': " + searchFrequencyMap.get(word));
    }

    // Method to get the search frequency of a specific word
    public int getSearchFrequency(String word) {
        return searchFrequencyMap.getOrDefault(word, 0); // Return frequency or 0 if not found
    }

    // Method to get the top N searches using a max heap (PriorityQueue)
    public List<Map.Entry<String, Integer>> getTopSearches(int topN) {
        // Create a max heap to sort entries based on frequency
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(
                (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Add all entries from the map to the heap
        for (Map.Entry<String, Integer> entry : searchFrequencyMap.entrySet()) {
            maxHeap.offer(entry);
        }

        // List to hold the top N entries
        List<Map.Entry<String, Integer>> topEntries = new ArrayList<>();
        int count = 0;
        // Retrieve the top N entries from the heap
        while (!maxHeap.isEmpty() && count < topN) {
            topEntries.add(maxHeap.poll()); // Get the entry with the highest frequency
            count++; // Increment count
        }

        return topEntries; // Return the list of top entries
    }

    // Method to load search frequency data from a file
    private void loadSearchData() {
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            // Read each line from the file
            while ((line = br.readLine()) != null) {
                // Split the line into key-value pairs
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    // Add the word and its frequency to the map
                    searchFrequencyMap.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            // Handle the case where the file is not found
            printStatement("No previous data found. Starting fresh.");
        }
    }

    // Method to save search frequency data to a file
    private void saveSearchData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE))) {
            // Write each entry in the map to the file
            for (Map.Entry<String, Integer> entry : searchFrequencyMap.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine(); // New line after each entry
            }
        } catch (IOException e) {
            // Handle any IO exceptions during saving
            printStatement("Error saving search data: " + e.getMessage());
        }
    }

    // Utility method to print a statement to the console
    public static void printStatement(String s) {
        System.out.println(s);
    }

    // Main method to run the application
    public static void main(String[] args) {
        SearchFrequencyTracker tracker = new SearchFrequencyTracker(); // Create an instance of the tracker
        Scanner scanner = new Scanner(System.in); // Initialize the scanner for user input

        while (true) {
            printStatement("Enter the word(s) to track (or 'exit' to quit):");
            String input = scanner.nextLine(); // Read user input

            // Check for exit condition
            if (input.equalsIgnoreCase("exit")) {
                tracker.saveSearchData(); // Save data before exiting
                printStatement("Have a nice day ..........");
                break; // Exit the loop
            }

            // Split the input by whitespace and track each word separately
            String[] words = input.split("\\s+");
            for (String word : words) {
                tracker.search(word); // Track each word
            }
            
            printStatement("Top 10 searches:"); // Prompt for top searches
            List<Map.Entry<String, Integer>> topSearches = tracker.getTopSearches(10); // Get top searches
            int rank = 1;
            // Print the top searches in a numbered format
            for (Map.Entry<String, Integer> entry : topSearches) {
                printStatement(rank + ") " + entry.getKey() + ": " + entry.getValue());
                rank++; // Increment rank
            }
        }

        scanner.close(); // Close the scanner resource
    }
}
