package com.gourd.erwa.java8;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wei.Li
 */
public class StreamUse {

    public static void main(String[] args) {

        final String hh = Stream
                .generate(
                        new Supplier<Long>() {
                            private long i = 0;

                            @Override
                            public Long get() {
                                return i++;
                            }
                        }
                )
                .skip(1).limit(5)
                .filter(aLong -> aLong % 2 == 0)
                .map(Object::toString)
                .reduce("HH", (s, s2) -> s + "**" + s2);
        System.out.println(hh);

        List<Choice> choices = null;

        Map<String, List<Choice>> result =
                choices.stream().collect(Collectors.groupingBy(Choice::getName));


    }
}
