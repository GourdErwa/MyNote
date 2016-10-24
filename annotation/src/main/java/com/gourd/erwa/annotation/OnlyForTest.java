package com.gourd.erwa.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 测试内容标识 , 被标识内容永远不参与正式环境运行 ,仅作为辅助开发
 *
 * @author wei.Li
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = {FIELD, TYPE, METHOD})
public @interface OnlyForTest {

    String description() default "";

}
