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

package com.gourd.erwa.design.abstractfactory;

interface Shape {
    void draw();
}


interface Color {
    void fill();
}

/**
 * 抽象工厂模式
 * <p>
 * 与工厂模式区别：
 * 工厂模式直接生产具体产品。
 * 抽象工厂生产每个系列产品的工厂，每个系列产品的工厂生产具体产品
 *
 * @author wei.Li by 15/3/31 (gourderwa@163.com).
 */
class AbstractFactoryClient {

    public static void main(String[] args) {

        final ColorFactory factory = ((ColorFactory) FactoryProducer.getFactory(ColorFactory.class));
        final Blue blue = (Blue) factory.getColor(Blue.class);
        blue.fill();

    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}

class Red implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Red::fill() method.");
    }
}

class Green implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Green::fill() method.");
    }
}

class Blue implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}

/**
 * 抽象工厂
 */
abstract class AbstractFactory {

    abstract <T extends Color> Color getColor(Class<T> aClass);

    abstract <T extends Shape> Shape getShape(Class<T> aClass);
}

/**
 * ShapeFactory 工厂  -  生产具体对象
 */
class ShapeFactory extends AbstractFactory {

    @Override
    public <T extends Shape> Shape getShape(Class<T> aClass) {

        Shape shape = null;
        try {
            shape = (Shape) Class.forName(aClass.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return shape;
    }

    @Override
    public <T extends Color> Color getColor(Class<T> aClass) {
        return null;
    }
}

/**
 * ColorFactory 工厂  - 生产具体对象
 */
class ColorFactory extends AbstractFactory {

    @Override
    public <T extends Shape> Shape getShape(Class<T> aClass) {
        return null;
    }

    @Override
    public <T extends Color> Color getColor(Class<T> aClass) {
        Color color = null;
        try {
            color = (Color) Class.forName(aClass.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return color;
    }
}

/**
 * 工厂制造者 - 生产抽象工厂
 */
class FactoryProducer {

    /**
     * @param aClass aClass
     * @param <T>    T extends AbstractFactory
     * @return AbstractFactory
     */
    static <T extends AbstractFactory> AbstractFactory getFactory(Class<T> aClass) {

        AbstractFactory abstractFactory = null;
        try {
            abstractFactory = (AbstractFactory) Class.forName(aClass.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return abstractFactory;
    }
}
