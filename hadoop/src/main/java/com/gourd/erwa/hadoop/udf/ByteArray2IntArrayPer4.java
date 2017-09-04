package com.gourd.erwa.hadoop.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author wei.Li by 2017/7/10
 */
@Description(name = "ByteArray2IntArrayPer4", value = "_FUNC_(str) - text->去除空格、全部转小写、转为16进制->byte[]->每4个 byte 转换为一个整数=>ArrayList<Integer> ")
public class ByteArray2IntArrayPer4 extends UDF {

    private static final String HEX_STR = "0123456789abcdef";

    /**
     * 十六进制转换字符串
     *
     * @param hexStr str Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @return String 对应的字符串
     */
    private static String hexStr2Str(String hexStr) {

        char[] hex = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = HEX_STR.indexOf(hex[2 * i]) * 16;
            n += HEX_STR.indexOf(hex[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * text->去除空格、全部转小写、转为16进制->byte[]->每4个 byte 转换为一个整数=>ArrayList<Integer>
     *
     * @param text 带转换字符串
     * @return 整型集合
     */
    public static ArrayList<Integer> evaluate(String text) {

        if (Objects.isNull(text)) {
            return new ArrayList<>();
        }

        text = hexStr2Str(text.replace(" ", "").toLowerCase());

        final byte[] bytes = text.getBytes();
        if (bytes.length % 4 != 0) {
            throw new IllegalArgumentException(text + ", [bytes.length % 4 != 0]");
        }

        final int length = bytes.length / 4;

        final ArrayList<Integer> r = new ArrayList<>(length);

        for (int i = 0; i < bytes.length; ) {

            int v = (bytes[i] & 0xFF)
                    | ((bytes[i + 1] & 0xFF) << 8)
                    | ((bytes[i + 2] & 0xFF) << 16)
                    | ((bytes[i + 3] & 0xFF) << 24);
            r.add(v);
            i += 4;
        }

        return r;
    }
}
