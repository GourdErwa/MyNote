package com.gourd.erwa.util.corejava.essence_36;

/**
 * Created by lw on 14-5-19.
 * <p>
 * Class到底怎么初始化，测试内容已打乱顺序
 * <p>
 * 1.先执行静态，按照静态语句块或者静态变量的顺序执行
 * 2.执行main
 * 3.非静态变量的初始化，按声明顺序执行
 * 4.构造代码块
 * 5.构造方法
 * -----------------
 * 静态代码执行几次？
 * 父类子类执行顺序？
 * 如果是父类的引用指向子类呢？
 * 如果成员变量中引用了其他的class呢？
 * 对于 int i=10; int j; 执行i=10时候 j 声明了吗？如果是静态的呢？
 */
public class InitClass extends SuperInitClass {

  /*  static {
        System.out.println("InitClass->静态代码块 1");
    }

    static String s1 = "InitClass->静态变量s1";

    static String s2 = getS2();
    static String s5;
    {
        System.out.println("InitClass->构造方法代码块");
        System.out.println("InitClass->构造方法代码块 执行时候成员变量s3是否已经初始化了？" + (s3 != null));
    }
    static {
        System.out.println("InitClass->静态代码块 2");
        System.out.println("InitClass->静态代码块 2->访问static String s1=" + s1);
    }
    String s3 = "InitClass->成员变量s3";
    String s4 = getS4();

    InitClass() {
        System.out.println("InitClass->构造方法");
        System.out.println("InitClass->构造方法 执行时候成员变量s3是否已经初始化了？" + (s3 != null));
    }

    static String getS2() {
        System.out.println("InitClass->getS2()执行->初始化静态变量s2");
        return "InitClass->初始化静态变量s2";
    }

    static String getS4() {
        System.out.println("InitClass->getS4()执行->初始化成员变量s4");
        return "InitClass->初始化成员变量s4";
    }*/

    public static void main(String[] args) throws Exception {
        System.out.println("main.........run");
        System.out.println();
        System.out.println("------------InitClass initClass = new InitClass();----------------执行");
        InitClass initClass = new InitClass();
        System.out.println();
        System.out.println("------------SuperInitClass superInitClass = new InitClass();----------------执行");
        SuperInitClass superInitClass = new InitClass();
        InitQuestion initQuestion = new InitQuestion();
        Init init = new Init();
    }

}

class SuperInitClass {
    static String s1 = "SuperInitClass->静态变量s1";
    static String s2 = getS2();

    static {
        System.out.println("SuperInitClass->静态代码块 1");
    }

    static {
        System.out.println("SuperInitClass->静态代码块 2");
        System.out.println("SuperInitClass->静态代码块 2->访问static String s1=" + s1);
    }

    String s3 = "SuperInitClass->成员变量s3";
    String s4 = getS4();

    People people = new People();

    {
        System.out.println("SuperInitClass->构造方法代码块");
        System.out.println("SuperInitClass->构造方法代码块 执行时候成员变量s3是否已经初始化了？" + (s3 != null));
    }

    SuperInitClass() {
        System.out.println("SuperInitClass->构造方法");
        System.out.println("SuperInitClass->构造方法 执行时候成员变量s3是否已经初始化了？" + (s3 != null));
    }

    static String getS2() {
        System.out.println("SuperInitClass->getS2()执行->初始化静态变量s2");
        return "SuperInitClass->初始化静态变量s2";
    }

    static String getS4() {
        System.out.println("SuperInitClass->getS4()执行->初始化成员变量s4");
        return "SuperInitClass->初始化成员变量s4";
    }
}

class People {

    People() {
        System.out.println("PeopleClass->init...");
    }
}


/**
 * 深入讨论初始化
 * 为什么static{}与构造方法{}语句块不许有return呢？
 * 为什么static{}语句块里不可用抛出异常呢?
 * 为什么构造方法{}语句块抛出异常后所有构造方法都需要抛出这个异常呢？
 * -----------------------------
 * 经过整理后的class如【InitQuestion_2.java内容所示】
 */
class InitQuestion {


    private static int xStatic;
    private static int yStatic = 20;

    static {
        xStatic = 20;
        if (yStatic < 20) {
            //   throw new Exception();
        }
        // return ;
    }

    private int y = 20;
    private int x;

    {
        x = 20;
        if (y < 20) {
            throw new Exception();
        }

        //return ;
    }


    public InitQuestion() throws Exception {
        this(0, 0);
    }

    public InitQuestion(int x) throws Exception {
    }

    public InitQuestion(int x, int y) throws Exception {

    }
}

/**
 * 静态变量的初始化工作默认放入static{}语句块前执行
 * 即：生成一个<clinit>方法
 * 实例变量的初始化工作默认放入构造方法{}语句块前执行
 * 然后把语句块放入每个构造方法的前面
 * 即：每个构造器生成一个<init>方法
 * <p>
 * <clinit><init>证明如下【Init.java】文件执行后的代码
 */
class InitQuestion_2 {
    private static int xStatic;
    private static int yStatic;

    static {
        yStatic = 20;
        xStatic = 20;
        if (yStatic < 20) {
            //
        }
    }

    private int x;
    private int y;

    public InitQuestion_2() throws Exception {
        this(0, 0);
    }

    public InitQuestion_2(int x) throws Exception {
        y = 20;
        x = 20;
        if (y < 20) {
            throw new Exception();
        }
    }

    public InitQuestion_2(int x, int y) throws Exception {
        y = 20;
        x = 20;
        if (y < 20) {
            throw new Exception();
        }
    }

}

class Init {

    private static int xStatic = getXStatic();

    static {
        try {
            throw new Exception("静态语句块执行...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int x = getX();

    {
        try {
            throw new Exception("构造方法语句块执行...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Init() {
        try {
            throw new Exception("构造方法执行...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getXStatic() {
        try {
            throw new Exception("静态变量xStatic初始化...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 10;
    }

    private int getX() {
        try {
            throw new Exception("实例变量x初始化...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 10;
    }
}

/**
 * 先静态语句块，静态变量，按声明顺序初始化
 * 然后变量，构造方法语句块，按声明顺序初始化
 * 最后执行构造方法
 * <p>
 * 有父类，先初始化父类
 */
