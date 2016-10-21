package qbb;

/**
 * @author wei.Li
 */
class SuperClass {
    private int number;


    public SuperClass() {
        this.number = 0;
    }


    public SuperClass(int number) {
        this.number = number;
    }


    public int getNumber() {
        number++;
        return number;
    }
}

class SubClass1 extends SuperClass {
    public SubClass1(int number) {
        super(number);
    }

}

class SubClass2 extends SuperClass {
    private int number;


    public SubClass2(int number) {
        super(number);
    }

}

public class SubClass extends SuperClass {

    private int number;


    public SubClass(int number) {
        super(number);
    }

    public static void main(String[] args) {
        SuperClass s = new SubClass(20);
        SuperClass s1 = new SubClass1(20);
        SuperClass s2 = new SubClass2(20);
        System.out.println(s.getNumber());
        System.out.println(s1.getNumber());
        System.out.println(s2.getNumber());
        //结论一：多态时，当子类覆盖了父类的方法，使用子类覆盖的方法
        //结论二：当子类覆盖父类的实例变量时，父类方法使用的是父类的 实例变量，子类方法使用的是子类的实例变量
    }

    public int getNumber() {
        number++;
        return number;
    }

}
