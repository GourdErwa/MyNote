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

package guava.collect;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.Li by 15/3/10 (gourderwa@163.com).
 */
public class Lists_ {

    private static void aVoid() {

        final ArrayList<String> newArrayList = Lists.newArrayList("1", "2", "3", "4", "5", "1", "2", "3", "4", "5");

       /* final List<List<String>> partition = Lists.partition(newArrayList, 2);

        System.out.println(partition);
*/
        final List<Object> objectList = Lists.<String, Object>transform(newArrayList, new Function<String, Object>() {
            @Override
            public Object apply(String input) {
                return input;
            }
        });

        System.out.println(objectList.toString());

    }

    public static void main(String[] args) {
        aVoid();

    }
}
