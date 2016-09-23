package guava.collect;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

/**
 * @author wei.Li
 */
public class MultiSets_ {

    public static void main(String[] args) {

        Multiset<String> multiset1 = HashMultiset.create();
        multiset1.add("a", 2);

        Multiset<String> multiset2 = HashMultiset.create();
        multiset2.add("a", 5);

        multiset1.containsAll(multiset2); // returns true: all unique elements are contained,

        // even though multiset1.count("a") == 2 < multiset2.count("a") == 5
        Multisets.containsOccurrences(multiset1, multiset2); // returns false

        //multiset2.removeOccurrences(multiset1); // multiset2 now contains 3 occurrences of "a"

        multiset2.removeAll(multiset1); // removes all occurrences of "a" from multiset2, even though multiset1.count("a") == 2
        multiset2.isEmpty(); // returns true


        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a", 3);
        multiset.add("b", 5);
        multiset.add("c", 1);

        ImmutableMultiset<String> highestCountFirst = Multisets.copyHighestCountFirst(multiset);
        // key 对应 count 值降序排列, like its entrySet and elementSet, iterates over the elements in order {"b", "a", "c"}
        //[b x 5, a x 3, c]
        System.out.println(highestCountFirst);

    }
}
