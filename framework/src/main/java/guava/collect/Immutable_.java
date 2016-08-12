package guava.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * 不可变的集合
 *
 * @author wei.Li by 14-8-28.
 */
public class Immutable_ {

    /**
     * 不可变集合可以用如下多种方式创建：
     * <p>
     * copyOf方法，如ImmutableSet.copyOf(set);
     * of方法，如ImmutableSet.of(“a”, “b”, “c”)或 ImmutableMap.of(“a”, 1, “b”, 2);
     * Builder工具
     * <p>
     * 查看源代码打印帮助
     * ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");
     * 会在构造时就把元素排序为a, b, c, d。
     */
    private static void immutableRangeSet() {
        //此外，对有序不可变集合来说，排序是在构造集合的时候完成的，如
        final ImmutableSet<String> GOOGLE_COLORS =
                ImmutableSet.<String>builder()
                        // .addAll("aa")
                        .add("bb")
                        .build();

        //比想象中更智能的copyOf
        ImmutableSet<String> foobar = ImmutableSet.of("foo", "bar", "baz");
        ImmutableList<String> defensiveCopy = ImmutableList.copyOf(foobar);
        /*
         在这段代码中，ImmutableList.copyOf(foobar)会智能地直接返回foobar.asList(),
         它是一个ImmutableSet的常量时间复杂度的List视图。
         作为一种探索，ImmutableXXX.copyOf(ImmutableCollection)会试图对如下情况避免线性时间拷贝：

         1.在常量时间内使用底层数据结构是可能的——例如，ImmutableSet.copyOf(ImmutableList)就不能在常量时间内完成。
         2.不会造成内存泄露——例如，你有个很大的不可变集合ImmutableList<String>
            hugeList， ImmutableList.copyOf(hugeList.subList(0, 10))就会显式地拷贝，以免不必要地持有hugeList的引用。
         3.不改变语义——所以ImmutableSet.copyOf(myImmutableSortedSet)会显式地拷贝，
            因为和基于比较器的ImmutableSortedSet相比，ImmutableSet对hashCode()和equals有不同语义。
         4.在可能的情况下避免线性拷贝，可以最大限度地减少防御性编程风格所带来的性能开销。
         */


        /*
        所有不可变集合都有一个asList()方法提供ImmutableList视图，来帮助你用列表形式方便地读取集合元素。
        例如，你可以使用sortedSet.asList().get(k)从ImmutableSortedSet中读取第k个最小元素。

        asList()返回的ImmutableList通常是——并不总是——开销稳定的视图实现，而不是简单地把元素拷贝进List。
        也就是说
        asList返回的列表视图通常比一般的列表平均性能更好，比如，在底层集合支持的情况下，它总是使用高效的contains方法。
         */
        defensiveCopy.asList().get(1);
    }

}
/**
 * 细节：关联可变集合和不可变集合
 * <p>
 * 可变集合接口	       属于JDK还是Guava	不可变版本
 * Collection	            JDK	        ImmutableCollection
 * List	                JDK	        ImmutableList
 * Set	                JDK	        ImmutableSet
 * SortedSet/NavigableSet	JDK	        ImmutableSortedSet
 * Map	                JDK	        ImmutableMap
 * SortedMap	            JDK	        ImmutableSortedMap
 * Multiset	            Guava	    ImmutableMultiset
 * SortedMultiset	        Guava	    ImmutableSortedMultiset
 * Multimap	            Guava	    ImmutableMultimap
 * ListMultimap	        Guava	    ImmutableListMultimap
 * SetMultimap	        Guava	    ImmutableSetMultimap
 * BiMap	                Guava	    ImmutableBiMap
 * ClassToInstanceMap	    Guava	    ImmutableClassToInstanceMap
 * Table	                Guava	    ImmutableTable
 */
