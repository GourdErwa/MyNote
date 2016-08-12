package guava.base;

import com.google.common.base.Optional;
import org.slf4j.LoggerFactory;

/**
 * 在Java世界里，解决空引用问题常见的一种办法是，使用Null Object模式。
 * 这样的话，在“没有什么”的情况下，就返回Null Object，客户端代码就不用判断是否为空了。
 * 但是，这种做法也有一些问题。
 * 首先，我们肯定要为Null Object编写代码，而且，如果我们想大规模应用这个模式，我们要为几乎每个类编写Null Object。
 * <p>
 * 幸好，我们还有另外一种选择：Optional。
 * <p>
 * Optional是对可以为空的对象进行的封装，它实现起来并不复杂
 * 如果你期待的是代码量的减少，恐怕这里要让你失望了。
 * 单从代码量上来说，Optional甚至比原来的代码还多。
 * 但好处在于，你绝对不会忘记判空.
 * <p>
 * 写的快了忘记 null 的逻辑处理0.0
 * <p>
 * <p>
 * JDK8里面也有Optional类了
 * {@link java.util.Optional}
 *
 * @author wei.Li by 14-8-26.
 */
public class Optional_ {

    public static void main(String[] args) {
        GuavaOptional_.otherMethods();
    }

}

/**
 * Java google Guava Null Optional
 */
class GuavaOptional_ {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(Optional_.class);

    /**
     * 创建对象
     */
    protected static void createObject() {
        //创建一个空对象
        Optional<String> absent = Optional.absent();

        //创建一个非空对象
        Optional<String> stringOptional = Optional.of("aa");

        //创建一个不确定是否非空的对象
        Optional<String> fromNullable = Optional.fromNullable("bb");

    }

    /**
     * 其它方法总结
     */
    static void otherMethods() {

        Optional<String> obj2null = Optional.absent();

        Optional<String> obj2fromNullable = Optional.fromNullable("a");

        //isPresent方法，对象中是否不为空
        LOGGER.info("fromNullable.isPresent()   is <{}>", obj2null.isPresent());//false
        LOGGER.info("fromNullable.isPresent()   is <{}>", obj2fromNullable.isPresent());//true

        //Optional.get() Returns如果为空抛出异常IllegalStateException
        //一般配合isPresent()方法使用
        LOGGER.info("obj2null.get()   is <{}>", obj2null.get());//异常

        //Returns 如果当前对象为null 则替换为 or 方法中的参数
        LOGGER.info("obj2null.or(\"aa\")            is <{}>", obj2null.or("aa"));//aa
        LOGGER.info("obj2fromNullable.or(\"aa\")    is <{}>", obj2fromNullable.or("aa"));//aa

        //orNull Returns 如果存在返回实例，否则返回 null
        obj2null.orNull();
        obj2fromNullable.orNull();

    }


    /**
     * 示例
     *
     * @return 处理后的 obj
     */
    protected static Object example() {

        Object o = null;
        Optional<Object> optional
                = Optional.fromNullable(o);
        //判断对象是否为 null
        if (optional.isPresent()) {
            // 对象不为 null
            optional.get();//获取
            //optional.orNull();//设为空
        } else {
            //为 null 时候的处理
            optional.or("S");//设值
        }

        return optional;
    }
}

/**
 * JDK 8 Null Optional
 */
class JdkOptional {

}
