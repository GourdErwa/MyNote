package com.gourd.erwa.util.corejava.essence_36;

import java.util.*;

/**
 * Created by lw on 14-5-20.
 * <p>
 * 有序的HashMap -LinkedHashMap
 */
public class Linked_HashMap {

    private static Map<Integer, String> integerStringMap;

    /**
     * accessOrder=true  按照元素最后访问时间排序
     * accessOrder=false 按照元素添加顺序排序
     * public LinkedHashMap(int initialCapacity,float loadFactor,boolean accessOrder) {
     * ..
     * }
     */
    private static void InitLinked_HashMap(boolean accessOrder) {
        integerStringMap = new LinkedHashMap(16, 0.75f, accessOrder);
        integerStringMap.put(1, "a");
        integerStringMap.put(2, "b");
        integerStringMap.put(3, "c");
        integerStringMap.put(4, "d");

        System.out.println("访问key=2、3的元素....");
        integerStringMap.get(2);
        integerStringMap.get(3);

        System.out.println(integerStringMap);
    }

    /**
     * accessOrder=true  按照元素最后访问时间排序
     * 遍历时候有Key获取Value时候抛出java.util.ConcurrentModificationException
     * <p>
     * 遍历时候，LinkedHashMap调用get方法会修改LinkedHashMap结构。
     * remove亦是。
     * 参考下面is2ConcurrentModificationException()方法内容
     */
    private static void for_Linked_HashMap() {
        integerStringMap = new LinkedHashMap(16, 0.75f, true);
        integerStringMap.put(1, "a");
        integerStringMap.put(2, "b");
        integerStringMap.put(3, "c");
        integerStringMap.put(4, "d");
        Set<Integer> set = integerStringMap.keySet();
        for (Integer integer : set) {
            integerStringMap.get(integer);
            // integerStringMap.remove(integer);
        }
    }

    public static void main(String[] args) {
        InitLinked_HashMap(true);
        InitLinked_HashMap(false);

        //for_Linked_HashMap();
        is2ConcurrentModificationException();
    }

    /**
     * 衍生：List怎么移除元素？
     * forEach怎么执行的呢？
     * 什么时候产生快速失败？什么时候产生安全失败？
     */
    private static void is2ConcurrentModificationException() {
        String str;
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");

        for (String s : list) {
            //list.remove(s);
        }

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            str = iterator.next();
            //list.remove(str);
        }

        Map map = new HashMap();
        map.put(1, "a");
        map.put(2, "b");

        for (int i = 0; i < map.size(); i++) {
            //  map.remove(1);
        }

        Set<Integer> set = map.keySet();
        for (Integer integer : set) {
            //set.remove(integer);//yes
            //map.remove(integer);//yes
        }

    }
}
