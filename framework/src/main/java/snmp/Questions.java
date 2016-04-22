package snmp;


public class Questions {

    public static void main(String[] args) {

        questions1();
        questions2();
        questions3();
        questions4();
        questions5();
    }

    /* questions1 */
    private static void questions1() {
        String str = "str";
        Questions.appendStr(str);
        System.out.println(str);
    }

    private static void appendStr(String str) {
        str += "1";
    }

    /* questions2 */
    private static void questions2() {

        new B();
        System.out.println();
    }

    /* questions3 */
    private static void questions3() {
        int a = 3, b = 4;

        if (a++ == 3 &&
                ++b == 5 &&
                ++a + b++ == 10 &&
                a++ == 7 &&
                ++b == 11) {
            System.out.println("a:" + a + "   b:" + b);
        } else {
            System.out.println("a:" + a + "   b:" + b);
        }
    }

    /* questions4 */
    private static void questions4() {

        int i = 0;
        try {
            for (; i < 5; i++) {
                if (i == 3) {
                    throw new Exception("i==3");
                }
            }
            i -= 10;
        } catch (Exception ignored) {
        } finally {
            i += 10;
        }

        System.out.println(i);
    }

    /* questions5 */
    public static void questions5() {
        int result = 0;
        int i = 2;
        switch (i) {
            case 1: {
                result = result + i;
            }
            case 2: {
                result = result + i * 2;
            }
            case 3: {
                result = result + i * 3;
            }
            break;
        }
        System.out.println(result);
    }

    /**
     * 内部类 A , B
     */
    static class A {

        private static final String STR = initStaticStr();
        private final String initStr = initStr();

        {
            System.out.print("1");
        }

        public A() {
            System.out.print("2");
        }

        private static String initStaticStr() {
            System.out.print("4");
            return "initStaticStr";
        }

        private String initStr() {
            System.out.print("3");
            return "initStr";
        }
    }

    static class B extends A {
        public B() {
            System.out.print("5");
        }
    }
}

class A {


}
/*
二、在java中wait和sleep方法的不同？

三、为什么我们调用start()方法时会执行run()方法，为什么我们不能直接调用run()方法？


四、List集合 A= [1,3,5,7] B= [1,2,5] ,写程序实现 A 和 B 的交集、并集、差集 ?


五、 用递归算法写一段程序实现 1+2+...+1000000


 */
