package guava.collect;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wei.Li
 */
public class MultiMaps_ {
    public static void main(String[] args) {

        /*
        index
         */
        ImmutableSet<String> digits = ImmutableSet.of("zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine");
        Function<String, Integer> lengthFunction = new Function<String, Integer>() {
            public Integer apply(String string) {
                return string.length();
            }
        };
        //{4=[zero, four, five, nine], 3=[one, two, six], 5=[three, seven, eight]}
        ImmutableListMultimap<Integer, String> digitsByLength = Multimaps.index(digits, lengthFunction);
        System.out.println(digitsByLength);

        /*
        invertFrom
         */
        ListMultimap<String, Integer> multiMap = ArrayListMultimap.create();
        multiMap.putAll("b", Ints.asList(2, 4, 6));
        multiMap.putAll("a", Ints.asList(4, 2, 1));
        multiMap.putAll("c", Ints.asList(2, 5, 3));

        //{1=[a], 2=[a, b, c], 3=[c], 4=[a, b], 5=[c], 6=[b]}
        TreeMultimap<Integer, String> inverse = Multimaps.invertFrom(multiMap, TreeMultimap.create());
        System.out.println(inverse);
        // note that we choose the implementation, so if we use a TreeMultiMap, we get results in order

        /*
        forMap
         */
        Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
        SetMultimap<String, Integer> forMap = Multimaps.forMap(map);
        // multiMap maps ["a" => {1}, "b" => {1}, "c" => {2}]
        //inverseForMap maps {1=[a, b], 2=[c]}
        Multimap<Integer, String> inverseForMap = Multimaps.invertFrom(forMap, HashMultimap.create());
        System.out.println(inverseForMap);


        /*
        Wrappers

        	Multimap	    ListMultimap	    SetMultimap	    SortedSetMultimap
         */
        //if {@code map} is empty
        final HashMap<String, Collection<Integer>> hashMap = Maps.newHashMap();
        /*hashMap.put("a", Ints.asList(1, 2, 3));
        hashMap.put("b", Ints.asList(2, 3, 5));*/
        final SetMultimap<String, Integer> setMultiMap = Multimaps.newSetMultimap(
                hashMap,
                new Supplier<Set<Integer>>() {
                    @Override
                    public Set<Integer> get() {
                        return Sets.newHashSet(10);
                    }
                });
        //{aa=[12, 10]} , setMultiMap中所有 value 默认添加 Supplier 中内容
        setMultiMap.put("aa", 12);

        //Exception in thread "main" java.lang.AssertionError: New Collection violated the Collection spec
        //setMultiMap.put("bb", 10);

        setMultiMap.put("bb", 11);
        setMultiMap.put("bb", 12);
        System.out.println(setMultiMap);
    }
}
