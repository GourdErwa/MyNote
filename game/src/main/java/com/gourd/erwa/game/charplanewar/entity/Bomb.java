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

import com.gourd.erwa.game.charplanewar.config.CatchableWeaponType;
import com.gourd.erwa.game.charplanewar.ui.GamePlayingPanel;

/**
 *
 */
public class Bomb extends CatchableWeapon {

    public Bomb(GamePlayingPanel gamePlayingPanel, CatchableWeaponType weaponType) {
        super(gamePlayingPanel, weaponType);
    }

}
