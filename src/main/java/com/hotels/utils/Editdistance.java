 package com.hotels.utils;

public class Editdistance {
    public static int computeEditDistance(String firstWord, String secondWord) {
        int[][] distanceTable = new int[firstWord.length() + 1][secondWord.length() + 1];

        for (int rowIndex = 0; rowIndex <= firstWord.length(); rowIndex++) {
            for (int columnIndex = 0; columnIndex <= secondWord.length(); columnIndex++) {
                if (rowIndex == 0) {
                    distanceTable[rowIndex][columnIndex] = columnIndex;
                } else if (columnIndex == 0) {
                    distanceTable[rowIndex][columnIndex] = rowIndex;
                } else {
                    int substitutionCost = (firstWord.charAt(rowIndex - 1) == secondWord.charAt(columnIndex - 1) ? 0 : 1);
                    distanceTable[rowIndex][columnIndex] = Math.min(
                            distanceTable[rowIndex - 1][columnIndex - 1] + substitutionCost,
                            Math.min(distanceTable[rowIndex - 1][columnIndex] + 1,
                                    distanceTable[rowIndex][columnIndex - 1] + 1));
                }
            }
        }
        return distanceTable[firstWord.length()][secondWord.length()];
    }
}
