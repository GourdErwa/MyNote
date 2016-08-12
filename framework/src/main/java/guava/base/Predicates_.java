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

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.isNull;

/**
 * Predicate则主要用于对象的过滤和筛选处理
 *
 * @author wei.Li by 15/4/13 (gourderwa@163.com).
 */
public class Predicates_ {

    public static void main(String[] args) {
        final boolean apply = isNull().apply(12);

        System.out.println(apply);

        final Predicate<Integer> integerPredicate = equalTo(12);
        final List<Integer> unfiltered = Lists.asList(1, 2, new Integer[]{12, 14});
        final Collection<Integer> filter = Collections2.filter(unfiltered, integerPredicate);

        System.out.println(filter);


        Predicate<Girl> agePredicate = input -> input.getAge() >= 18;

        Predicate<Girl> facePredicate = input -> input.getFace().equals("nice");

        Girl girl = new Girl(18, "ugly");

        //and：用于过滤两个Predicate都为true
        Predicate<Girl> predicate = Predicates.and(agePredicate, facePredicate);
        checkOut(predicate.apply(girl)); //输出结果：She doesn't fit me

        //or：用于过滤其中一个Predicate为true
        predicate = Predicates.or(agePredicate, facePredicate);
        checkOut(predicate.apply(girl));  //输出结果：She fits me

        //or：用于将指定Predicate取反
        Predicate<Girl> noneAgePredicate = Predicates.not(agePredicate);
        predicate = Predicates.and(noneAgePredicate, facePredicate);
        checkOut(predicate.apply(girl));  //输出结果：She doesn't fit me

        //compose: Function与Predicate的组合
        Map<String, Girl> map = new HashMap<String, Girl>() {
            //构造一个测试用Map集合
            {
                put("love", new Girl(18, "nice"));
                put("miss", new Girl(16, "ugly"));
            }
        };
        Predicate<Girl> predicate1 = Predicates.and(agePredicate, facePredicate);
        Function<String, Girl> function1 = Functions.forMap(map);
        Predicate<String> stringPredicate = Predicates.compose(predicate1, function1);
        System.out.println(stringPredicate.apply("love"));//true
        System.out.println(stringPredicate.apply("miss"));//false
    }

    /**
     * 判断输出
     *
     * @param flag flag
     */
    private static void checkOut(boolean flag) {
        if (flag) {
            System.out.println("She fits me");
        } else {
            System.out.println("She doesn't fit me");
        }
    }

}
