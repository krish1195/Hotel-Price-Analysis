package com.hotels.storage;

// create Node class to design the structure of the AVL Tree Node  
public class Node  
{      
    public String element;  
    public int h;  //for height  
    public int frequency = 0;
    public Node leftChild;  
    public Node rightChild;  
      
    //default constructor to create null node  
    public Node()  
    {  
        leftChild = null;  
        rightChild = null;  
        element = "";  
        h = 0;  
    }  
    // parameterized constructor  
    public Node(String element)  
    {  
        leftChild = null;  
        rightChild = null;  
        this.element = element;
        this.frequency = 1;
        h = 0;  
    }
    public Node(String element, int frequency)  
    {  
        leftChild = null;  
        rightChild = null;  
        this.element = element;  
        this.frequency = frequency;
        h = 0;  
    }
}  