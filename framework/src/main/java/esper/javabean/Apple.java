package esper.javabean;

import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * apple javaBean
 */
public class Apple {

    public static final String AVG_PRICE = "avg(price)";
    public static final String CLASSNAME = Apple.class.getName();
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.CHINA);
    private static final String[] YIELDLY = new String[]{"BeiJing", "ShanXi", "HeNan", "ShangHai", "TianJing"};
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Apple.class);
    private static final String[] COLORS = new String[]{"1", "2", "3"};
    private static final int COLORS_LENGTH = COLORS.length;


    private String id; //id
    private int price; //价格
    private double discount;//折扣
    private String color;//颜色 COLORS 中随机获取
    private int size;//大小  1-10
    private Long create_time;//生产时间
    private Yieldly yieldly;//生产地

    private boolean otherField;

    Apple() {
        Random random = new Random();
        this.id = "1";
        //this.price = random.nextInt(4);
        this.price = 1;
        this.discount = random.nextDouble();
        this.color = COLORS[random.nextInt(COLORS_LENGTH)];
        this.size = random.nextInt(2);
        this.create_time = System.currentTimeMillis() - random.nextInt(3) * 1000L;
        this.yieldly = new Yieldly(YIELDLY[random.nextInt(YIELDLY.length)]);
    }

    /**
     * @return 返回一个随机创建的 {@link Apple}
     */
    public static Apple getRandomApple() {

        Apple apple = new Apple();
        LOGGER.info("~~~~~~~~~ random Apple JavaBean is <{}> ~~~~~~~~~~", "");
        return apple;
    }

    /**
     * 静态方法
     *
     * @param price    price
     * @param discount discount
     * @return 该Apple 折扣后的价格
     */
    public static double getPriceByDiscount2StaticMethod(int price, double discount) {
        return price * discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Yieldly getYieldly() {
        return yieldly;
    }

    public void setYieldly(Yieldly yieldly) {
        this.yieldly = yieldly;
    }

    /**
     * @return 该Apple 折扣后的价格
     * @see Apple#getPriceByDiscount(int, double)
     */
    public double getPriceByDiscount() {
        return this.price * this.discount;
    }

    /**
     * @param price    price
     * @param discount discount
     * @return 该Apple 折扣后的价格
     */
    public double getPriceByDiscount(int price, double discount) {
        return price * discount;
    }

    public boolean getOtherField() {
        return otherField;
    }

    public void setOtherField(boolean otherField) {
        this.otherField = otherField;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", discount=" + String.format("%.2f", discount) +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", create_time=" + FORMAT.format(new Date(create_time)) +
                ", yieldly=" + yieldly +
                ", otherField=" + otherField +
                '}';
    }
}
