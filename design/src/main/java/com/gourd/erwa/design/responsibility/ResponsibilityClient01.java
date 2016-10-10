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

package com.gourd.erwa.design.responsibility;

/*
责任链模式
 */

/**
 * 由传入参数判断处理对象，不满足当前处理条件则交给下一个处理者
 *
 * @author wei.Li by 15/3/30 (gourderwa@163.com).
 */
class ResponsibilityClient01 {

    public static void main(String[] args) {

        ShopManager shopManager = new ShopManager();
        AreaManager areaManager = new AreaManager();
        GeneralManager generalManager = new GeneralManager();
        ChairmanManager chairmanManager = new ChairmanManager();

        shopManager.setSuccessor(areaManager);
        areaManager.setSuccessor(generalManager);
        generalManager.setSuccessor(chairmanManager);

        double money = 1D;
        for (int i = 1; i < 10; i++, money *= 100D) {
            shopManager.handleRequest(money);
        }

    }
}

/**
 * 抽象处理类
 */
abstract class Handler {

    /**
     * 持有后继的责任对象
     */
    Handler successor;

    /**
     * 示意处理请求的方法，虽然这个示意方法是没有传入参数的
     * 但实际是可以传入参数的，根据具体需要来选择是否传递参数
     */
    public abstract boolean handleRequest(double money);

    /**
     * 赋值方法，设置后继的责任对象
     */
    void setSuccessor(Handler successor) {
        this.successor = successor;
    }

}

/**
 * 店长
 */
class ShopManager extends Handler {

    @Override
    public boolean handleRequest(double money) {

        return money > 10_000D ? this.successor.handleRequest(money) : handleReponse(money);
    }

    private boolean handleReponse(double money) {
        //do something

        System.out.println("ShopManager handle ... " + money);
        return true;
    }
}

/**
 * 区域经理
 */
class AreaManager extends Handler {

    @Override
    public boolean handleRequest(double money) {

        return money > 10_000_000D ? this.successor.handleRequest(money) : handleReponse(money);
    }


    private boolean handleReponse(double money) {
        //do something

        System.out.println("AreaManager handle ... " + money);
        return true;
    }
}

/**
 * 总经理
 */
class GeneralManager extends Handler {

    @Override
    public boolean handleRequest(double money) {

        return money > 10_000_000_000D ? this.successor.handleRequest(money) : handleReponse(money);
    }

    private boolean handleReponse(double money) {
        //do something

        System.out.println("GeneralManager handle ... " + money);
        return true;
    }
}


/**
 * 董事长
 */
class ChairmanManager extends Handler {

    @Override
    public boolean handleRequest(double money) {

        return handleReponse(money);
    }

    private boolean handleReponse(double money) {
        //do something

        System.out.println("ChairmanManager handle ... " + money);
        return true;
    }
}
