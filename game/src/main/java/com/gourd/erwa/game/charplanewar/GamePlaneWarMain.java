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

package com.gourd.erwa.game.charplanewar;

import com.gourd.erwa.game.charplanewar.ui.GamePlaneWarMainFrame;

/**
 * 游戏启动 - Main 函数
 */
public class GamePlaneWarMain {


    /**
     * 游戏标题
     */
    public static final String GAME_TITLE = "飞机大战";
    /**
     * 图片、音频文件路径、sound包所在绝对路径
     */
    public static String CONF_PATH = "/lw/workfile/intellij_work/MyNote/game/src/main/java/com/gourd/erwa/game/charplanewar/sound";

    /*static {
        CONF_PATH = GamePlaneWarMain.class.getResource("/").getPath()+ "charplanewar/erwa/sound/";
        System.out.println("资源文件路径: " + CONF_PATH);
    }*/

    public static void main(String args[]) throws InterruptedException {
        try {

            new GamePlaneWarMainFrame().loadGame();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("启动错误、error is" + e + " 。 自动退出。");
            System.exit(1);
        }

    }
}
