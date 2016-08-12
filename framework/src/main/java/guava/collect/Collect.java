/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package guava.collect;

import com.google.common.collect.*;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author wei.Li by 14-8-25.
 */
public class Collect {

    public static void main(String[] args) {
        //treeRangeSet();

        //Multiset_.multiset();


        //Multimap_.hashMultimap();
        //BigMap_.biMap();
        //MapMaker_.aVoid();
        Table_.table_();
    }

    /**
     * <dependency>
     * <groupId>com.google.guava</groupId>
     * <artifactId>guava</artifactId>
     * <version>17.0</version>
     * </dependency>
     */

    /*
     * RangeSet类是用来存储一些不为空的也不相交的范围的数据结构.
     * 假如需要向RangeSet的对象中加入一个新的范围，那么任何相交的部分都会被合并起来，所有的空范围都会被忽略.
     * <p>
     * 实现了RangeSet接口的类有ImmutableRangeSet和TreeRangeSet.
     * ImmutableRangeSet是一个不可修改的RangeSet，而TreeRangeSet是利用树的形式来实现.
     */
    private static class RangeSet_ {

        private static final org.slf4j.Logger LOGGER
                = LoggerFactory.getLogger(RangeSet_.class);

        /**
         * 另有实现
         *
         * @see RangeMap
         */
        private static void treeRangeSet() {

            RangeSet<Integer> treeRangeSet = TreeRangeSet.create();
            treeRangeSet.add(Range.closed(1, 10));       // {[1, 10]}
            treeRangeSet.add(Range.closedOpen(11, 15)); // {[1, 10], [11, 15)}
            treeRangeSet.add(Range.open(15, 20));       // 不连贯的范围; {[1‥10], [11‥15), (15‥20)}
            treeRangeSet.add(Range.openClosed(0, 0));   // 空范围; {[1, 10], [11, 20)}
            treeRangeSet.remove(Range.open(5, 10));     // 拆分范围 [1, 10];

            // 最终结果 {[1‥5], [10‥10], [11‥15), (15‥20)}
            LOGGER.info("create treeRangeSet        is <{}>", treeRangeSet.toString());

            //某值是否存在
            LOGGER.info("treeRangeSet.contains(11)  is <{}>", treeRangeSet.contains(20));//false

            //某值所在的范围，不存在返回 null
            LOGGER.info("treeRangeSet.rangeContaining(1)  is <{}>",
                    treeRangeSet.rangeContaining(1));//[1‥5]

            //遍历
            for (Range<Integer> integerRange : treeRangeSet.asRanges()) {

                LOGGER.info("integerRange  is <{}>", integerRange);//[1‥5] ...for...
                integerRange.lowerEndpoint();//1
                integerRange.upperEndpoint();//5
            }

            //补集
            RangeSet complement = treeRangeSet.complement();
            //[(-∞‥1), (5‥10), (10‥11), [15‥15], [20‥+∞)]
            LOGGER.info("treeRangeSet.complement() is <{}>", complement);

        }
    }

    /*
     * Multiset:把重复的元素放入集合
     * [car x 2, ship x 6, bike x 3]
     * <p>
     * 常用实现 Multiset 接口的类有：
     * HashMultiset:        元素存放于 HashMap
     * LinkedHashMultiset:  元素存放于 LinkedHashMap，即元素的排列顺序由第一次放入的顺序决定
     * TreeMultiset:        元素被排序存放于TreeMap
     * EnumMultiset:        元素必须是 enum 类型
     * ImmutableMultiset:   不可修改的 Mutiset
     */
    private static class Multiset_ {

        private static final org.slf4j.Logger LOGGER
                = LoggerFactory.getLogger(Multiset_.class);

        protected static void multiset() {
            Multiset<String> multiset = HashMultiset.create();
            multiset.add("a", 10);
            multiset.add("b", 2);
            multiset.add(null, 2);

            //[null x 2, a x 10, b x 2]
            LOGGER.info("create multiset is <{}>", multiset);

            for (Multiset.Entry<String> s : multiset.entrySet()) {
                s.getElement();
                s.getCount();
            }
            //2
            LOGGER.info("multiset.count(\"b\") is <{}>", multiset.count("b"));

        }

    }

    /*
     * Multimap: 在 Map 的 value 里面放多个元素
     * <p>
     * Muitimap 就是一个 key 对应多个 value 的数据结构。
     * {k1=[v1, v2, v3], k2=[v7, v8],....}
     * <p>
     * Muitimap 接口的主要实现类有：
     * HashMultimap:        key 放在 HashMap，而 value 放在 HashSet，即一个 key 对应的 value 不可重复
     * ArrayListMultimap:   key 放在 HashMap，而 value 放在 ArrayList，即一个 key 对应的 value 有顺序可重复
     * LinkedHashMultimap:  key 放在 LinkedHashMap，而 value 放在 LinkedHashSet，即一个 key 对应的 value 有顺序不可重复
     * TreeMultimap:        key 放在 TreeMap，而 value 放在 TreeSet，即一个 key 对应的 value 有排列顺序
     * ImmutableMultimap:   不可修改的 Multimap
     */
    private static class Multimap_ {

        private static final org.slf4j.Logger LOGGER
                = LoggerFactory.getLogger(Multimap_.class);

        protected static void hashMultimap() {
            HashMultimap<String, String> hashMultimap
                    = HashMultimap.create();
            hashMultimap.put("a", "1");
            hashMultimap.put("a", "2");
            hashMultimap.put("a", null);
            hashMultimap.put("b", "1");
            hashMultimap.put("b", "2");

            hashMultimap.put(null, null);

            //{null=[null], a=[null, 1, 2], b=[1, 2]}
            LOGGER.info("create hashMultimap is <{}>", hashMultimap);

            //[null,1, 2]
            Set<String> stringSet = hashMultimap.get("a");
            LOGGER.info("hashMultimap.get(\"a\") is <{}>", stringSet);

            //所有”键-单个值映射”的个数，而非不同键的个数
            hashMultimap.size();
            //不同键的个数
            hashMultimap.keySet().size();

            //所有”键-单个值映射”——包括重复键
            hashMultimap.entries();//[a=1, a=2, b=1, b=2]
            //所有”键-值集合映射”
            hashMultimap.asMap().entrySet();//[a=[1, 2], b=[1, 2]]

            //为Multimap<K, V>提供Map<K,Collection<V>>形式的视图。
            // 返回的Map支持remove操作，并且会反映到底层的Multimap，但它不支持put或putAll操作。
            // 更重要的是，如果你想为Multimap中没有的键返回null，而不是一个新的、可写的空集合，你就可以使用asMap().get(key)。
            hashMultimap.asMap().get("a");//1,2
        }

    }

    /*
     * BiMap: 双向 Map
     * <p>
     * BiMap 实现了 java.util.Map 接口。
     * 它的特点是它的 value 和它 key 一样也是不可重复的，换句话说它的 key 和 value 是等价的。
     * 如果你往 BiMap 的 value 里面放了重复的元素，就会得到 IllegalArgumentException。
     */
    private static class BigMap_ {
        private static final org.slf4j.Logger LOGGER
                = LoggerFactory.getLogger(BigMap_.class);

        protected static void biMap() {
            BiMap<String, String> biMap = HashBiMap.create();

            biMap.put("a", "1");

            /**
             * 在BiMap中，如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常。
             * 如果对特定值，你想要强制替换它的键,使用 forcePut();
             */
            String forcePut = biMap.forcePut("a", "2");
            LOGGER.info("forcePut(\"a\", \"2\") is <{}>", forcePut);//1

            String put = biMap.put("b", "1");
            LOGGER.info("put(\"b\", \"1\") is <{}>", put);//null

            //{b=1, a=2}
            LOGGER.info("last the biMap is <{}>", biMap);

            //反转后，键值互换 {1=b, 2=a}
            LOGGER.info("biMap.inverse is <{}>", biMap.inverse());
        }
    }

    /*
     * Table  <R, C, V> <行,列,值>
     *
     * 通常来说，当你想使用多个键做索引的时候，你可能会用类似Map<FirstName, Map<LastName, Person>>的实现，这种方式很丑陋，使用上也不友好。
     * Guava为此提供了新集合类型Table，它有两个支持所有类型的键：”行”和”列”。
     *
     * Table提供多种视图，以便你从各种角度使用它：
     *
     * rowMap() ：用Map<R, Map<C, V>>表现Table<R, C, V>。同样的， rowKeySet()返回”行”的集合Set<R>。
     * row(r)   ：用Map<C, V>返回给定”行”的所有列，对这个map进行的写操作也将写入Table中。
     *              类似的列访问方法：columnMap()、columnKeySet()、column(c)。（基于列的访问会比基于的行访问稍微低效点）
     * cellSet()：用元素类型为Table.Cell<R, C, V>的Set表现Table<R, C, V>。Cell类似于Map.Entry，但它是用行和列两个键区分的。
     */
    private static class Table_ {
        private static final org.slf4j.Logger LOGGER
                = LoggerFactory.getLogger(Table_.class);

        protected static void table_() {
            Table<String, String, Double> stringStringDoubleTable = HashBasedTable.create();
            stringStringDoubleTable.put("a", "b", 4D);
            stringStringDoubleTable.put("a", "c", 20D);
            stringStringDoubleTable.put("b", "c", 5D);

            //{a={b=4.0, c=20.0}, b={c=5.0}}
            LOGGER.info("create table is : <{}>", stringStringDoubleTable);

            //行 key 为 a 的列与对应的值
            stringStringDoubleTable.row("a"); //Map<列，值> 类型 {b=4.0, c=20.0}
            //列为 c 的行与对应的值
            stringStringDoubleTable.column("c"); //Map<行，值> 类型  {a=20.0, b=5.0}

            //返回所有的 （行，列）= 值
            stringStringDoubleTable.cellSet();//[(a,b)=4.0, (a,c)=20.0, (b,c)=5.0]
            //返回行为 key 对应的 Value 为Map<列,值>
            stringStringDoubleTable.rowMap();//{a={b=4.0, c=20.0}, b={c=5.0}}

        }

        /*
         * Table有如下几种实现：

         HashBasedTable：本质上用HashMap<R, HashMap<C, V>>实现；
         TreeBasedTable：本质上用TreeMap<R, TreeMap<C,V>>实现；
         ImmutableTable：本质上用ImmutableMap<R, ImmutableMap<C, V>>实现；注：ImmutableTable对稀疏或密集的数据集都有优化。
         ArrayTable：    要求在构造时就指定行和列的大小，本质上由一个二维数组实现，以提升访问速度和密集Table的内存利用率。
                            ArrayTable与其他Table的工作原理有点不同，请参见Javadoc了解详情。
         */
    }

    private static class MapMaker_ {

        private static final org.slf4j.Logger LOGGER
                = LoggerFactory.getLogger(MapMaker_.class);

        static void aVoid() {


        }
    }


}
