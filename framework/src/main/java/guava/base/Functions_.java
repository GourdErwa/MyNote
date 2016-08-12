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
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wei.Li by 15/4/13 (gourderwa@163.com).
 */
public class Functions_ {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-mm-dd  11");

    private static final Function<Date, String> STRING_DATE_FUNCTION = DATE_FORMAT::format;

    private static final Function<String, String> STRING_TRIM_FUNCTION = String::trim;

    public static void main(String[] args) {

        System.out.println(STRING_DATE_FUNCTION.apply(new Date()));

        /**
         * @see Functions#constant(Object)
         */
        final Function<Object, SimpleDateFormat> dateFormatFunction = Functions.constant(new SimpleDateFormat("YYYY-mm-dd"));
        /**
         * 组合2个 Function A-B-C Date -> String -> trim String
         */
        final Function<Date, String> compose = Functions.compose(STRING_TRIM_FUNCTION, STRING_DATE_FUNCTION);

        /**
         * 返回一个函数，它总是返回的结果调用 {@link Supplier#get} {@code  Supplier}
         */
        Functions.forSupplier(() -> 2);

        final Function<Object, Object> objectFunction = Functions.forMap(Maps.newHashMap());
        System.out.println(objectFunction);

    }
}
