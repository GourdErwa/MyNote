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

package com.gourd.erwa.game.charplanewar.factory;

import com.gourd.erwa.game.charplanewar.config.CatchableWeaponType;
import com.gourd.erwa.game.charplanewar.config.Config;
import com.gourd.erwa.game.charplanewar.config.ImageConstants;
import com.gourd.erwa.game.charplanewar.entity.Bomb;
import com.gourd.erwa.game.charplanewar.entity.CatchableWeapon;
import com.gourd.erwa.game.charplanewar.entity.DoubleLaser;
import com.gourd.erwa.game.charplanewar.ui.GamePlayingPanel;
import com.gourd.erwa.game.charplanewar.util.Images;

import java.util.Random;

/**
 * 炸弹效果工厂
 */
public class CatchableWeaponFactory {

    public static final Random rand = new Random();

    public static CatchableWeapon createCatchableWeapon(GamePlayingPanel playingPanel, CatchableWeaponType weaponType) {
        CatchableWeapon weapon = null;
        switch (weaponType) {
            case BOMB:
                weapon = new Bomb(playingPanel, weaponType);
                weapon.setWidth(ImageConstants.BOMB_WIDTH);
                weapon.setHeight(ImageConstants.BOMB_HEIGHT);
                weapon.setWeaponImage(Images.BOMB_IMG);
                weapon.setSpeed(Config.POP_WEAPON_MOVE_SPEED);
                break;
            case DOUBLE_LASER:
                weapon = new DoubleLaser(playingPanel, weaponType);
                weapon.setWidth(ImageConstants.DOUBLE_LASER_WIDTH);
                weapon.setHeight(ImageConstants.DOUBLE_LASER_HEIGHT);
                weapon.setWeaponImage(Images.DOUBLE_LASER_IMG);
                weapon.setSpeed(Config.POP_WEAPON_MOVE_SPEED);
                break;
        }

        int posX = rand.nextInt(playingPanel.getWidth() - weapon.getWidth());
        int posY = 0;
        weapon.setPosX(posX);
        weapon.setPosY(posY);

        return weapon;
    }
}
