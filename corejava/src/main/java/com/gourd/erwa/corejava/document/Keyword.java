package com.gourd.erwa.util.corejava.document;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 关键字
 *
 * @author wei.Li by 14-8-26.
 */
@Documented
@Target(value = {CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Keyword {
    public String keyWord() default "";

    public String Description() default "";
}
