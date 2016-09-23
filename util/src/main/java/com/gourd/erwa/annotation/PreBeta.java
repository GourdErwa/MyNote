package com.gourd.erwa.annotation;

import java.lang.annotation.*;

/**
 * 标识内容正在测试 , 目前不可用于正式环境
 *
 * @author wei.Li
 */
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.TYPE})
@Documented
public @interface PreBeta {

    String author();

    String description() default "";

}
