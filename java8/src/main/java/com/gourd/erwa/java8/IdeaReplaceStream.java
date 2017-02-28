package com.gourd.erwa.java8;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wei.Li by 2017/1/11
 */
public class IdeaReplaceStream {

    private List<Integer> collect(List<Integer> list) {

        return list.stream().filter(integer -> integer > 5).collect(Collectors.toList());
    }
}
