package guava.collect;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import java.util.ArrayList;

/**
 * @author wei.Li
 */
public class Iterables_ {

    public static void main(String[] args) {

        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 10, 6)
        );

        Integer lastAdded = Iterables.getLast(concatenated);
        //6
        System.out.println(lastAdded);

        //IllegalArgumentException : expected one element but was: <1, 2, 3, 4, 10, ...>
        //Integer theElement = Iterables.getOnlyElement(concatenated);
        //System.out.println(theElement);

        final ArrayList<Integer> removeFrom = Lists.newArrayList(1, 2, 3, 4, 5);
        final ArrayList<Integer> elementsToRemove = Lists.newArrayList(1, 3);
        Iterables.removeAll(
                removeFrom,
                elementsToRemove
        );
        /*
        [2, 4, 5]
        [1, 3]
         */
        System.out.println(removeFrom);
        System.out.println(elementsToRemove);

    }
}
