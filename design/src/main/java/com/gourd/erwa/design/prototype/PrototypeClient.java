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

package com.gourd.erwa.design.prototype;

import java.util.Hashtable;

/**
 * @author wei.Li by 15/3/31 (gourderwa@163.com).
 */
class PrototypeClient {

    public static void main(String[] args) {
        ShapeCache.loadCache();
        Shape clonedShape = ShapeCache.getShape("1");
        System.out.println("Shape : " + clonedShape.getType());
        Shape clonedShape2 = ShapeCache.getShape("2");
        System.out.println("Shape : " + clonedShape2.getType());
        Shape clonedShape3 = ShapeCache.getShape("3");
        System.out.println("Shape : " + clonedShape3.getType());
    }

}

/**
 * 抽象模型
 */
abstract class Shape implements Cloneable {

    private String type;
    private String id;
    private String commonData;

    Shape(String type) {
        this.type = type;
    }

    abstract void draw();

    String getType() {
        return type;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public String getCommonData() {
        return commonData;
    }

    public void setCommonData(String commonData) {
        this.commonData = commonData;
    }

    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}

/**
 * 长方形模具
 */
class Rectangle extends Shape {


    Rectangle(String type) {
        super(type);
    }

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

/**
 * 正方形模具
 */
class Square extends Shape {


    Square(String type) {
        super(type);
    }

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

/**
 * 圆形模具
 */
class Circle extends Shape {

    Circle(String type) {
        super(type);
    }

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}

class ShapeCache {

    private static Hashtable<String, Shape> shapeMap = new Hashtable<>();

    static Shape getShape(String shapeId) {
        Shape cachedShape = shapeMap.get(shapeId);
        return (Shape) cachedShape.clone();
    }

    // for each shape run database query and create shape
    // shapeMap.put(shapeKey, shape);
    // for example, we are adding three shapes
    static void loadCache() {
        Circle circle = new Circle("circle");
        circle.setId("1");
        shapeMap.put(circle.getId(), circle);
        Square square = new Square("square");
        square.setId("2");
        shapeMap.put(square.getId(), square);
        Rectangle rectangle = new Rectangle("rectangle");
        rectangle.setId("3");
        shapeMap.put(rectangle.getId(), rectangle);
    }
}
