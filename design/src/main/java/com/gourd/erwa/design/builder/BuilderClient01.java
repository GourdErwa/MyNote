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

package com.gourd.erwa.design.builder;


//定义产品

/**
 * 抽象产品类，使用了模板方法模式，不同产品有不同的“组成部分part”
 *
 * @author wei.Li by 15/3/27 (gourderwa@163.com).
 */
abstract class AbstractProduct {

    protected abstract void part01();

    protected abstract void part02();

    protected abstract void part03();

    //模板方法给出了默认的组装方式，生成默认的产品
    final AbstractProduct defaultProduct() {
        part01();
        part02();
        part03();
        //返回当前对象，即默认组装方式的产品
        return this;
    }
}

/**
 * 具体的产品A，不同产品实现了不同的“组成部分part”
 */
class ConcreteProductA extends AbstractProduct {

    protected void part01() {
        System.out.println("产品A ：part01() ...");
    }

    protected void part02() {
        System.out.println("产品A ：part02() ...");
    }

    protected void part03() {
        System.out.println("产品A ：part03() ...");
    }
}

/**
 * 具体的产品B，不同产品实现了不同的“组成部分part”
 */
class ConcreteProductB extends AbstractProduct {

    protected void part01() {
        System.out.println("产品B ：part01() ...");
    }

    protected void part02() {
        System.out.println("产品B ：part02() ...");
    }

    protected void part03() {
        System.out.println("产品B ：part03() ...");
    }
}


// 建造者


/**
 * 抽象建造者，制定每一种产品应该实现的组合方式buildPart()和生产buildProduct()的标准
 */
abstract class AbstractBuilder {

    public abstract void buildPart();

    public abstract AbstractProduct buildProduct();

}


/**
 * 具体建造者 A，
 * 如果对于默认产品（即当调用抽象产品中的defaultProduct()方法）不满意时，
 * 可以不调用它来获得产品，而是使用具体的建造者来改变产品的生产组装方式，以得到不同的产品
 */
class ConcreteBuilderA extends AbstractBuilder {

    private AbstractProduct productA = new ConcreteProductA();

    public void buildPart() {
        this.productA.part03();
        this.productA.part02();
        this.productA.part01();
    }

    public AbstractProduct buildProduct() {
        return this.productA;
    }
}

/**
 * 具体建造者 B
 */
class ConcreteBuilderB extends AbstractBuilder {

    private AbstractProduct productB = new ConcreteProductB();

    public void buildPart() {
        this.productB.part02();
        this.productB.part01();
        //特地省略掉产品B中的一个组成部分，例如该部分的功能顾客不需要
//    this.productB.part03();
    }

    public AbstractProduct buildProduct() {
        return this.productB;
    }
}


//指挥者


/**
 * 导演类，预先持有各个产品的建造者，为需要不同于默认产品的用户提供不同的组装方式
 */
class Director {

    private AbstractBuilder builderA = new ConcreteBuilderA();
    private AbstractBuilder builderB = new ConcreteBuilderB();

    /**
     * @return ConcreteBuilderA 组装的产品
     */
    AbstractProduct getProductA() {
        this.builderA.buildPart();
        return this.builderA.buildProduct();
    }

    /**
     * @return ConcreteBuilderB 组装的产品
     */
    AbstractProduct getProductB() {
        this.builderB.buildPart();
        return this.builderB.buildProduct();
    }
}

//测试类
class BuilderClient01 {

    public static void main(String[] args) {

        System.out.println("利用模板方法模式获得默认的产品A");
        //AbstractProduct defaultProductA = new ConcreteProductA().defaultProduct();

        System.out.println("\n利用Director类获得不同组装方式的产品A");
        Director director = new Director();
        director.getProductA();

        System.out.println("\n利用Director类获得不同组装方式的产品B");
        director.getProductB();
    }
}
