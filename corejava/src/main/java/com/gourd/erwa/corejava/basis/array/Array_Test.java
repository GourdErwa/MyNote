package com.gourd.erwa.util.corejava.basis.array;


import java.util.Arrays;

/**
 * @author wei.Li by 14-8-26.
 */
public class Array_Test {

    private static void aVoid() {
        int[] intArray = {1, 2, 3, 4, 5};
        String intArrayString = Arrays.toString(intArray);
        // print directly will print reference value
        System.out.println(Arrays.toString(intArray));
        // [I@7150bd4d
        System.out.println(intArrayString);// [1, 2, 3, 4, 5]
    }

    public static void main(String[] args) {
        aVoid();
    }
}
