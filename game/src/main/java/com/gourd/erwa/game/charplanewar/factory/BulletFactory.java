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

import com.gourd.erwa.game.charplanewar.config.BulletType;
import com.gourd.erwa.game.charplanewar.config.ImageConstants;
import com.gourd.erwa.game.charplanewar.entity.Bullet;
import com.gourd.erwa.game.charplanewar.entity.MyPlane;

/**
 * 子弹工厂
 */
public class BulletFactory {

    public static Bullet createYellowBullet(MyPlane myPlane) {
        int planePosX = myPlane.getPosX();
        int planePosY = myPlane.getPosY();

        int bulletPosX = planePosX + myPlane.getWidth() / 2 - ImageConstants.YELLOW_BULLET_WIDTH / 2;
        int bulletPosY = planePosY + ImageConstants.YELLOW_BULLET_HEIGHT;

        Bullet b = new Bullet(myPlane.getPlayingPanel(), BulletType.YELLOW_BULLET);
        b.setPosX(bulletPosX);
        b.setPosY(bulletPosY);
        return b;
    }

    public static Bullet[] createBlueBullet(MyPlane myPlane) {
        Bullet[] bullets = new Bullet[2];
        int planePosX = myPlane.getPosX();
        int planePosY = myPlane.getPosY();

        int bullet1PosX = planePosX + myPlane.getWidth() / 4 - ImageConstants.BLUE_BULLET_WIDTH / 2;
        int bullet1PosY = planePosY + ImageConstants.BLUE_BULLET_HEIGHT;

        Bullet b1 = new Bullet(myPlane.getPlayingPanel(), BulletType.BLUE_BULLET);
        b1.setPosX(bullet1PosX);
        b1.setPosY(bullet1PosY);

        int bullet2PosX = planePosX + myPlane.getWidth() / 4 * 3 - ImageConstants.BLUE_BULLET_WIDTH / 2;
        int bullet2PosY = planePosY + ImageConstants.BLUE_BULLET_HEIGHT;

        Bullet b2 = new Bullet(myPlane.getPlayingPanel(), BulletType.BLUE_BULLET);
        b2.setPosX(bullet2PosX);
        b2.setPosY(bullet2PosY);

        bullets[0] = b1;
        bullets[1] = b2;

        return bullets;
    }
}
