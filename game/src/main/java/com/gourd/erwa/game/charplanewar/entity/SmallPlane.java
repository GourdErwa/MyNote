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

package com.gourd.erwa.game.charplanewar.entity;

import com.gourd.erwa.game.charplanewar.config.Config;
import com.gourd.erwa.game.charplanewar.config.EnemyPlaneType;
import com.gourd.erwa.game.charplanewar.config.ImageConstants;
import com.gourd.erwa.game.charplanewar.ui.GamePlayingPanel;
import com.gourd.erwa.game.charplanewar.util.Images;

import java.awt.*;

/**
 * 小型敌机
 */
public class SmallPlane extends EnemyPlane {

    public SmallPlane(GamePlayingPanel getPlayingPanel, EnemyPlaneType enemyType) {
        super(getPlayingPanel, enemyType);
    }

    @Override
    public void drawFighting(Graphics g) {
        new Thread(new DrawFighting(g)).start();
    }

    private void drawFightingRun(Graphics g) {
        this.setPlaneImage(Images.SMALL_PLANE_FIGHTING_IMG);
        this.setWidth(ImageConstants.SMALL_PLANE_FIGHTING_WIDTH);
        this.setHeight(ImageConstants.SMALL_PLANE_FIGHTING_HEIGHT);
        super.draw(g);
        try {
            Thread.sleep(Config.SMALL_PLANE_STATUS_CHANGE_INTERVAL);
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void drawKilled(Graphics g) {
        new Thread(new DrawKilled(g)).start();
    }

    private void drawKilledRun(Graphics g) {
        this.setPlaneImage(Images.SMALL_PLANE_KILLED_IMG);
        this.setWidth(ImageConstants.SMALL_PLANE_KILLED_WIDTH);
        this.setHeight(ImageConstants.SMALL_PLANE_KILLED_HEIGHT);
        super.draw(g);
        try {
            Thread.sleep(Config.SMALL_PLANE_STATUS_CHANGE_INTERVAL);
        } catch (InterruptedException ignored) {

        }
        this.setPlaneImage(Images.SMALL_PLANE_ASHED_IMG);
        this.setWidth(ImageConstants.SMALL_PLANE_ASHED_WIDTH);
        this.setHeight(ImageConstants.SMALL_PLANE_ASHED_HEIGHT);
        super.draw(g);
        try {
            Thread.sleep(Config.SMALL_PLANE_STATUS_CHANGE_INTERVAL);
        } catch (InterruptedException ignored) {

        }
    }

    class DrawFighting implements Runnable {
        private Graphics g;

        DrawFighting(Graphics g) {
            this.g = g;
        }

        @Override
        public void run() {
            drawFightingRun(g);
        }
    }

    class DrawKilled implements Runnable {
        private Graphics g;

        DrawKilled(Graphics g) {
            this.g = g;
        }

        @Override
        public void run() {
            drawKilledRun(g);
        }

    }

}
