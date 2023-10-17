package edu.hw1;

public final class Task3 {

    private Task3() {
    }

    public static boolean isNestable(int[] firstArray, int[] secondArray) {
        int firstLength = firstArray.length;
        int secondLength = secondArray.length;
        boolean result;
        if (firstLength == 0) {
            return true;
        } else if (secondLength == 0) {
            return false;
        } else {
            int minInFirstArray = getMinInArray(firstArray);
            int maxInFirstArray = getMaxInArray(firstArray);
            int minInSecondArray = getMinInArray(secondArray);
            int maxInSecondArray = getMaxInArray(secondArray);
            result = (minInFirstArray > minInSecondArray) && (maxInFirstArray < maxInSecondArray);
        }
        return result;
    }

    private static int getMinInArray(int[] array) {
        int minValue = array[0];
        for (int value : array) {
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }

    private static int getMaxInArray(int[] array) {
        int maxValue = array[0];
        for (int value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}
