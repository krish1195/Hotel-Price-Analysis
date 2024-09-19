package com.hotels.utils;

import java.util.Arrays;

public class MergeSort{
    /**
     * Sorts an array using the merge sort algorithm.
     *
     * @param inputArray      the array to be sorted
     * @param inputDistances  the corresponding distances array
     */
    public static void mergeSortAlgorithm(String[] inputArray, int[] inputDistances) {
        if (inputArray.length <= 1) {
            return;
        }

        int mid = inputArray.length / 2;
        String[] leftArray = Arrays.copyOfRange(inputArray, 0, mid);
        String[] rightArray = Arrays.copyOfRange(inputArray, mid, inputArray.length);
        int[] leftDistances = Arrays.copyOfRange(inputDistances, 0, mid);
        int[] rightDistances = Arrays.copyOfRange(inputDistances, mid, inputDistances.length);

        mergeSortAlgorithm(leftArray, leftDistances);
        mergeSortAlgorithm(rightArray, rightDistances);

        mergeArrays(inputArray, inputDistances, leftArray, leftDistances, rightArray, rightDistances);
    }

    /**
     * Merges two sorted arrays into one sorted array.
     *
     * @param inputArray       the main array to be merged into
     * @param inputDistances   the corresponding distances array
     * @param leftArray        the left array to be merged
     * @param leftDistances    the corresponding distances array for the left array
     * @param rightArray       the right array to be merged
     * @param rightDistances   the corresponding distances array for the right array
     */
    private static void mergeArrays(String[] inputArray, int[] inputDistances, String[] leftArray, int[] leftDistances, String[] rightArray, int[] rightDistances) {
        int i = 0, j = 0, k = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftDistances[i] <= rightDistances[j]) {
                inputArray[k] = leftArray[i];
                inputDistances[k] = leftDistances[i];
                i++;
            } else {
                inputArray[k] = rightArray[j];
                inputDistances[k] = rightDistances[j];
                j++;
            }
            k++;
        }

        while (i < leftArray.length) {
            inputArray[k] = leftArray[i];
            inputDistances[k] = leftDistances[i];
            i++;
            k++;
        }

        while (j < rightArray.length) {
            inputArray[k] = rightArray[j];
            inputDistances[k] = rightDistances[j];
            j++;
            k++;
        }
    }
}
