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

package com.gourd.erwa.game.charplanewar.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 加载图片
 */
public class ImageLoader {

    private BufferedImage sourceImg;

    public ImageLoader(String imagePath) throws IOException {
        sourceImg = ImageIO.read(new File(imagePath));
    }

    /**
     * 初始化图片
     *
     * @param posX   x
     * @param posY   y
     * @param width  宽度
     * @param height 高度
     * @return 图片对象
     */
    public Image getImage(int posX, int posY, int width, int height) {
        BufferedImage targetImg = this.sourceImg.getSubimage(posX, posY, width, height);
        return new ImageIcon(targetImg).getImage();
    }

}
