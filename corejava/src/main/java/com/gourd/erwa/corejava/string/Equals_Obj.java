package com.gourd.erwa.util.corejava.string;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-7
 */
public class Equals_Obj {

    private static void aVoid() {
        String s0 = "a";
        String s1 = "abc";
        String s2 = "abc";
        String s3 = new String("abc");
        String s4 = s0 + "bc";

        System.out.println(s1 == s2);
        System.out.println(s1 == s4);
        System.out.println(s1 == "abc");
        System.out.println(s1 == s3);
        System.out.println(s3 == "abc");
        System.out.println(s3 == s4);
        System.out.println(s1.equals(s3));
        System.out.println(s1 == s2.intern());
        System.out.println(s3 == s3.intern());
        System.out.println(s1 == s4.intern());
        System.out.println(s4 == s4.intern());
        System.out.println(s1.intern() == s4.intern());
    }

    public static void main(String[] args) {
        //analogThreads();
    }
}
