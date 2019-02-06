package lib2D;

import java.util.Random;

abstract public class BasicGeom {

    public static final double ZERO = 0.00001; // avoids problems with the resolution
    public static final double INFINITY = 99e99; // for managing infinty
    public static int RANGE = 100; //defines the work range. 2 * RANGE is the total width
    public static double PI = 3.14159265359; // PI constant

    public static boolean equal(double a, double b) {
        return (Math.abs(a - b) < ZERO);
    }

    public static Random randomSeed = new Random();
    public static int seed = randomSeed.nextInt();

    public static double min3(double a, double b, double c) {
        return (a < b ? (a < c ? a : c) : (b < c ? b : c));
    }

    public static double max3(double a, double b, double c) {
        return (a > b ? (a > c ? a : c) : (b > c ? b : c));
    }

}
