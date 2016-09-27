/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package com.gourd.erwa.util.corejava.basis.enum_;

/**
 * 枚举定义变量，类似 Map 形式
 * <p>
 * 枚举还要可以用来实现 Singleton 模式，参见 Effective Java Item 3.
 *
 * @author wei.Li by 14-9-6.
 */
public enum Used_1_VariableLabel {

    yiwa("葫芦一娃", 1), erwa("葫芦二娃", 2);

    private String name;
    private int id;


    private Used_1_VariableLabel(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static String getName(int id) {

        for (Used_1_VariableLabel used_1VariableLabel : Used_1_VariableLabel.values()) {
            if (used_1VariableLabel.getId() == id) {
                return used_1VariableLabel.getName();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
