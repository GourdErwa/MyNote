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

package guava.reflection;

import com.google.common.reflect.Invokable;
import org.elasticsearch.common.Nullable;

/**
 * @author wei.Li by 14/12/23 (gourderwa@163.com).
 */
public class Invokable_ {


    public static void main(String[] args) throws NoSuchMethodException {

        final Invokable<?, Object> invokable = Invokable.from(Invokable_.class.getMethod("aVoid"));

        System.out.println(invokable);

        invokable.isPublic();

        /**
         方法是否能够被子类重写？
         !(isFinal() || isPrivate() || isStatic()
         || Modifier.isFinal(getDeclaringClass().getModifiers()));
         */
        invokable.isOverridable();

        /**
         !(Modifier.isPrivate(method.getModifiers()) || Modifier.isPublic(method.getModifiers()))
         */
        invokable.isPackagePrivate();

        /**
         * 方法的第一个参数是否被定义了注解@Nullable？
         */
        invokable.getParameters().get(0).isAnnotationPresent(Nullable.class);

      /*  Invokable<List<String>, ?> listInvokable = new TypeToken<List<String>>() {
        }.method(getMethod);
        listInvokable.getReturnType(); // String.class*/
    }

    public void aVoid() {

    }
}
