package guava.base;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 部分Object 方法
 * <p>
 * Ordering犀利的比较器
 *
 * @author wei.Li by 14-8-27.
 */
public class ObjectMethodsAndOrdering {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(Optional_.class);

    /**
     * 当一个对象中的字段可以为null时，实现Object.equals方法会很痛苦，因为不得不分别对它们进行null检查。
     * 使用Objects.equal帮助你执行null敏感的equals判断，从而避免抛出NullPointerException。
     * <p>
     * 注意：JDK7引入的Objects类提供了一样的方法Objects.equals。
     */
    private static void objectsEqual() {
        Objects.equal("a", "a"); // returns true
        Objects.equal(null, "a"); // returns false
        Objects.equal("a", null); // returns false
        Objects.equal(null, null); // returns true

        Objects.hashCode("a", "b");//
    }

    /**
     * 用对象的所有字段作散列[hash]运算应当更简单。
     * Guava的Objects.hashCode(Object...)会对传入的字段序列计算出合理的、顺序敏感的散列值。
     * 你可以使用Objects.hashCode(field1, field2, …, fieldn)来代替手动计算散列值。
     * <p>
     * 注意：JDK7引入的Objects类提供了一样的方法Objects.hash(Object...)
     */
    private static void objectsHashCode() {
        Objects.equal("a", "a"); // returns true
        Objects.equal(null, "a"); // returns false
        Objects.equal("a", null); // returns false
        Objects.equal(null, null); // returns true

        Objects.hashCode("a", "b");//
        System.out.printf("");
    }

    public static void main(String[] args) {

        Arrays.sort(OrderTest.ORDER_TESTS);
        LOGGER.info("---------- ORDER_TESTS after sort start ----------");
        for (OrderTest orderTest : OrderTest.ORDER_TESTS) {
            LOGGER.info("<{}>", orderTest);
        }
        LOGGER.info("---------- ORDER_TESTS after sort end   ----------");

        LOGGER.info("\n---------- Apple Ordering start ----------");
        OrderTest.appleOrdering();
        LOGGER.info("---------- Apple Ordering end   ----------");

        /* main 函数执行结果
        ---------- ORDER_TESTS after sort start ----------
<OrderTest{id=6, name='null'}>
<OrderTest{id=2, name='erwa'}>
<OrderTest{id=3, name='sanwa'}>
<OrderTest{id=4, name='siwa'}>
<OrderTest{id=5, name='wuwa'}>
<OrderTest{id=5, name='wuwa'}>
<OrderTest{id=1, name='yiwa'}>
<OrderTest{id=11, name='yiwa'}>
---------- ORDER_TESTS after sort end   ----------

---------- Apple Ordering start ----------
isOrdered          : <true>
isStrictlyOrdered  : <false>
greatestOf 2 index : <[OrderTest{id=1, name='yiwa'}, OrderTest{id=11, name='yiwa'},
                      OrderTest{id=5, name='wuwa'}, OrderTest{id=5, name='wuwa'}]>
orderTestList max  : <OrderTest{id=1, name='yiwa'}>
---------- Apple Ordering end   ----------

         */
    }


    /*****************************/
    /* Ordering犀利的比较器部分API */
    /****************************/

    /*
     创建排序器：常见的排序器可以由下面的静态方法创建

     方法	             描述
     natural()	        对可排序类型做自然排序，如数字按大小，日期按先后排序
     usingToString()	按对象的字符串形式做字典排序[lexicographical ordering]
     from(Comparator)	把给定的Comparator转化为排序器
     arbitrary()        返回一个所有对象的任意顺序，
                        即compare(a, b) == 0 就是 a == b (identity equality)。
                        本身的排序是没有任何含义， 但是在VM的生命周期是一个常量。

     链式调用方法：通过链式调用，可以由给定的排序器衍生出其它排序器

     方法	                 描述
     reverse()	            返回与当前Ordering相反的排序
     nullsFirst()	        使用当前排序器，但额外把null值排到最前面。
     nullsLast()	        使用当前排序器，但额外把null值排到最后面。
     compound(Comparator)	合成另一个比较器，以处理当前排序器中的相等情况。
     lexicographical()	    返回一个按照字典元素迭代的Ordering；
     onResultOf(Function)	对集合中元素调用Function，再按返回值用当前排序器排序。


     运用排序器：Guava的排序器实现有若干操纵集合或元素值的方法

      方法	                                 描述	                                      另请参见
      greatestOf(Iterable iterable, int k)	返回指定的第k个可迭代的最大的元素，
                                                按照这个从最大到最小的顺序。是不稳定的。           leastOf
      isOrdered(Iterable)	        判断可迭代对象是否已按排序器排序：允许有排序值相等的元素。	       isStrictlyOrdered
                                        Iterable不能少于2个元素。否则永远为 true,必须的啊
      sortedCopy(Iterable)	        返回指定的元素作为一个列表的排序副本。	                       immutableSortedCopy
      min(E, E)	                    返回两个参数中最小的那个。如果相等，则返回第一个参数。	           max(E, E)
      min(E, E, E, E...)	        返回多个参数中最小的那个。
                                        如果有超过一个参数都最小，则返回第一个最小的参数。	       max(E, E, E, E...)
      min(Iterable)	                返回迭代器中最小的元素。                                    max(Iterable), min(Iterator), max(Iterator)
                                        如果可迭代对象中没有元素，则抛出NoSuchElementException。
     */

    /**
     * Ordering犀利的比较器演示
     * <p>
     * jdk8也实现了部分
     *
     * @see java.util.Comparators
     */
    private static class OrderTest implements Comparable<OrderTest> {

        public static final OrderTest[] ORDER_TESTS = new OrderTest[]{
                new OrderTest(1, "yiwa")
                , new OrderTest(11, "yiwa")
                , new OrderTest(2, "erwa")
                , new OrderTest(3, "sanwa")
                , new OrderTest(4, "siwa")
                , new OrderTest(5, "wuwa")
                , new OrderTest(5, "wuwa")
                , new OrderTest(6, null)
        };
        private int id;
        private String name;

        OrderTest(int id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * 构建Ordering
         */
        private static Ordering<OrderTest> getOrdering() {

            return Ordering
                    .natural()
                    .nullsFirst()
                    //返回按String 排序的Comparable
                    //当阅读链式调用产生的排序器时，应该从后往前读。
                    // 下面的例子中，排序器首先调用apply方法获取name值，并把name为null的元素都放到最前面，然后把剩下的元素按name进行自然排序。
                    // 之所以要从后往前读，是因为每次链式调用都是用后面的方法包装了前面的排序器。
                    .onResultOf(new Function<OrderTest, Comparable>() {
                        @Override
                        public Comparable apply(OrderTest input) {
                            return input.name;
                        }
                    })
                    // .leastOf(Arrays.asList(ORDER_TESTS),2)
                    ;
        }

        /**
         * 运用选择器进行一些计算
         */
        private static void appleOrdering() {
            Ordering<OrderTest> ordering = getOrdering();
            List<OrderTest> orderTestList
                    = Lists.newArrayList(ORDER_TESTS);

            //判断可迭代对象是否已按排序器排序：允许有排序值相等的元素。
            boolean orderingOrdered = ordering.isOrdered(orderTestList);
            LOGGER.info("isOrdered          : <{}>", orderingOrdered);

            //判断可迭代对象是否已按排序器严格排序：不允许有排序值相等的元素。
            boolean orderingStrictlyOrdered = ordering.isStrictlyOrdered(orderTestList);
            LOGGER.info("isStrictlyOrdered  : <{}>", orderingStrictlyOrdered);

            //返回指定的元素作为一个列表的排序副本。immutableSortedCopy返回不可变的排序副本
            // LOGGER.info("sortedCopy  : <{}>", ordering.sortedCopy(orderTestList));

            //最大的2元素
            LOGGER.info("greatestOf 2 index : <{}>", ordering.greatestOf(orderTestList, 4));

            //获取最大值，迭代比较大小后返回
            LOGGER.info("orderTestList max  : <{}>", ordering.max(orderTestList));
        }

        @Override
        public int compareTo(OrderTest that) {
            return ComparisonChain.start()
                    // .compare(this.id, that.id)
                    .compare(this, that, getOrdering())
                    .result()
                    ;
        }

        @Override
        public String toString() {
            return "OrderTest{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

    }

    /*
     * Comparator, Comparable区别
     * <p>
     * 1.comparable是通用的接口，用户可以实现它来完成自己特定的比较，
     *      而comparator可以看成一种算法的实现,在需要容器集合 collection需要比较功能的时候，来指定这个比较器。
     * 2.一个类实现了Camparable接口表明这个类的对象之间是可以相互比较的。
     *      如果用数学语言描述的话就是这个类的对象组成的集合中存在一个全序。这样，这个类对象组成的集合就可以使用Sort方法排序了。
     * 3.而Comparator的作用有两个：
     * 　a，如果类的设计师没有考虑到Compare的问题而没有实现Comparable接口，可以通过Comparator来实现比较算法进行排序
     * 　b，可以更加灵活实现排序规则，为了使用不同的排序标准做准备，比如：升序、降序，或者将来想通过类的其他字段进行排序
     *
     * 例如：在对List 排序时候默认使用comparable的compareTo(Object o)方法
     *      但在比较某个对象下的属性时候，我们可以自定义一个Comparator的compare(Object o1, Object o2)方法比较器进行比较
     *      Collections 中的 public static <T> void sort(List<T> list, Comparator<? super T> c) 方法
     *
     */
    private class ComparableAndComparatorDistinction implements Comparator, Comparable {

        /*
         * 重写Comparable中的方法
         */
        @Override
        public int compareTo(Object o) {
            return 0;
        }

        /*
         * 重写Comparator中的方法
         */
        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    }

    /**
     * 实现一个比较器[Comparator]
     * JDK 与 Guava的实现的不同
     */
    private class Person2compareToTest implements Comparable<Person2compareToTest> {
        private String lastName;
        private String firstName;
        private int zipCode;

        /**
         * jdk 方式的比较
         *
         * @param other
         * @return
         */
        @Override
        public int compareTo(Person2compareToTest other) {
            int cmp = lastName.compareTo(other.lastName);
            if (cmp != 0) {
                return cmp;
            }
            cmp = firstName.compareTo(other.firstName);
            if (cmp != 0) {
                return cmp;
            }
            return Integer.compare(zipCode, other.zipCode);
        }

        /**
         * Guava的实现
         * <p>
         * ComparisonChain执行一种懒比较：它执行比较操作直至发现非零的结果，在那之后的比较输入将被忽略。
         *
         * @param that 比较对象
         * @return 比较结果
         * @see #compareTo(Person2compareToTest)
         */
        public int compareToByComparisonChain(Person2compareToTest that) {
            return ComparisonChain.start()
                    .compare(this.lastName, that.lastName)
                    .compare(this.firstName, that.firstName)
                    .compare(this.zipCode, that.zipCode, Ordering.natural().nullsLast())
                    .result();
        }
    }
}
