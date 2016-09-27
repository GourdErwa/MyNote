package com.gourd.erwa.util.corejava.essence_36;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 14-5-23.
 * <p>
 * 构造器做了什么？
 * 对象是谁创建的？构造器？
 * this到底是什么？
 */

class SupperConstructorInvocation {

    SupperConstructorInvocation() {
        this(1);//只能是第一句调用其他构造器，默认super();最后递归到Object
        //this(1,2); //error
        //super();   //error
    }

    SupperConstructorInvocation(double d) {
        //this();
        System.out.println(d);
    }

    SupperConstructorInvocation(int i, int y) {
        //this();
    }
}

public class ConstructorInvocation extends SupperConstructorInvocation {

    public static void main(String[] args) {
        /*ConstructorInvocation constructorInvocation
                =new ConstructorInvocation(1);*/
        //构造器不能继承

        //测试，new对象时候构造器的参数列表执行了吗？
        new SupperConstructorInvocation(100 / Math.PI);
        //输出31.830988618379067,说明100 / Math.PI执行了才执行构造函数里的内容

        /**
         *  运行内存
         *   -Xms1m -Xmx1m
         *  执行结果如图1，说明对象创建成功后才去执行构造方法
         *  不是构造方法创建的对象
         */
        CreateObject.getMaxObjects();

    }

    public void demo() {
        //this(); //error ,只能在构造器中调用构造器
    }
}


class CreateObject {

    private static final List<CreateObject> CREATE_OBJECT_LIST = new ArrayList<CreateObject>();

    CreateObject() {
        CreateObject object = new CreateObject();
    }

    CreateObject(int temp) {
        System.out.println("CreateObject(int temp)->run...");
    }

    public static void getMaxObjects() {
        int temp = 0;
        while (true) {
            try {
                CREATE_OBJECT_LIST.add(new CreateObject(temp = 1));
                temp = 0;
            } catch (Exception e) {

            } finally {
                System.out.println("对象创建时成功时候：构造方法执行了吗？" + (temp == 0));
            }
        }
    }
}

class This {

    This() {

    }

    This(int i) {

    }

    /**
     * 相当于

     This(This this){
     }

     This(This this,int i){
     }

     */

}
