/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package performance;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author wei.Li by 14/12/23 (gourderwa@163.com).
 */
public class ForAndForeach {

    public static void main(String[] args) {
        updateUser();

        String s = "1";
        updateStr(s);
        System.out.println(s);

        ComparisonChain.start();

        final double ceil = Math.ceil(2.23F);
        System.out.println(ceil);
        System.out.println(Math.floor(2.23F));

        int i = 1;
        int u = i = 10;

        System.out.println(u + "=" + i);

    }


    private static String updateStr(String s) {
        s += "2";
        return s;
    }

    private static void updateUser() {
        List<User> userList = Lists.newArrayList(new User(1, "1"), new User(2, "2"));

        for (User next : userList) {
            next.setId(4);
            next.setName("4");
            next = new User(3, "3");
        }
        System.out.println(userList);
    }

}

class User {

    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
