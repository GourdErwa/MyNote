package com.gourd.erwa.util.corejava.document;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author lw by 14-5-30.
 */
public class Test {


    /**
     * 获取注解参数
     *
     * @param annotationClass 注解类
     * @param annotationField 注解类字段名称
     * @param aClass          使用注解的class名称
     * @param methodName      使用注解的方法名称
     * @throws Exception Exception
     */
    private static void getAnnotationPar(Class annotationClass, String annotationField, Class aClass, String methodName) throws Exception {

        Method aClassMethod = aClass.getMethod(methodName);
        Annotation annotation = aClassMethod.getAnnotation(annotationClass);
        Method method = annotation.getClass().getDeclaredMethod(annotationField);
        System.out.println(method.invoke(annotation));
    }

    public static void main(String[] args) throws Exception {
        Test.getAnnotationPar(MethodInfo.class, "Value", Test2Annotation.class, "testMyAnnotation");
    }
}

class Test2Annotation {
    @MethodInfo(Value = "自定义的注解@MethodInfo")
    public void testMyAnnotation() {

    }
}
