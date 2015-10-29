package com.test.liw;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.RandomAccess;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-1
 * Time: 10:51
 */
public class Test_1 implements Serializable, Cloneable, RandomAccess {


    public static String string_1 = "string_1";

    static {
        string_1 = "string _3";
    }

    public String string_2;

    {
        System.out.println(string_2);

        string_2 = getString_2();

        System.out.println(string_2);
    }


    Test_1() {
        System.out.println(Test_1.string_1);
        System.out.println(string_2);
    }

    private static int aVoid() {
        for (int i = -1; i < 1; i++) {
            try {
                System.out.println("for -> i is:" + i);
                int temp = 10 / i;
            } catch (Exception e) {
                i = 1;
                return i;
            } finally {
                i++;
                System.out.println("finally ...  i is" + i);
                return i;
            }
        }
        return 0;
    }

    public static void setString() {

        String s = "上海库存Web服务器";

        try {
            s = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        System.out.println(s);

        try {
            s = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(s);
    }

    public static void main(String[] args) {
        // new Test_1();
        //setString();

        aVoid1();
    }

    public static void aVoid1() {
        String file = "/lw/a.txt";
        String ssss = "";
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
                ssss += s + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ssss);

    }

    private String getString_1() {
        return "method result String_1";
    }

    private String getString_2() {
        return "method result String_2";
    }
}
