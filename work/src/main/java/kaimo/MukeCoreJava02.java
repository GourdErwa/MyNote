package kaimo;

import java.util.Scanner;

/**
 * 滴滴租车系统
 *
 * @author HuKaiMo on 2015/8/17.
 */
public class MukeCoreJava02 {

    public static void main(String[] args) {
        Foremost dafd = new Foremost();
        System.out.println(dafd);
    }

    public static void information() {

    }
}

class Information {
    private String name;
    private double price;//价格
    private double load;//载重
    private double manned;//载人

    public Information(String name, double price, double load, double manned) {
        this.name = name;
        this.price = price;
        this.load = load;
        this.manned = manned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public double getManned() {
        return manned;
    }

    public void setManned(double manned) {
        this.manned = manned;
    }
}

class Foremost {
    public Foremost() {
        int sum = 0;
        StringBuffer str = new StringBuffer();
        Information xfl = new Information("雪佛兰", 500, 0, 4);
        Information aodi = new Information("奥迪 ", 800, 0, 5);
        Information santy = new Information("三拖一", 1000, 40, 0);
        Information pika = new Information("皮卡 ", 600, 5, 3);
        Information[] arr = {xfl, aodi, santy, pika};
        StringBuffer st1 = new StringBuffer();
        Scanner input = new Scanner(System.in);
        System.out.println("是否需要租车：1、是；0、否");
        int tongguo = input.nextInt();
        if (tongguo == 1) {
            System.out.println("你可以选择的车及价格表");
            System.out.println("序号 " + "名称" + "\t\t" + "价格" + "\t" + "载重量" + "\t\t" + "载客量");
            for (int i = 0; i < arr.length; i++) {
                System.out.println((i + 1) + "\t " + arr[i].getName() + "\t" + arr[i].getPrice() + "元/天" + "\t" + "载重量" + arr[i].getLoad() + "\t" + "载客量" + arr[i].getManned());
            }
            System.out.println("选择你要租车的数量：");
            int number = input.nextInt();
            for (int x = 0; x < number; x++) {
                System.out.println("请输入第" + (x + 1) + "辆车的序号");
                int xuhao = input.nextInt();
                if (xuhao > 0 && xuhao <= arr.length) {
                    sum += arr[xuhao - 1].getPrice();
                    str.append(arr[xuhao - 1].getName() + "\t");
                } else {
                    System.out.println("不在规定范围内");
                    continue;
                }
            }
            System.out.println("请输入要租的天数：");
            int day = input.nextInt();
            System.out.println("你的账单：");
            System.out.print("所选车辆：");
            System.out.println(str);
            System.out.println("租赁天数：" + day);
            System.out.println("租车总价：" + sum * day);
        } else if (tongguo == 0) {
            System.out.println("滚");
            return;
        } else {
            System.out.println("输入不合法");
        }
    }
}
