package com.gourd.erwa.util.corejava.basis.collection.jdk.map;

import java.util.*;

/**
 * 常用的 Map 操作方法
 *
 * @author wei.Li by 14-8-11.
 */
public class MapCommon {

    private static final Map<String, String> map;

    static {

        Map<String, String> map1 = new HashMap<>();

        map1.put("1", "1");
        map1.put("2", "2");
        map = Collections.unmodifiableMap(map1);
    }

    /**
     * 通过 Collections.unmodifiableMap(map1); 返回一个键值映射不可修改的视图
     * <p>
     * 修改时候会抛出 UnsupportedOperationException
     */
    private static void setMap() {
        map.put("3", "3");
        System.out.println("map -> " + map.toString());
    }

    /**
     * 将Map转换为List类型
     */
    private static void map2List() {


        ArrayList stringList;

        //key
        stringList = new ArrayList<>(map.keySet());

        //value
        stringList = new ArrayList<>(map.values());

        //key=value
        stringList = new ArrayList(map.entrySet());

        System.out.println(Arrays.toString(stringList.toArray()));//[1=1, 2=2]

    }

    /**
     * 通过Entry 遍历Map
     */
    private static void write2Map() {

        for (String s : map.keySet()) {
            //String key = s;
            String value = map.get(s);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            entry.getKey();//key
            entry.getValue();//value
        }
    }

    /**
     * 通过Key来对Map排序
     */
    private static void sortByMapKey() {

        /**
         *①通过ArrayList构造函数把map.entrySet()转换成list
         */
        ArrayList<Map.Entry<String, String>> mappingList = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        //通过比较器实现比较排序
        Collections.sort(mappingList, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> mapping1, Map.Entry<String, String> mapping2) {
                return mapping1.getKey().compareTo(mapping2.getKey());
            }
        });

        /**
         *②通过TreeMap实现
         */
        SortedMap<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {

            @Override
            public int compare(String k1, String k2) {
                return k1.compareTo(k2);
            }
        });
        sortedMap.putAll(map);
    }

    /**
     * java中提供了很多方法都可以实现对一个Map的复制，但是那些方法不见得会时时同步。
     * <p>
     * 简单说，就是一个Map发生的变化，而复制的那个依然保持原样。
     * <p>
     * 还有就是克隆,但是克隆非常有局限性，而且在很多时候造成了不必要的影响
     * <a>http://www.artima.com/intv/bloch13.html</a>
     */
    private static void copy2Map() {
        Map copiedMap = Collections.synchronizedMap(map);
    }

    /**
     * 创建一个空的Map
     */
    private static void init2Map() {
        Map map1;
        // 如果这个map被置为不可用，可以通过以下实现
        map1 = Collections.emptyMap();

        //相反，我们会用到的时候，就可以直接
        map1 = new HashMap();

    }

    public static void main(String[] args) {
        setMap();

    }
}
