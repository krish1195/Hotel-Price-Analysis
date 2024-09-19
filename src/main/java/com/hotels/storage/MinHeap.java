// MinHeap class to manage heap operations
package com.hotels.storage;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    private List<Node> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    // Insert a new node into the heap
    public void insert(Node node) {
        heap.add(node);
        int currentIndex = heap.size() - 1;
        heapifyUp(currentIndex);
    }

    // Heapify up to maintain the min-heap property
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            Node currentNode = heap.get(index);
            Node parentNode = heap.get(parentIndex);

            if (currentNode.frequency < parentNode.frequency) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    // Extract the minimum node (root) from the heap
    public Node extractMin() {
        if (heap.isEmpty()) {
            return null;
        }
        Node minNode = heap.get(0);
        Node lastNode = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastNode);
            heapifyDown(0);
        }

        return minNode;
    }

    // Heapify down to maintain the min-heap property
    private void heapifyDown(int index) {
        int leftChildIndex;
        int rightChildIndex;
        int smallestIndex;

        while (true) {
            leftChildIndex = 2 * index + 1;
            rightChildIndex = 2 * index + 2;
            smallestIndex = index;

            if (leftChildIndex < heap.size() && heap.get(leftChildIndex).frequency < heap.get(smallestIndex).frequency) {
                smallestIndex = leftChildIndex;
            }

            if (rightChildIndex < heap.size() && heap.get(rightChildIndex).frequency < heap.get(smallestIndex).frequency) {
                smallestIndex = rightChildIndex;
            }

            if (smallestIndex != index) {
                swap(index, smallestIndex);
                index = smallestIndex;
            } else {
                break;
            }
        }
    }

    // Swap two nodes in the heap
    private void swap(int index1, int index2) {
        Node temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    // Get the size of the heap
    public int size() {
        return heap.size();
    }

    // Check if the heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void createHeapFromTree(Node node) {
        if (node == null) {
            return;
        }
        new Node(node.element, node.frequency);
        insert(node);
        createHeapFromTree(node.leftChild);
        createHeapFromTree(node.rightChild);
    }
}

// // Example usage
// public class MinHeapExample {
//     public static void main(String[] args) {
//         MinHeap minHeap = new MinHeap();

//         // Insert elements into the heap
//         minHeap.insert(new Node("apple", 5));
//         minHeap.insert(new Node("banana", 2));
//         minHeap.insert(new Node("cherry", 7));
//         minHeap.insert(new Node("date", 1));
//         minHeap.insert(new Node("elderberry", 3));

//         // Extract elements from the heap
//         while (!minHeap.isEmpty()) {
//             Node minNode = minHeap.extractMin();
//             System.out.println("Extracted: " + minNode.element + " (" + minNode.frequency + ")");
//         }
//     }
// }