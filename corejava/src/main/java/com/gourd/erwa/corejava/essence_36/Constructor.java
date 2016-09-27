package com.gourd.erwa.util.corejava.essence_36;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 14-5-19.
 */
public class Constructor {

    Constructor(int i) {

    }

    public static void demo_1() {
        List<Constructor> connectionArrayList = new ArrayList<Constructor>(20000);
        Constructor connection;
        int temp = 0;
        try {
            while (true) {
                connection = new Constructor(temp = 1);
                temp = 0;
                connectionArrayList.add(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println(temp);
        } finally {
        }
    }

    public static void main(String[] args) {
        demo_1();
    }
}
