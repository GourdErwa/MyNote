package com.gourd.erwa.concurrent.jdkpool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author lw by 14-7-2.
 */
public class Volatile_ implements Runnable {

    private static volatile int i = 0;

    public static void main(String[] args) {
       /* for (int i = 0; i < 1000; i++) {
            new Volatile_().run();
        }*/
        //System.out.println(i);
        List<String> strings = new ArrayList<String>();
        strings.add("1");
        strings.add("2");

        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals("1")) {
                iterator.remove();
            }
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i++;
    }

    public class NumberRange {
        private int lower, upper;

        public int getLower() {
            return lower;
        }

        public void setLower(int value) {
            if (value > upper) throw new IllegalArgumentException("...");
            lower = value;
        }

        public int getUpper() {
            return upper;
        }

        public void setUpper(int value) {
            if (value < lower) throw new IllegalArgumentException("...");
            upper = value;
        }
    }
}
