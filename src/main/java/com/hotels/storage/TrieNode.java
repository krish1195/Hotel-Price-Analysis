package com.hotels.storage;

public class TrieNode {
    TrieNode[] childNode;
    boolean wordEnd;

    TrieNode()
    {
        childNode = new TrieNode[128];
        wordEnd = false;
    }
}