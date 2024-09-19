package com.hotels.utils;

import java.util.HashMap;
import java.util.Map;

// This class represents a single node in the Trie.
// Each node has a map of children nodes and a flag to indicate if it represents the end of a word.
class TrieNodeAutocorrect {
    // A map that holds child nodes for each character.
    Map<Character, TrieNodeAutocorrect> childrenNodesMap = new HashMap<>();
    // A flag that indicates if this node marks the end of a word.
    boolean isEndOfWordFlag;
}

// This class represents the entire Trie data structure.
public class TrieAutocorrect {
    // The root node of the Trie, from where all operations start.
    private final TrieNodeAutocorrect rootNode;

    // Constructor to initialize the Trie with a root node.
    public TrieAutocorrect() {
        rootNode = new TrieNodeAutocorrect();
    }

    // Method to insert a word into the Trie.
    // It takes a string wordToInsert and adds each character to the Trie.
    public void insert(String wordToInsert) {
        // Start from the root node.
        TrieNodeAutocorrect currentNode = rootNode;
        // Iterate over each character in the word.
        for (char character : wordToInsert.toCharArray()) {
            // If the character is not already a child of the current node,
            // create a new TrieNode and put it in the children nodes map.
            currentNode = currentNode.childrenNodesMap.computeIfAbsent(character, c -> new TrieNodeAutocorrect());
        }
        // After adding all characters, mark the last node as the end of the word.
        currentNode.isEndOfWordFlag = true;
    }

    // Method to search for a word in the Trie.
    // It takes a string wordToSearch and checks if it exists in the Trie.
    public boolean search(String wordToSearch) {
        // Start from the root node.
        TrieNodeAutocorrect currentNode = rootNode;
        // Iterate over each character in the word.
        for (char character : wordToSearch.toCharArray()) {
            // Move to the child node corresponding to the character.
            currentNode = currentNode.childrenNodesMap.get(character);
            // If the character is not found, the word does not exist in the Trie.
            if (currentNode == null) {
                return false;
            }
        }
        // After checking all characters, return true if the last node is marked as the end of the word.
        return currentNode.isEndOfWordFlag;
    }
}
