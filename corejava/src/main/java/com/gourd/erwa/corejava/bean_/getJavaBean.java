package com.gourd.erwa.util.corejava.bean_;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Map;

/**
 * Created by lw on 14-5-29.
 */
public class getJavaBean {

    public static Object getJavaBean(Class<?> tClass, Map map) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(tClass);
        return new Object();
    }

}

class People {
    private String name;
    private int age;


}
