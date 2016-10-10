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

import java.util.ArrayList;
import java.util.List;

/**
 * 物品接口
 */
interface Item {

    String name();

    Packing packing();

    float price();
}

/**
 * 包装接口
 */
interface Packing {

    String pack();
}

/**
 * 建造者模式
 *
 * @author wei.Li by 15/3/31 (gourderwa@163.com).
 */
class BuilderClient02 {

    public static void main(String[] args) {

        MealBuilder mealBuilder = new MealBuilder();
        Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();
        System.out.println("Total Cost: " + vegMeal.getCost());


        Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        System.out.println("\n\nNon-Veg Meal");
        nonVegMeal.showItems();
        System.out.println("Total Cost: " + nonVegMeal.getCost());
    }
}

/**
 * 纸质包装
 */
class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
}

/**
 * 瓶子包装
 */
class Bottle implements Packing {
    @Override
    public String pack() {
        return "Bottle";
    }
}

/**
 * 汉堡包 类别项目
 */
abstract class Burger implements Item {
    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}

/**
 * 冷饮 类别项目
 */
abstract class ColdDrink implements Item {
    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}

/**
 * 具体的蔬菜 汉堡包 类别项目
 */
class VegBurger extends Burger {
    @Override
    public float price() {
        return 25.0f;
    }

    @Override
    public String name() {
        return "Veg Burger";
    }
}

/**
 * 具体的鸡肉 汉堡包 类别项目
 */
class ChickenBurger extends Burger {
    @Override
    public float price() {
        return 50.5f;
    }

    @Override
    public String name() {
        return "Chicken Burger";
    }
}

/**
 * 具体的可口可乐 冷饮 类别项目
 */
class Coke extends ColdDrink {
    @Override
    public float price() {
        return 30.0f;
    }

    @Override
    public String name() {
        return "Coke";
    }
}

/**
 * 具体的百事可乐 冷饮 类别项目
 */
class Pepsi extends ColdDrink {
    @Override
    public float price() {
        return 35.0f;
    }

    @Override
    public String name() {
        return "Pepsi";
    }
}


/**
 * 进餐实现
 */
class Meal {

    private List<Item> items = new ArrayList<>();

    void addItem(Item item) {
        items.add(item);
    }

    float getCost() {
        float cost = 0.0f;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    void showItems() {
        for (Item item : items) {
            System.out.print("Item : " + item.name());
            System.out.print(", Packing : " + item.packing().pack());
            System.out.println(", Price : " + item.price());
        }
    }
}

/**
 * 进餐具体内容构建者
 */
class MealBuilder {

    Meal prepareVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    Meal prepareNonVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
