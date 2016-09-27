package com.gourd.erwa.util.corejava.basis.urlproperties;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-7
 */
public class GetResourceAsStream {


    protected static void aVoid() {
        URL uri = GetResourceAsStream.class.getClassLoader()
                .getResource("esper_benchmark.properties");
        System.out.println(uri != null ? uri.getPath() : null);
    }

    public static void main(String[] args) {

        aVoid();
    }
}
