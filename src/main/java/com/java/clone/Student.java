package com.java.clone;

import java.util.Vector;

/**
 * Created by lw on 14-5-5.
 * <p>
 * 浅拷贝
 * <p>
 * 深拷贝
 */
public class Student implements Cloneable {

    private int id;
    private String name;
    private Vector courses;

    public Student(int id, String name, Vector courses) {

        try {

            Thread.sleep(1000);
            System.out.println("Student is create ....");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.id = id;
        this.name = name;
        this.courses = courses;
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

    public Vector getCourses() {
        return courses;
    }

    public void setCourses(Vector courses) {
        this.courses = courses;
    }

    /**
     * 浅拷贝
     * 克隆对象拥有和原始对象相同的引用，不是值拷贝。
     *
     * @return Student
     */
    public Student newInstance() {

        try {
            return (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 深拷贝
     *
     * @return Object
     */
    @Override
    protected Object clone() {
        Student student = null;
        try {
            student = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Vector vector = null;
        if (student != null) {
            vector = student.getCourses();
        }
        Vector vector1 = new Vector();
        if (vector != null) {
            for (Object o : vector) {
                vector1.add(o);
            }
        }
        student.setCourses(vector1);
        return student;

    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
