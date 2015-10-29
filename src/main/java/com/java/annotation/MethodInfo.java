package com.java.annotation;

import java.lang.annotation.*;

/**
 * Created by lw on 14-5-30.
 * 自定义注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo {
    public String Value() default "暂无说明";
}

