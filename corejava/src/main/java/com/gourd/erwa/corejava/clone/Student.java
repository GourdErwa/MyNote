package com.gourd.erwa.util.corejava.clone;

import java.util.Vector;

/**
 * Created by lw on 14-5-5.
 * <p>
 * 浅拷贝
 * <p>
 * 深拷贝
 */
class Student implements Cloneable {

    private int id;
    private String name;
    private Vector<Object> courses;

    Student(int id, String name, Vector<Object> courses) {

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

    public Vector<Object> getCourses() {
        return courses;
    }

    public void setCourses(Vector<Object> courses) {
        this.courses = courses;
    }

    /**
     * 浅拷贝
     * 克隆对象拥有和原始对象相同的引用，不是值拷贝。
     *
     * @return Student
     */
    Student newInstance() {

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
    protected Object clone() throws CloneNotSupportedException {
        Student student = null;
        try {
            student = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Vector<Object> vector = null;
        if (student != null) {
            vector = student.getCourses();
        }
        Vector<Object> vector1 = new Vector<>();
        if (vector != null) {
            for (Object o : vector) {
                vector1.add(o);
            }
        }
        if (student != null) {
            student.setCourses(vector1);
        }
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
