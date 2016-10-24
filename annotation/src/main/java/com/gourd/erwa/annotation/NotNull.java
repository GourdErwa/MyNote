package com.gourd.erwa.annotation;

import java.lang.annotation.*;

/**
 * 变量 参数 返回值 不为空说明
 *
 * @author wei.Li by 15/10/14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
public @interface NotNull {
}
