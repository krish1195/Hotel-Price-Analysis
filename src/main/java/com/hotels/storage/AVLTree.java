package com.hotels.storage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;  

  
// create class AVLTree for constructing AVL Tree  
public class AVLTree  
{  
    private Node rootNode;       
  
    //Constructor to set null value to the rootNode  
    public AVLTree()  
    {  
        rootNode = null;  
    }  
      
    //create removeAll() method to make AVL Tree empty  
    public void removeAll()  
    {  
        rootNode = null;  
    }  
      
    // create checkEmpty() method to check whether the AVL Tree is empty or not  
    public boolean checkEmpty()  
    {  
        if(rootNode == null)  
            return true;  
        else   
            return false;  
    }  
      
    // create insertElement() to insert element to to the AVL Tree  
    public void insertElement(String element)  
    {  
        rootNode = insertElement(element, rootNode);  
    }  
      
    //create getHeight() method to get the height of the AVL Tree  
    private int getHeight(Node node )  
    {  
        return node == null ? -1 : node.h;  
    }  
      
    //create maxNode() method to get the maximum height from left and right node  
    private int getMaxHeight(int leftNodeHeight, int rightNodeHeight)  
    {  
    return leftNodeHeight > rightNodeHeight ? leftNodeHeight : rightNodeHeight;  
    }  
      
      
    //create insertElement() method to insert data in the AVL Tree recursively   
    private Node insertElement(String element, Node node)  
    {  
        //check whether the node is null or not  
        if (node == null)  
            node = new Node(element);  
        //insert a node in case when the given element is lesser than the element of the root node  
        else if (element.compareTo(node.element)<0)  
        {  
            node.leftChild = insertElement( element, node.leftChild );  
            if( getHeight( node.leftChild ) - getHeight( node.rightChild ) == 2 )  
                if( element.compareTo(node.leftChild.element)<0 )  
                    node = rotateWithLeftChild( node );  
                else  
                    node = doubleWithLeftChild( node );  
        }  
        else if( element.compareTo(node.element)>0 )  
        {  
            node.rightChild = insertElement( element, node.rightChild );  
            if( getHeight( node.rightChild ) - getHeight( node.leftChild ) == 2 )  
                if( element.compareTo(node.rightChild.element)>0)  
                    node = rotateWithRightChild( node );  
                else  
                    node = doubleWithRightChild( node );  
        }  
        else  
            node.frequency++;  // if the element is already present in the tree, we will do nothing   
        node.h = getMaxHeight( getHeight( node.leftChild ), getHeight( node.rightChild ) ) + 1;  
          
        return node;  
          
    }  
      
    // creating rotateWithLeftChild() method to perform rotation of binary tree node with left child        
    private Node rotateWithLeftChild(Node node2)  
    {  
        Node node1 = node2.leftChild;  
        node2.leftChild = node1.rightChild;  
        node1.rightChild = node2;  
        node2.h = getMaxHeight( getHeight( node2.leftChild ), getHeight( node2.rightChild ) ) + 1;  
        node1.h = getMaxHeight( getHeight( node1.leftChild ), node2.h ) + 1;  
        return node1;  
    }  
  
    // creating rotateWithRightChild() method to perform rotation of binary tree node with right child        
    private Node rotateWithRightChild(Node node1)  
    {  
        Node node2 = node1.rightChild;  
        node1.rightChild = node2.leftChild;  
        node2.leftChild = node1;  
        node1.h = getMaxHeight( getHeight( node1.leftChild ), getHeight( node1.rightChild ) ) + 1;  
        node2.h = getMaxHeight( getHeight( node2.rightChild ), node1.h ) + 1;  
        return node2;  
    }  
  
    //create doubleWithLeftChild() method to perform double rotation of binary tree node. This method first rotate the left child with its right child, and after that, node3 with the new left child  
    private Node doubleWithLeftChild(Node node3)  
    {  
        node3.leftChild = rotateWithRightChild( node3.leftChild );  
        return rotateWithLeftChild( node3 );  
    }  
  
    //create doubleWithRightChild() method to perform double rotation of binary tree node. This method first rotate the right child with its left child and after that node1 with the new right child  
    private Node doubleWithRightChild(Node node1)  
    {  
        node1.rightChild = rotateWithLeftChild( node1.rightChild );  
        return rotateWithRightChild( node1 );  
    }      
  
    //create getTotalNumberOfNodes() method to get total number of nodes in the AVL Tree  
    public int getTotalNumberOfNodes()  
    {  
        return getTotalNumberOfNodes(rootNode);  
    }  
    private int getTotalNumberOfNodes(Node head)  
    {  
        if (head == null)  
            return 0;  
        else  
        {  
            int length = 1;  
            length = length + getTotalNumberOfNodes(head.leftChild);  
            length = length + getTotalNumberOfNodes(head.rightChild);  
            return length;  
        }  
    }  
  
    //create searchElement() method to find an element in the AVL Tree  
    public Node searchElement(String element)  
    {  
        return searchElement(rootNode, element);  
    }  
  
    private Node searchElement(Node head, String element)  
    {  
        Node check = null;  
        while ((head != null) && check==null)  
        {  
            String headElement = head.element;  
            if (element.compareTo(headElement)<0)  
                head = head.leftChild;  
            else if (element.compareTo(headElement)>0)  
                head = head.rightChild;  
            else  
            {  
                check = head;
                break;
            }  
            check = searchElement(head, element);  
        }  
        return check;  
    }  
    //create searchElement() method to find an element in the AVL Tree  
    public Node searchPrefix(String element)  
    {  
        return searchPrefix(rootNode, element);  
    }  
  
    private Node searchPrefix(Node head, String element)  
    {  
        Node check = null;  
        while ((head != null) && check==null)  
        {  
            String headElement = head.element;
            if (headElement.startsWith(element)==true) {
                check = head;
                break;
            }
            if (element.compareTo(headElement)<0)  
                head = head.leftChild;  
            else if (element.compareTo(headElement)>0)  
                head = head.rightChild;  
            else  
            {  
                check = head;
                break;
            }  
            check = searchElement(head, element);  
        }  
        return check;  
    }  
    
    // create inorderTraversal() method for traversing AVL Tree in in-order form  
    public void inorderTraversal()  
    {  
        inorderTraversal(rootNode);  
    }  
    public void inorderTraversal(Node head)  
    {  
        if (head != null)  
        {  
            inorderTraversal(head.leftChild);  
            System.out.println(head.element+" frequency:"+head.frequency);  
            inorderTraversal(head.rightChild);  
        }  
    }  
  
    // create preorderTraversal() method for traversing AVL Tree in pre-order form  
    public void preorderTraversal()  
    {  
        preorderTraversal(rootNode);  
    }  
    public void preorderTraversal(Node head)  
    {  
        if (head != null)  
        {  
            System.out.print(head.element+" ");  
            preorderTraversal(head.leftChild);               
            preorderTraversal(head.rightChild);  
        }  
    }  
      
    // create postorderTraversal() method for traversing AVL Tree in post-order form  
    public void postorderTraversal()  
    {  
        postorderTraversal(rootNode);  
    }  
      
    private void postorderTraversal(Node head)  
    {  
        if (head != null)  
        {  
            postorderTraversal(head.leftChild);               
            postorderTraversal(head.rightChild);  
            System.out.print(head.element+" ");  
        }  
    }      
    
    public void breathfirstsearch() {
        breathfirstsearch(rootNode);
    }
    public void breathfirstsearch(Node head) {
        if (head == null) {
            return;
        }
        int x = 1;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        int i=1;
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.element + " ");
            if (current.leftChild != null) {
                queue.add(current.leftChild);
            }
            if (current.rightChild != null) {
                queue.add(current.rightChild);
            }
            if(i==x*2-1){
                System.out.println();
                x=x*2;
            }
            i++;
        }
        System.out.println();
    }
}  
  
// create AVLTree class to construct AVL Tree  
// public class MYAVLTree 
// {  
//     //main() method starts  
//     public static void main(String[] args)  
//     {              
//         //creating Scanner class object to get input from user  
//         Scanner sc = new Scanner(System.in);  
  
//         // create object of AVLTree class object for costructing AVL Tree  
//         AVLTree tree = new AVLTree();   
  
//         char choice;    //initialize a character type variable to choice   
          
//         // perform operation of AVL Tree using switch  
//         do      
//         {  
//             System.out.println("\nSelect an operation:\n");  
//             System.out.println("1. Insert a node");  
//             System.out.println("2. Search a node");  
//             System.out.println("3. Get total number of nodes in AVL Tree");  
//             System.out.println("4. Is AVL Tree empty?");  
//             System.out.println("5. Remove all nodes from AVL Tree");  
//             System.out.println("6. Display AVL Tree in Post order");  
//             System.out.println("7. Display AVL Tree in Pre order");  
//             System.out.println("8. Display AVL Tree in In order");  
  
//             //get choice from user  
//             int ch = sc.nextInt();
//             String val = sc.nextLine();
//             switch (ch)  
//             { 
//                 case 1 :   
//                     System.out.println("Please enter an element to insert in AVL Tree");  
//                     val = sc.nextLine();
//                     tree.insertElement( val );                       
//                     break;                            
//                 case 2 :   
//                     System.out.println("Enter integer element to search");  
//                     val = sc.nextLine();
//                     System.out.println(tree.searchElement( val ));  
//                     break;                                            
//                 case 3 :   
//                     System.out.println(tree.getTotalNumberOfNodes());  
//                     break;       
//                 case 4 :   
//                     System.out.println(tree.checkEmpty());  
//                     break;       
//                 case 5 :   
//                     tree.removeAll();  
//                     System.out.println("\nTree Cleared successfully");  
//                     break;  
//                 case 6 :   
//                     System.out.println("\nDisplay AVL Tree in Post order");  
//                     tree.postorderTraversal();  
//                     break;  
//                 case 7 :   
//                     System.out.println("\nDisplay AVL Tree in Pre order");  
//                     tree.preorderTraversal();  
//                     break;  
//                 case 8 :   
//                     System.out.println("\nDisplay AVL Tree in In order");  
//                     tree.inorderTraversal();  
//                     break;  
//                 default :   
//                     System.out.println("\n ");  
//                     break;      
//             }  
//             System.out.println("\nPress 'y' or 'Y' to continue \n");  
//             choice = sc.next().charAt(0);                          
//         } while (choice == 'Y'|| choice == 'y');         
//     }  
// }  