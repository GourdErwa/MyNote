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

package guava.base;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;

/**
 * https://code.google.com/p/guava-libraries/w/list?q=label:explained。
 *
 * @author wei.Li by 14/12/17 (gourderwa@163.com).
 */
public class HandleString {


    /**
     * 连接器[Joiner]
     */
    private static void handleJoiner() {

        Object[] objects = new Object[]{"1", "2", "3", null, "5"};

        System.out.println(
                Joiner.on(",").skipNulls().join(objects)
        );//1,2,3,5


        System.out.println(
                Joiner.on(",").useForNull("NULL").join(objects)
        );//1,2,3,NULL,5

    }

    private static void handleCharMatcher() {

        final int i = CharMatcher.ASCII.indexIn("A");
        System.out.println(i);
        final boolean naN = Double.isNaN(0d / 0);
        System.out.println(0d / 0);
    }


    public static void main(String[] args) {
       /* final String constant_name = CaseFormat.UPPER_UNDERSCORE.
                to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME");// returns "constantName"
        //System.out.println(constant_name);
        long x = 1, y = 2;
        //(x & y) + ((x ^ y) >> 1);
        System.out.println(LongMath.mean(x,y));
        System.out.println(x & y);
        //handleJoiner();
        //handleCharMatcher();*/

        List<String> stringList = Lists.newArrayList("a", "b", "a");
        A a = new A();
        a.setStr(stringList);

        List<String> str = a.getStr();
        str = Lists.newArrayList(Sets.newHashSet(str));
        a.setStr(str);

        System.out.println(a.getStr());
    }
}

class A {

    private List<String> str;

    public List<String> getStr() {
        return str;
    }

    public void setStr(List<String> str) {
        this.str = str;
    }
}
