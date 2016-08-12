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

import com.google.common.collect.Lists;

import java.util.List;

/**
 * The interface Has defaule method interface.
 */
@FunctionalInterface
interface HasDefauleMethodInterface {

    /**
     * Run.
     *
     * @param s the s
     * @param i the
     */
    public void run_(String s, int i);

}

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-7
 * <p>
 * Lambda表达式教程
 */
public class Lambda_ {


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        /*HasDefauleMethodInterface hasDefauleMethod = (s, i) -> {
            System.out.println("s:" + s);
            System.out.println("i:" + i);
        };

        hasDefauleMethod.run_("1", 2);
        hasDefauleMethod.defaultMethod();*/
    }

    private static void forEach() {
        List<String> list = Lists.newArrayList("a", "b");
        //list.forEach(Lambda_::analogThreads);
    }

    private static void aVoid(String s) {
        System.out.println("analogThreads's s is " + s);
    }
}
