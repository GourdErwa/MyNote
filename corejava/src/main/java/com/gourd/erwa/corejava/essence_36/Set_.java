package com.gourd.erwa.util.corejava.essence_36;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by lw on 14-5-20.
 */
public class Set_ {

    private static Set<Integer> set;

    private static void initSet2Type(Set<Integer> set1, String state) {
        set = set1;
        set.add(3);
        set.add(5);
        set.add(130);
        set.add(13);
        set.add(33);
        System.out.println("\n\nSet->Type:" + state);
        for (Integer integer : set) {
            System.out.print(integer + "\t");
        }
    }

    public static void main(String[] args) {
        initSet2Type(new HashSet(), "new HashSet()");

        initSet2Type(new LinkedHashSet(), "new LinkedHashSet()");

        initSet2Type(new TreeSet(), "new TreeSet()");
    }

        /*  打印
        Set->Type:new HashSet()
        33	130	3	5	13

        Set->Type:new LinkedHashSet()
        3	5	130	13	33

        Set->Type:new TreeSet()
        3	5	13	33	130
        */

}
