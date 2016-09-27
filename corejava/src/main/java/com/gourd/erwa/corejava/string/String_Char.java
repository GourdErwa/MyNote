package com.gourd.erwa.util.corejava.string;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: lw
 * Date: 14-5-5
 */
public class String_Char {

    /**
     * 按字节截取字符串，如果截取的最后一个是汉字，则舍弃
     *
     * @param string 截取的字符串
     * @param index  截取的长度
     * @return 最终处理
     * @throws UnsupportedEncodingException 编码转换错误
     */
    private static String subString(String string, int index)
            throws UnsupportedEncodingException {

        if (StringUtils.isEmpty(string)) {
            return "";
        }

        //转 GBK ，一个汉字对应2字节
        byte[] bytes = string.getBytes("GBK");
        int length = bytes.length;
        index = length >= index ? index : length;

        //截取字节个数 <= 1
        if (index <= 1) {
            return (bytes[0] < 0) ? string.substring(0, 1) : "";
        }

        int temp = 0;
        for (int i = 0; i < index; i++) {
            //一个汉字2字节 temp+2 ，奇数代表截取到半个汉字
            if (bytes[i] < 0)
                temp++;
        }

        //如果截取到半个汉字则-1，舍弃这个汉字
        temp = (temp % 2 == 0) ? (temp / 2) : (temp - 1 / 2);

        return string.substring(0, index - temp);
    }


    public static void main(String[] args)
            throws UnsupportedEncodingException {
        String s = "My名字是GourdErwa！";
        System.out.println(String_Char.subString(s, 3));
    }
}
