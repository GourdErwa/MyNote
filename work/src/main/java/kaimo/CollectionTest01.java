/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package kaimo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.Li by 15/8/10
 */
public class CollectionTest01 {


    public static final List<String> LIST_01 = new ArrayList<>();
    public static final List<String> LIST_02 = new ArrayList<>();

    // init
    static {

        LIST_01.add("1");
        LIST_01.add("3");
        LIST_01.add("5");
        LIST_01.add("6");
        LIST_01.add("7");
        LIST_01.add("9");

        LIST_02.add("1");
        LIST_02.add("2");
        LIST_02.add("5");
        LIST_02.add("6");
        LIST_02.add("8");
        LIST_02.add("9");

    }


    public static void main(String[] args) {

    }

    /**
     * @return LIST_01 ,LIST_02 的交集
     */
    private static String[] intersection() {

        return null;
    }

    /**
     * @return LIST_01 ,LIST_02 的并集
     */
    private static String[] union() {

        return null;
    }

    /**
     * @return LIST_01 ,LIST_02 的并集 , 并按自然顺序反向排序
     */
    private static String[] unionOfSort() {

        return null;
    }


    /**
     * @return LIST_01 ,LIST_02 的补集
     */
    private static String[] complement() {

        return null;
    }
}
