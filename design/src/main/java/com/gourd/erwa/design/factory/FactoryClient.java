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

package com.gourd.erwa.design.factory;

/**
 * 产品接口
 */
interface IProduct {

    void method01();

    void method02();
}

/**
 * 工厂模式
 *
 * @author wei.Li by 15/3/31 (gourderwa@163.com).
 */
class FactoryClient {

    public static void main(String[] args) {
        final IProduct product = ProductFactory.createProduct(ConcreteProductA.class);
        product.method01();
        product.method02();
    }
}

/**
 * 具体产品 A
 */
class ConcreteProductA implements IProduct {

    @Override
    public void method01() {
        System.out.println("ConcreteProductA method01 run ......");
    }

    @Override
    public void method02() {
        System.out.println("ConcreteProductA method02 run ......");
    }
}

/**
 * 具体产品 B
 */
class ConcreteProductB implements IProduct {

    @Override
    public void method01() {
        System.out.println("ConcreteProductB method01 run ......");
    }

    @Override
    public void method02() {
        System.out.println("ConcreteProductB method02 run ......");
    }
}

/*
abstract class Factory{
    public  <T extends IProduct> IProduct createProduct(Class<T> aClass);
}
*/

/**
 * 制造工厂
 */
class ProductFactory {

    static <T extends IProduct> IProduct createProduct(Class<T> aClass) {
        IProduct iProduct = null;
        try {
            iProduct = (IProduct) Class.forName(aClass.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return iProduct;
    }
}

