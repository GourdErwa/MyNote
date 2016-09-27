package com.gourd.erwa.util.corejava.class_name;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lw on 14-7-18.
 * <p>
 * TODO 一般，用于load class的时候，比如说Class.forName，就需要用Class.getName而不是Class.getCononicalName
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * public String getCanonicalName()
 * 返回 Java Language Specification 中所定义的底层类的规范化名称。
 * 如果底层类没有规范化名称（即如果底层类是一个组件类型没有规范化名称的本地类、匿名类或数组），则返回 null。
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * 以 String 的形式返回此 Class 对象所表示的实体类、接口、数组类、基本类型或 void名称。
 * 如果此类对象表示的是非数组类型的引用类型，则返回该类的二进制名称，Java Language Specification, Second Edition 对此作了详细说明。
 * <p>
 * 如果此类对象表示一个基本类型或 void，则返回的名字是一个与该基本类型或 void 所对应的 Java 语言关键字相同的 String。
 * <p>
 * 如果此类对象表示一个数组类，则名字的内部形式为：表示该数组嵌套深度的一个或多个 '[' 字符加元素类型名。元素类型名的编码如下：
 * <p>
 * Element Type	    	 Encoding
 * boolean	    	         Z
 * byte	    	             B
 * char	    	             C
 * class or interface	    Lclassname;
 * double	    	         D
 * float	    	         F
 * int	    	             I
 * long	    	             J
 * short	    	         S
 * 类或接口名 classname 是上面指定类的二进制名称。
 */
public class Class_Name implements Serializable {

    private static String string = "hulu";
    private static Integer integer = 1;
    private static float aFloat = 100L;
    private static ArrayList arrayList = new ArrayList();
    private static String[] strings = new String[1];
    private static double[] doubles = new double[1];

    public static void main(String[] args) {
        Class class_Name = Class_Name.class;
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~非内部类~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("class_Name.getName()->          \t" + class_Name.getName());
        System.out.println("class_Name.getCanonicalName()-> \t" + class_Name.getCanonicalName());
        System.out.println("class_Name.getSimpleName()->    \t" + class_Name.getSimpleName());
        System.out.println("class_Name.getTypeName()->      \t" + class_Name.getTypeName());

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~内部类~~~~~~~~~~~~~~~~~~~~~~");
        Class internal_aClass = Internal_Static_Class.class;
        System.out.println("internal_aClass.getName()->          \t" + internal_aClass.getName());
        System.out.println("internal_aClass.getCanonicalName()-> \t" + internal_aClass.getCanonicalName());
        System.out.println("internal_aClass.getSimpleName()->    \t" + internal_aClass.getSimpleName());
        System.out.println("internal_aClass.getTypeName()->      \t" + internal_aClass.getTypeName());

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~String~~~~~~~~~~~~~~~~~~~~~~");
        Class string_aClass = string.getClass();
        System.out.println("string_aClass.getName()->          \t" + string_aClass.getName());
        System.out.println("string_aClass.getCanonicalName()-> \t" + string_aClass.getCanonicalName());
        System.out.println("string_aClass.getSimpleName()->    \t" + string_aClass.getSimpleName());
        System.out.println("string_aClass.getTypeName()->      \t" + string_aClass.getTypeName());

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~Integer~~~~~~~~~~~~~~~~~~~~~~");
        Class integer_aClass = integer.getClass();
        System.out.println("integer_aClass.getName()->          \t" + integer_aClass.getName());
        System.out.println("integer_aClass.getCanonicalName()-> \t" + integer_aClass.getCanonicalName());
        System.out.println("integer_aClass.getSimpleName()->    \t" + integer_aClass.getSimpleName());
        System.out.println("integer_aClass.getTypeName()->      \t" + integer_aClass.getTypeName());


        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~ArrayList~~~~~~~~~~~~~~~~~~~~~~");
        Class arrayList_aClass = arrayList.getClass();
        System.out.println("arrayList_aClass.getName()->          \t" + arrayList_aClass.getName());
        System.out.println("arrayList_aClass.getCanonicalName()-> \t" + arrayList_aClass.getCanonicalName());
        System.out.println("arrayList_aClass.getSimpleName()->    \t" + arrayList_aClass.getSimpleName());
        System.out.println("arrayList_aClass.getTypeName()->      \t" + arrayList_aClass.getTypeName());

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~String[]~~~~~~~~~~~~~~~~~~~~~~");
        Class strings_aClass = strings.getClass();
        System.out.println("strings_aClass.getName()->          \t" + strings_aClass.getName());
        System.out.println("strings_aClass.getCanonicalName()-> \t" + strings_aClass.getCanonicalName());
        System.out.println("strings_aClass.getSimpleName()->    \t" + strings_aClass.getSimpleName());
        System.out.println("strings_aClass.getTypeName()->      \t" + strings_aClass.getTypeName());

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~double[]~~~~~~~~~~~~~~~~~~~~~~");
        Class doubles_aClass = doubles.getClass();
        System.out.println("doubles_aClass.getName()->          \t" + doubles_aClass.getName());
        System.out.println("doubles_aClass.getCanonicalName()-> \t" + doubles_aClass.getCanonicalName());
        System.out.println("doubles_aClass.getSimpleName()->    \t" + doubles_aClass.getSimpleName());
        System.out.println("doubles_aClass.getTypeName()->      \t" + doubles_aClass.getTypeName());

    }

    public static class Internal_Static_Class {

    }

    /**
     * 打印结果
     *
     */
}
