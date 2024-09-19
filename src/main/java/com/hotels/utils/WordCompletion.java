package com.hotels.utils;

import com.hotels.storage.AVLTree;
import com.hotels.storage.Node;
import com.hotels.storage.Trie;
import com.hotels.storage.MinHeap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


import java.util.Arrays;
import java.util.Collections;

import com.hotels.utils.CSVFile;

// start of the application
public class WordCompletion {
    public static ArrayList<String> autocomplete(String[] words, String query) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word.toLowerCase());
        }
        return trie.suggestWords(query.toLowerCase());
    }
    public static void main(String[] args) {
        
        /*
         * TASK 2 Vocabulary
         */ 

        AVLTree tree = new AVLTree();
        
        /*
         * TASK 2 Vocabulary part A
         */
        // read the file and get all the words
        String fileData = CSVFile.readFile("hotel_details.csv");
        String[] words = CSVFile.getWords(fileData);
        Arrays.sort(words);
        
        // System.out.println("Words in the file: ");
        // for (String word : words) {
        //     System.out.println(word);
        // }

        /*
         * TASK 2 Vocabulary part B
         */
        // insert all the words into the AVL tree
        for (String word : words) {
            tree.insertElement(word.toLowerCase());
        }

        System.out.println("Total Words in the AVL tree: "+tree.getTotalNumberOfNodes());
        
        /*
         * TASK 2 Autocomplete Functionality:
         */ 

        // taking user input for searching the word
        System.out.println("Enter the word to search: ");
        Scanner sc = new Scanner(System.in); 
        String prefix = sc.nextLine();

        // tree.breathfirstsearch();
        // tree.preorderTraversal(); // print the AVL tree in preorder
        
        /*
         * TASK 2 Autocomplete Functionality PART A:
         */

        /*
         * search for the user input based on just the prefix in the AVL tree
         * This is different from normal search as in we are only interested in the prefix of the word not the whole word
         * For example, if the user enters "hot", the search should return the first node starting with "hot" like "hotel", "hotels", "hotels.com" etc.
         */ 
        Node searchedNode = tree.searchPrefix(prefix.toLowerCase());

        if(searchedNode == null) {
            System.out.println("The word is not found in the AVL tree");
            return;
        }
        
        // uncomment to print the sub tree of the searched node
        // System.out.println("_____________Sub Tree_____________");
        // tree.breathfirstsearch(searchedNode);
        // System.out.println(searchedNode.frequency);

        // print all the children of the searched node
        // System.out.println("Children of the searched node: ");
        // tree.inorderTraversal(searchedNode);

        /*
         * TASK 2 Autocomplete Functionality PART B:
         */


        /*
         * Ideally I would choose Max heap which will return the maximum frequency word first
         * but as the Task 2 Autocomplete Functionality PART B requires me to use min heap, I will use min heap
         */ 
        MinHeap minHeap = new MinHeap();
        minHeap.createHeapFromTree(searchedNode);
        

        // As min heap returns the minimum frequency word first, I will extract all the elements from the heap and store them in an arraylist
        ArrayList<Node> suggestions = new ArrayList<Node>();
        //Extract elements from the heap
        while (!minHeap.isEmpty()) {
            Node minNode = minHeap.extractMin();
            suggestions.add(0, minNode); // add the minNode to the start of the arraylist so that the arraylist is sorted based on frequency
            // System.out.println("Suggestion: " + minNode.element + " (" + minNode.frequency + ")");
        }

        // // I have all the elements in the arraylist but they are in opposite order min to max frequency
        // // but i need max to min frequency, so I will sort the arraylist based on frequency as requested in the task 2 Autocomplete Functionality PART B second sentence.
        // suggestions.sort(new Comparator<Node>() {
        //     @Override
        //     public int compare(Node n1, Node n2) {
        //         return -1*Integer.compare(n1.frequency, n2.frequency);
        //     }
        // });

        // // uncomment to sort based on string values instead of frequency
        // suggestions.sort(new Comparator<Node>() {
        //     @Override
        //     public int compare(Node n1, Node n2) {
        //         return n1.element.compareTo(n2.element);
        //     }
        // });

        // extract all the words with start with the prefix as they should get the priority
        // subtree also contains words with doesn't start with the prefix
        ArrayList<Node> suggestionsWithPrefix = new ArrayList<Node>();
        for(int i=0; i<suggestions.size(); i++) {;
            if(suggestions.get(i).element.startsWith(prefix)){
                //remove this element from the suggestions arraylist
                suggestionsWithPrefix.add(suggestions.get(i));
                suggestions.remove(suggestions.get(i));
            }
        }

        //printing all the final suggestions based on frequency of the words
        System.out.println("_____________Sorted Suggestions based on frequency_____________");
        for(Node suggestion: suggestionsWithPrefix) {
            System.out.println(suggestion.element + " (frequency: " + suggestion.frequency + ")");
        }
        for(Node suggestion: suggestions) {
            System.out.println(suggestion.element + " (frequency: " + suggestion.frequency + ")");
        }
    }
}