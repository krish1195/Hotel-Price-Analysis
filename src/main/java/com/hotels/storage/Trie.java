package com.hotels.storage;

import java.util.ArrayList;

public class Trie {
    TrieNode root;
    /*
     * Constructor
     * it initializes the root node with 128 child nodes each for a character of ascii value 0-127,
     * our input file and search string is in ascii value 0-127
     */
    public Trie() { root = new TrieNode(); }

    /*
     * The function that insert a key into the Trie
     * it initialized the root node and then iterates through the key to insert the key into the trie
     */
    public void insert(String key)
    {
        TrieNode currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 0;
            if (currentNode.childNode[index] == null) {
                currentNode.childNode[index]
                    = new TrieNode();
            }
            currentNode = currentNode.childNode[index];
        }
        currentNode.wordEnd = true;
    }
    /*
     * Function to search for a key in the Trie
     * it iterates through the key to search for the key in the trie
     * if the key is found it returns true else false
     */
    public boolean search(String key)
    {
        TrieNode currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 0;
            if (currentNode.childNode[index] == null) {
                return false;
            }
            currentNode = currentNode.childNode[index];
        }
        return currentNode.wordEnd;
    }

    /*
     * A function that return all the words that start with the given prefix
     * it iterates through the prefix to search for the prefix in the trie
     * if the prefix is found it calls the printAllWords function to print all the words that start with the given prefix
     * else it prints "No words found with this prefix"
     */
    public ArrayList<String> suggestWords(String prefix) {
        // ArrayList<String> words = new ArrayList<>();
        TrieNode currentNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 0;

            if (currentNode == null || currentNode.childNode[index] == null) {
                // System.out.println("No words found with this prefix");
                return new ArrayList<>();
            }
            currentNode = currentNode.childNode[index];
        }
        // printAllWords(currentNode, prefix);
        return returnAllWords(currentNode, prefix);
    }
    public ArrayList<String> returnAllWords(TrieNode node, String prefix) {
        ArrayList<String> words = new ArrayList<>();
        if (node == null) {
            return words;
        }
        if (node.wordEnd) {
            words.add(prefix);
        }
        for (int i = 0; i < 128; i++) {
            if (node.childNode[i] != null) {
                words.addAll(returnAllWords(node.childNode[i], prefix + (char)(i)));
            }
        }
        return words;
    }
    /*
     * A recursive function that prints all the words that start with the given prefix
     * it iterates through the child nodes of the prefix node and calls itself recursively to print all the words that start with the given prefix
    */
    public void printAllWords(TrieNode node, String prefix) {
        if (node.wordEnd) {
            System.out.println(prefix);
        }
        for (int i = 0; i < 128; i++) {
            if (node.childNode[i] != null) {
                printAllWords(node.childNode[i], prefix + (char)(i));
            }
        }
    }
}

