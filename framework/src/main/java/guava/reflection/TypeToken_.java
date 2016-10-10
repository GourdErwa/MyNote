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

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

/**
 * @author wei.Li by 14/12/23 (gourderwa@163.com).
 */
public class TypeToken_ {

    public static void main(String[] args) throws NoSuchMethodException {
        TypeToken<String> stringTok = TypeToken.of(String.class);

        final String s = stringTok.toString();
        System.out.println(s);

        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
        };
        System.out.println(stringListTok.toString());

        //返回大家熟知的运行时类
        final Class<? super List<String>> rawType = stringListTok.getRawType();
        System.out.println(rawType.getName());

        //返回那些有特定原始类的子类型。举个例子，如果这有一个Iterable并且参数是List.class，那么返回将是List。
        final TypeToken<? extends List<String>> subtype = stringListTok.getSubtype(ArrayList.class);
        System.out.println(subtype);

        //产生这个类型的超类，这个超类是指定的原始类型。举个例子，如果这是一个Set并且参数是Iterable.class，结果将会是Iterable。
        final TypeToken<? super List<String>> supertype = stringListTok.getSupertype(List.class);
        System.out.println(supertype);

        //如果这个类型是 assignable from 指定的类型，并且考虑泛型参数，返回true。List<? extends Number>是assignable from List，但List没有.
        /*final boolean assignableFrom = stringListTok.isAssignableFrom(TypeToken.of(List.class));
        System.out.println(assignableFrom);*/

        //返回组件类型数组。
        final TypeToken<?> componentType = stringListTok.getComponentType();
        System.out.println(componentType);

        //获得包装的java.lang.reflect.Type.
        final Type type = stringListTok.getType();
        System.out.println(type.getTypeName());

        //返回一个Set，包含了这个所有接口，子类和类是这个类型的类。返回的Set同样提供了classes()和interfaces()方法允许你只浏览超类和接口类。
        final TypeToken<List<String>>.TypeSet types = stringListTok.getTypes();
        System.out.println(types);

        TypeToken<Map<String, BigInteger>> mapToken = mapToken(TypeToken.of(String.class), TypeToken.of(BigInteger.class));
        TypeToken<Map<Integer, Queue<String>>> complexToken = mapToken(TypeToken.of(Integer.class), new TypeToken<Queue<String>>() {
        });

        System.out.println(mapToken.toString());
        System.out.println(complexToken);

        resolveType();

    }

    /**
     * resolveType是一个可以用来“替代”context token 中的类型参数的一个强大而复杂的查询操作
     * 获取某个泛型中的、方法返回的类型
     *
     * @throws NoSuchMethodException
     */
    private static void resolveType() throws NoSuchMethodException {
        TypeToken<Function<Integer, String>> funToken = new TypeToken<Function<Integer, String>>() {
        };
        TypeToken<?> funResultToken = funToken.resolveType(Function.class.getTypeParameters()[1]);
        // returns a TypeToken<String>

        //TypeToken将Java提供的TypeVariables和context token中的类型变量统一起来。这可以被用来一般性地推断出在一个类型相关方法的返回类型：
        TypeToken<Map<String, Integer>> typeToken = new TypeToken<Map<String, Integer>>() {
        };
        TypeToken<?> entrySetToken = typeToken.resolveType(Map.class.getMethod("entrySet").getGenericReturnType());
        // returns a TypeToken<Set<Map.Entry<String, Integer>>>
    }

    //TypeToken提供了一种方法来动态的解决泛型类型参数
    private static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
        return new TypeToken<Map<K, V>>() {
        }.
                where(new TypeParameter<K>() {
                }, keyToken).
                where(new TypeParameter<V>() {
                }, valueToken);
    }


}
