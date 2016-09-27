package com.gourd.erwa.util.corejava.essence_36;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lw on 14-5-20.
 * 学生姓名与成绩
 */
class Student implements Comparable<Student> {

    String name;
    int score;

    /**
     * 衍生疑问：
     * 为什么要有构造器？
     * 他做了什么？
     * 对象到底是谁创建的？构造器里面吗？
     * this到底什么？
     */
    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * 重写排序实现方法
     * 按成绩比较
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Student o) {
        if (o.score > this.score) {
            return -1;
        }
        if (o.score < this.score) {
            return 1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}

//学生的详细信息
class Student_Message {
    String address;

    Student_Message(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "address='" + address;
    }
}

public class Tree_Map {

    //①：比较的对象实现了Comparable接口
    private static Map<Student, Student_Message> map = new TreeMap();

    //①：将Comparator 传入构造器
    private static Map<Student, Student_Message> map_1 = new TreeMap(new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    });
    /**
     * 衍生疑问：
     * 以上2种方法有什么区别。
     * 为什么将Comparator还需要重写equals方法？
     */

    /**
     * 初始化treeMap
     */
    private static void InitTreeMap() {
        Student yiwa = new Student("yiwa", 20);
        Student erwa = new Student("erwa", 30);
        Student sanwa = new Student("sanwa", 40);
        Student siwa = new Student("siwa", 80);
        Student wuwa = new Student("wuwa", 100);
        map.put(yiwa, new Student_Message("TY"));
        map.put(erwa, new Student_Message("TY"));
        map.put(sanwa, new Student_Message("TY"));
        map.put(siwa, new Student_Message("TY"));
        map.put(wuwa, new Student_Message("TY"));

        //查询一娃~~四娃成绩之间的学生
        System.out.println(((TreeMap) map).subMap(yiwa, siwa));
        System.out.println();
        //查询成绩大于一娃的学生
        System.out.println(((TreeMap) map).tailMap(yiwa));
        System.out.println();
        //查询成绩小于三娃的学生
        System.out.println(((TreeMap) map).headMap(sanwa));
    }

    public static void main(String[] args) {
        InitTreeMap();
    }
}
