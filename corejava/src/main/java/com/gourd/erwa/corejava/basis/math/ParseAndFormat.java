package com.gourd.erwa.util.corejava.basis.math;

import java.text.DecimalFormat;

/**
 * @author wei.Li by 14-8-26.
 */
public class ParseAndFormat {

    /**
     * 基础语法
     */
    private static void grammarBasis() {

        String str = "GourdErwa";
        String intStr = "1990";
        int mouth = 6;

        // 如何将字符串转换为数字？
        Integer.parseInt(intStr);

        // 如何将数字转换为十六进制字符串？
        Integer.parseInt(intStr, 16);

        // 如何将字节串转换为十六进制字符串？
        Integer.toString(mouth, 16);

        /**
         * <a>http://book.51cto.com/art/200907/140729.htm</a>
         */

        //如何对浮点数打印出指定小数位数
        double v = 00.87654321;
        String.format("%.3f", v);//0.877
        new DecimalFormat("#0.00")
                .format(v);//0.88

        //如何将浮点数输出为指定位数的科学计数法
        new DecimalFormat("0.000E0")
                .format(123456789876543.23456789);//1.235E14

        // 如何将数字输出为每三位逗号分隔的格式，例如“1,234,467”？
        new DecimalFormat("###,###.###")
                .format(11122233445.34567);//11,122,233,445.346

        //如何将字符串转换为Boolean对象
        Boolean.valueOf("str");//false

        // 如何将一个四字节转换为一个整数？以及反过来？
        //一个整数4位 32字节
        //byte  1位 8字节
        int x = 123456;
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) ((x >> (i * 8)) & 0xFF);
        }
        //[64,-30,1,0]

        byte[] bytes = new byte[]{64, -30, 1, 0};


    }
}
