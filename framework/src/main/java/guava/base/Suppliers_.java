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

import com.google.common.base.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wei.Li by 15/4/13 (gourderwa@163.com).
 */
public class Suppliers_ {

    public static void main(String[] args) {

        Supplier<Predicate<String>> supplier = new Supplier<Predicate<String>>() {
            @Override
            public Predicate<String> get() {
                Map<String, Girl> map = new HashMap<String, Girl>() {
                    {
                        put("love the age", new Girl(18, "not so nice"));
                        put("love the face", new Girl(16, "so nice"));
                    }
                };
                Function<String, Girl> function = Functions.forMap(map);
                Predicate<Girl> predicate = input -> input.getAge() >= 18;
                return Predicates.compose(predicate, function);
            }
        };

        //Supplier.memoize方法，返回传入参数Supplier的包装类，
        //当get()方法第一次被调用，Supplier的包裹被创建，
        //包装类缓存了Supplier实例，并将其返回给调用者
        Supplier<Predicate<String>> wrapped = Suppliers.memoize(supplier);
        System.out.println(wrapped.get().apply("love the age"));//true

        //Supplier.memoizeWithExpiration方法，设定时间的数值（10l）和单位(TimeUnit.SECONDS)
        // 返回传入参数Supplier的包装类，当get方法被调用，在指定的时间内，
        // memoizeWithExpiration作用与memoize相同，包装类缓存Supplier实例给定的时间
        Supplier<Predicate<String>> wrapped2 = Suppliers
                .memoizeWithExpiration(supplier, 10l, TimeUnit.SECONDS);

    }
}
