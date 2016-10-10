package com.gourd.erwa.corejava.clone;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * list 浅拷贝问题
 *
 * @author wei.Li
 */
public class CollectionClone {


    public static void main(String[] args) {

        final List<Map<String, String>> maps = Lists.newArrayList();
        maps.add(
                new HashMap<String, String>() {{
                    put("name", "LW");
                }}
        );

        System.out.println(maps);

        //遍历 10 次，记录遍历每个 phone 的结果到 lists
        final List<List<Map<String, String>>> lists = Lists.newArrayList();

        for (int phone = 0; phone < 10; phone++) {
            for (Map<String, String> map : maps) {
                map.put("phone", String.valueOf(phone));
                lists.add(maps);
            }
        }

        System.out.println(lists);
    }
}


