package fi.tuni.tamk.tiko.malmbergtapio.util;

/**
 * Contains utility methods for working with arrays.
 *
 * @author Tapio Malmberg
 */
public class Arrays {
    /**
     * Converts an array of strings into an array of integers.
     *
     * The conversion is done using the Integer.parseInt method.
     *
     * @param array a string array to be converted into an integer array.
     * @return an array of integers.
     * @throws NumberFormatException if a string in the array cannot be converted
     *                               into an integer.
     */
    public static int[] toIntArray(String[] array) {
        int[] output = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            output[i] = Integer.parseInt(array[i]);
        }

        return output;
    }

    /**
     * Converts an array of integers into an array of strings.
     * 
     * The conversion is done using the Integer.toString method.
     *
     * @param array an integer array to be converted into a string array.
     * @return an array of strings.
     */
    public static String[] toStringArray(int[] array) {
        String[] output = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            output[i] = Integer.toString(array[i]);
        }

        return output;
    }

    /**
     * Checks if a given integer is found in the given array of integers.
     *
     * @param value the integer that will be searched for.
     * @param array the array to be searched.
     * @return a boolean indicating if the value was found in the array.
     */
    public static boolean contains(int value, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }

        return false;
    }

    /**
     * Counts the number of same values contained in two arrays.
     *
     * Assumes that both arrays contain unique values.
     *
     * @param array1 the first array.
     * @param array2 the second array.
     * @return the number of same values contained in both arrays.
     */
    public static int countSameValues(int[] array1, int[] array2) {
        int sameValuesCount = 0;

        for (int i = 0; i < array1.length; i++) {
            if (contains(array1[i], array2)) {
                sameValuesCount++;
            }
        }

        return sameValuesCount;
    }

    /**
     * Sorts an array of integers into ascending order.
     *
     * The algorithm used is selection sort.
     *
     * @param array the array to be sorted.
     * @return a copy of the input array, sorted in ascending order.
     */
    public static int[] sort(int[] array) {
        // Copy the input array into a new array
        int[] sortedArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            sortedArray[i] = array[i];
        }

        for (int i = 0; i < sortedArray.length; i++) {
            int min = sortedArray[i];
            int minIndex = i;

            // Look for the smallest number in the tail
            for (int j = i + 1; j < sortedArray.length; j++) {
                if (sortedArray[j] < min) {
                    min = sortedArray[j];
                    minIndex = j;
                }
            }

            // Switch the head with the smallest number
            int temp = sortedArray[i];
            sortedArray[i] = sortedArray[minIndex];
            sortedArray[minIndex] = temp;
        }

        return sortedArray;
    }

    /**
     * Converts an array of integers into strings that are padded left
     * with the chosen character.
     * 
     * @param array an array of integers.
     * @param padChar the character used for the padding.
     * @param padToLength the length the numbers will padded to.
     * @return the padded array of strings.
     */
    public static String[] padLeft(int[] array, char padChar, int padToLength) {
        String[] output = toStringArray(array);

        for (int i = 0; i < output.length; i++) {
            String str = output[i];

            while(str.length() < padToLength) {
                str = padChar + str;
            }

            output[i] = str;
        }

        return output;
    }
}