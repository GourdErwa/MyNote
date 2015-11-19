package kaimo;

/**
 * @author wei.Li by 15/11/11
 */
public class YinYongChuanDi {

/*        public static   void  change( int [] counts) {
            counts[ 0 ] =  6 ;
            System.out.println(counts[ 0 ]);
        }

        public   static   void  main(String[] args) {
            int [] count = {  1 ,  2 ,  3 ,  4 ,  5  };
            change(count);
        }*/


    /*String str = new String("good");

    char[] ch = {'a', 'b', 'c'};

    public static void main(String args[]) {
        YinYongChuanDi ex = new YinYongChuanDi();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str + " and ");
        System.out.println(ex.ch);
    }

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'g';
    }
*/


    public static void main(String[] args) {
        StringBuffer s = new StringBuffer("good");
        StringBuffer s2 = new StringBuffer("bad");
        test(s, s2);
        System.out.println(s);//9
        System.out.println(s2);//10
    }

    static void test(StringBuffer s, StringBuffer s2) { //此时会在方法栈中新建引用，但指向同一内存地址
        System.out.println(s);//1
        System.out.println(s2);//2
        s2 = s;//3
        s = new StringBuffer("new");//4
        System.out.println(s);//5
        System.out.println(s2);//6
        s.append("hah");//7
        s2.append("hah");//8
    }
}
