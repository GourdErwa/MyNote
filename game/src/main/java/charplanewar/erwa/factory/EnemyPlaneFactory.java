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

package charplanewar.erwa.factory;

import charplanewar.erwa.config.Config;
import charplanewar.erwa.config.EnemyPlaneType;
import charplanewar.erwa.config.ImageConstants;
import charplanewar.erwa.entity.BigPlane;
import charplanewar.erwa.entity.BossPlane;
import charplanewar.erwa.entity.EnemyPlane;
import charplanewar.erwa.entity.SmallPlane;
import charplanewar.erwa.ui.GamePlayingPanel;
import charplanewar.erwa.util.Images;

import java.util.Random;

/**
 * 敌机工厂
 */
public class EnemyPlaneFactory {

    public static final Random rand = new Random();

    public static EnemyPlane createEnemyPlane(GamePlayingPanel playingPanel, EnemyPlaneType enemyPlaneType) {
        EnemyPlane enemyPlane = null;
        switch (enemyPlaneType) {
            case SMALL_ENEMY_PLANE:
                enemyPlane = new SmallPlane(playingPanel, enemyPlaneType);
                enemyPlane.setWidth(ImageConstants.SMALL_PLANE_WIDTH);
                enemyPlane.setHeight(ImageConstants.SMALL_PLANE_HEIGHT);
                enemyPlane.setPlaneImage(Images.SMALL_PLANE_IMG);
                enemyPlane.setKilledCount(Config.BULLET_COUNT_TO_KILL_SMALL_PLANE);
                enemyPlane.setKilledScore(Config.KILL_SMALL_PLANE_SCORE);
                break;
            case BIG_ENEMY_PLANE:
                enemyPlane = new BigPlane(playingPanel, enemyPlaneType);
                enemyPlane.setWidth(ImageConstants.BIG_PLANE_WIDTH);
                enemyPlane.setHeight(ImageConstants.BIG_PLANE_HEIGHT);
                enemyPlane.setPlaneImage(Images.BIG_PLANE_IMG);
                enemyPlane.setKilledCount(Config.BULLET_COUNT_TO_KILL_BIG_PLANE);
                enemyPlane.setKilledScore(Config.KILL_BIG_PLANE_SCORE);
                break;
            case BOSS_ENEMY_PLANE:
                enemyPlane = new BossPlane(playingPanel, enemyPlaneType);
                enemyPlane.setWidth(ImageConstants.BOSS_PLANE_WIDTH);
                enemyPlane.setHeight(ImageConstants.BOSS_PLANE_HEIGHT);
                enemyPlane.setPlaneImage(Images.BOSS_PLANE_IMG);
                enemyPlane.setKilledCount(Config.BULLET_COUNT_TO_KILL_BOSS_PLANE);
                enemyPlane.setKilledScore(Config.KILL_BOSS_PLANE_SCORE);
                break;
        }

        int posX = rand.nextInt(playingPanel.getWidth() - enemyPlane.getWidth());
        int posY = 0;
        enemyPlane.setPosX(posX);
        enemyPlane.setPosY(posY);
        int speed = rand.nextInt(Config.ENEMY_PLANE_MOVE_SPEED_MAX - Config.ENEMY_PLANE_MOVE_SPEED_MIN)
                + Config.ENEMY_PLANE_MOVE_SPEED_MIN;
        enemyPlane.setSpeed(speed);
        enemyPlane.addEnemyPlaneListener(playingPanel);
        return enemyPlane;
    }
}