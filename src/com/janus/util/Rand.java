package com.janus.util;

import java.security.SecureRandom;

public class Rand {
    private static final SecureRandom gen = new SecureRandom();

    /**
     * Random generator for hitting a certain probability.
     *
     * @param denom The 1/denom rate that this returns true.
     * @return Whether the chance was hit.
     */
    public static boolean hit(int denom) {
        return gen.nextInt(denom) == 0;
    }

    public static int hitRandom(int denom) {
        return gen.nextInt(denom);
    }

    /**
     * Random generator for hitting a certain probability.
     *
     * @param percent The percent rate that this returns true.
     * @return Whether the chance was hit.
     */
    public static boolean hitPercent(int percent) {
        return gen.nextInt(100) < percent;
    }

    /**
     * Returns a random element of a generic array.
     *
     * @param array
     * @return random element from the array
     */
    public static <T> T ranElement(T[] array) {
        return array[gen.nextInt(array.length)];
    }

    /**
     * Returns a random element of a int[] array.
     *
     * @param array
     * @return random element from the array
     */
    public static int ranElement(int[] array) {
        return array[gen.nextInt(array.length)];
    }

    /**
     * Returns a random element of a boolean[] array.
     *
     * @param array
     * @return random element from the array
     */
    public static boolean ranElement(boolean[] array) {
        return array[gen.nextInt(array.length)];
    }

    /**
     * Returns a random element of a double[] array.
     *
     * @param array
     * @return random element from the array
     */
    public static double ranElement(double[] array) {
        return array[gen.nextInt(array.length)];
    }

    /**
     * Returns a random element of a float[] array.
     *
     * @param array
     * @return random element from the array
     */
    public static float ranElement(float[] array) {
        return array[gen.nextInt(array.length)];
    }

    public static int inclusive(int low, int high) {
        return low + gen.nextInt(high - low + 1);
    }

    public static boolean nextBoolean() {
        return gen.nextBoolean();
    }

    /**
     * @param bound
     * @return random number between 0 inclusive and bound exclusive
     */
    public static int nextInt(int bound) {
        return gen.nextInt(bound);
    }

    /**
     * @param bound
     * @return random number between 0 inclusive and bound inclusive
     */
    public static int nextInclusiveInt(int bound) {
        return gen.nextInt(bound + 1);
    }

    public static double nextDouble() {
        return gen.nextDouble();
    }
}
