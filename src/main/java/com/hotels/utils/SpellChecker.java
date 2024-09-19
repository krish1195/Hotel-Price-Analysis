package com.hotels.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpellChecker {
    // This is a Trie data structure that will be used to store the vocabulary words
    private TrieAutocorrect trieDataStructureForVocabularyWords;
    // This is a list that will hold all the vocabulary words
    private List<String> listOfVocabularyWords;

    // Constructor to initialize the SpellChecker
    public SpellChecker() {
        // Initialize the Trie
        trieDataStructureForVocabularyWords = new TrieAutocorrect();
        // Initialize the vocabulary list
        listOfVocabularyWords = new ArrayList<>();
    }

    public void loadVocabulary(String[] vocabularyWords) {
        // reset vocabulary list
        // listOfVocabularyWords = new ArrayList<>();
        // trieDataStructureForVocabularyWords = new Trie();
        for (String vocabularyWord : vocabularyWords) {
            String trimmedAndLowercasedWord = vocabularyWord.trim().toLowerCase();
            listOfVocabularyWords.add(trimmedAndLowercasedWord);
            // Insert the word in Trie
            trieDataStructureForVocabularyWords.insert(trimmedAndLowercasedWord);
        }
    }

    public void loadVocabulary(String pathToVocabularyFile) throws IOException {
        BufferedReader bufferedReaderForVocabularyFile = new BufferedReader(new FileReader(pathToVocabularyFile));
        String lineFromVocabularyFile;

        while ((lineFromVocabularyFile = bufferedReaderForVocabularyFile.readLine()) != null) {
            String[] wordsFromLine = lineFromVocabularyFile.split(",");
            for (String wordFromLine : wordsFromLine) {
                String[] individualWords = wordFromLine.split("\\s+");
                for (String individualWord : individualWords) {
                    // Trim any leading or trailing spaces and convert to lowercase
                    String trimmedAndLowercasedWord = individualWord.trim().toLowerCase();
                    listOfVocabularyWords.add(trimmedAndLowercasedWord);
                    // Insert the word in Trie
                    trieDataStructureForVocabularyWords.insert(trimmedAndLowercasedWord);
                }
            }
        }
        // Close the BufferedReader
        bufferedReaderForVocabularyFile.close();
    }

    public boolean checkSpelling(String textToCheck) {
        String[] wordsToCheck = textToCheck.split("\\s+");
        for (String wordToCheck : wordsToCheck) {
            if (!trieDataStructureForVocabularyWords.search(wordToCheck.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    // Method to suggest words similar to a given word
    public List<String> suggestWords(String wordToSuggest, int maximumNumberOfSuggestions) {
        List<String> listOfSuggestions = new ArrayList<>();
        // List to hold the edit distances
        List<Integer> listOfEditDistances = new ArrayList<>();

        // Loop through the vocabulary to calculate edit distances
        for (String vocabularyWord : listOfVocabularyWords) {
            int editDistance = Editdistance.computeEditDistance(wordToSuggest, vocabularyWord);
            listOfSuggestions.add(vocabularyWord);
            listOfEditDistances.add(editDistance);
        }

        String[] arrayOfSuggestions = listOfSuggestions.toArray(new String[0]);
        // Convert the distances list to an array
        int[] arrayOfEditDistances = listOfEditDistances.stream().mapToInt(Integer::intValue).toArray();

        MergeSort.mergeSortAlgorithm(arrayOfSuggestions, arrayOfEditDistances);

        // List to hold the sorted suggestions
        List<String> sortedListOfSuggestions = new ArrayList<>();

        for (int i = 0; i < Math.min(arrayOfSuggestions.length, maximumNumberOfSuggestions); i++) {
            sortedListOfSuggestions.add(arrayOfSuggestions[i]);
        }

        return sortedListOfSuggestions;
    }

    public void saveVocabulary(String pathToSaveVocabularyFile) throws IOException {
        FileWriter fileWriterForVocabularyFile = new FileWriter(pathToSaveVocabularyFile);
        for (String vocabularyWord : listOfVocabularyWords) {
            fileWriterForVocabularyFile.write(vocabularyWord + "\n");
        }
        fileWriterForVocabularyFile.close();
    }
    public static List<String> autocorrect(String[] reference, String query) {
        SpellChecker spellCheckerInstance = new SpellChecker();
        spellCheckerInstance.loadVocabulary(reference);
        return spellCheckerInstance.suggestWords(query, 1);
    }
    // Main method to run the SpellChecker
    public static void main(String[] args) {
        SpellChecker spellCheckerInstance = new SpellChecker();
        Scanner scannerForUserInput = new Scanner(System.in);

        try {
            System.out.print("Enter the path to the vocabulary CSV file: ");
            String pathToVocabularyFile = scannerForUserInput.nextLine();

            spellCheckerInstance.loadVocabulary(pathToVocabularyFile);

            while (true) {
                System.out.print("Enter a word or phrase to check (or type 'exit' to quit): ");
                String userInput = scannerForUserInput.nextLine();

                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }

                String[] wordsToCheckFromUserInput = userInput.split("\\s+");
                boolean allWordsAreCorrectlySpelled = true;
                for (String wordToCheckFromUserInput : wordsToCheckFromUserInput) {
                    if (!spellCheckerInstance.checkSpelling(wordToCheckFromUserInput)) {
                        allWordsAreCorrectlySpelled = false;
                        // Suggest corrections for the misspelled word
                        List<String> suggestionsForMisspelledWord = spellCheckerInstance.suggestWords(wordToCheckFromUserInput, 1);
                        System.out.println(wordToCheckFromUserInput + " is not spelled correctly. Did you mean:");
                        for (String suggestion : suggestionsForMisspelledWord) {
                            System.out.println(suggestion);
                        }
                    }
                }

                if (allWordsAreCorrectlySpelled) {
                    System.out.println(userInput + " is spelled correctly.");
                }
            }
        } catch (IOException exceptionForFileOperations) {
            exceptionForFileOperations.printStackTrace();
        } finally {
            scannerForUserInput.close();
        }
    }
}
// Programmed by Krish
