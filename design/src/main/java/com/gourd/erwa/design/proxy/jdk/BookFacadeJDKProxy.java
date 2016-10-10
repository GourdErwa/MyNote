package com.gourd.erwa.design.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lw by 14-5-1.
 */
class BookFacadeJDKProxy implements InvocationHandler {

    private Object object;

    /**
     * 绑定委托对象并返回一个代理类
     *
     * @param object 委托对象
     * @return 代理类
     */
    Object bind(Object object) {
        this.object = object;
        //取得代理对象
        final Class<?> objectClass = object.getClass();

        /*
         ClassLoader loader：    类加载器
         Class<?>[] interfaces： 得到全部的接口
         InvocationHandler h：   得到InvocationHandler接口的子类实例
         */
        return Proxy.newProxyInstance(
                objectClass.getClassLoader(),
                objectClass.getInterfaces(),
                this);   //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)

    }

    /**
     * @param proxy  指被代理的对象
     * @param method 要调用的方法
     * @param args   方法调用时所需要的参数
     * @return Object
     * @throws Throwable Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before ...");
        method.invoke(object, args);
        System.out.println("after ...");
        return null;
    }
}


