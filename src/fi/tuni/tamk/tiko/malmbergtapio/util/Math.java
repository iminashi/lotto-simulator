package fi.tuni.tamk.tiko.malmbergtapio.util;

/**
* Contains methods for basic mathematic operations.
*
* @author Tapio Malmberg
*/
public class Math {
    /**
    * Returns a random number in the range [min, max]
    *
    * @param min the inclusive lower bound for the random number.
    * @param max the inclusive upper bound for the random number.
    * @return a random integer between min and max.
    */
    public static int getRandom(int min, int max) {
        return min + (int) (java.lang.Math.random() * ((max - min) + 1));
    }
}