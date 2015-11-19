package kaimo;

/**
 * @author wei.Li by 15/11/11
 */
public class ZhiChuanDi {
    public void zhi(int a) {
        a=a+1;
        System.out.println(a);
    }

    public static void main(String[] args) {
        int b=5;
        ZhiChuanDi zhiChuanDi=new ZhiChuanDi();
        zhiChuanDi.zhi(b);
        System.out.println(b);
    }
}
