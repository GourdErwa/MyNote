package elasticsearch;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: lw
 * Date: 14-7-8
 */
public class User {

    private String name;
    private String home;//家乡
    private String now_home;//现住址
    private double height;//身高
    private int age;
    private Date birthday;
    private boolean isRealMen = false; //是否是男人
    private Location location;//地理位置

    public User() {
    }

    public User(String name, String home, String now_home, double height, int age, Date birthday, boolean isRealMen, Location location) {
        this.name = name;
        this.home = home;
        this.now_home = now_home;
        this.height = height;
        this.age = age;
        this.birthday = birthday;
        this.isRealMen = isRealMen;
        this.location = location;
    }

    /**
     * 随机生成一个用户信息
     *
     * @return User
     */
    public static User getOneRandomUser() {
        Random random = new Random();
        Location location = new Location(random.nextDouble(), random.nextDouble());
        String home = "山西省太原市" + random.nextInt(10000) + "街道";
        String now_home = random.nextBoolean() ? "北京市" + random.nextInt(10000) + "街道" : home;
        return new User("葫芦" + random.nextInt(10000) + "娃", home, now_home, random.nextInt(10000),
                random.nextInt(10000), new Date(System.currentTimeMillis() - (long) (Math.random() * 100000)), random.nextBoolean(), location);
    }

    /**
     * 随机生成num个用户信息
     *
     * @param num 生成数量
     * @return List User
     */
    public static List<User> getRandomUsers(int num) {
        List<User> users = new ArrayList<>();
        if (num < 0) num = 10;
        for (int i = 0; i < num; i++) {
            Random random = new Random();
            Location location = new Location(random.nextDouble(), random.nextDouble());
            String home = "山西省太原市" + random.nextInt(10000) + "街道";
            String now_home = random.nextBoolean() ? "北京市" + random.nextInt(10000) + "街道" : home;
            users.add(new User("葫芦" + random.nextInt(10000) + "娃", home, now_home, random.nextInt(10000),
                    random.nextInt(10000), new Date(System.currentTimeMillis() - (long) (Math.random() * 100000)), random
                    .nextBoolean(), location));
        }

        return users;
    }

    /**
     * 封装对象的Json信息
     *
     * @param user user
     * @return XContentBuilder
     * @throws IOException IOException
     */
    static XContentBuilder getXContentBuilder(User user) throws IOException {
        return XContentFactory.jsonBuilder()
                .startObject()
                .field("@timestamp", System.currentTimeMillis())
                .field("name", user.getName())//该字段在上面的方法中mapping定义了,所以该字段就有了自定义的属性,比如 age等
                .field("home", user.getHome())
                .field("now_home", user.getNow_home())
                .field("height", user.getHeight())
                .field("age", user.getAge())
                .field("birthday", user.getBirthday())
                .field("isRealMen", user.isRealMen())
                .startObject("location")
                .field("lat", user.getLocation().getLat())
                .field("lon", user.getLocation().getLon())
                .endObject()
                .field("state", "默认属性,mapping中没有定义")//该字段在上面方法中的mapping中没有定义,所以该字段的属性使用es默认的.
                .endObject();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getNow_home() {
        return now_home;
    }

    public void setNow_home(String now_home) {
        this.now_home = now_home;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isRealMen() {
        return isRealMen;
    }

    public void setRealMen(boolean isRealMen) {
        this.isRealMen = isRealMen;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

/**
 * 地理位置信息
 */
class Location {
    private double lat;
    private double lon;

    Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
