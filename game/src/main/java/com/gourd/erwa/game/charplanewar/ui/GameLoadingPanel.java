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

package com.gourd.erwa.game.charplanewar.ui;

import com.gourd.erwa.game.charplanewar.config.Config;
import com.gourd.erwa.game.charplanewar.util.Images;

import javax.swing.*;
import java.awt.*;


/**
 * 游戏加载画面
 */
public class GameLoadingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel gameLoadingPlaneLabel;
    private ImageIcon[] gameLoadingPlaneImgList;

    public GameLoadingPanel() {
        this.createLoadingPanel();
    }

    private void createLoadingPanel() {

        this.gameLoadingPlaneImgList = new ImageIcon[3];
        this.gameLoadingPlaneImgList[0] = new ImageIcon(Images.GAME_LOADING_IMG1);
        this.gameLoadingPlaneImgList[1] = new ImageIcon(Images.GAME_LOADING_IMG2);
        this.gameLoadingPlaneImgList[2] = new ImageIcon(Images.GAME_LOADING_IMG3);
        Image gameLoadingTextImg = Images.GAME_LOADING_TEXT_IMG;

        gameLoadingPlaneLabel = new JLabel();
        gameLoadingPlaneLabel.setOpaque(false);
        JLabel gameLoadingTextLabel = new JLabel(new ImageIcon(gameLoadingTextImg));
        gameLoadingTextLabel.setOpaque(false);
        GridLayout gridLayout = new GridLayout(2, 1);

        FlowLayout flowLayout1 = new FlowLayout(FlowLayout.CENTER);
        JPanel panel1 = new JPanel();
        panel1.setLayout(flowLayout1);
        panel1.add(gameLoadingPlaneLabel);
        panel1.setOpaque(false);

        FlowLayout flowLayout2 = new FlowLayout(FlowLayout.CENTER);
        JPanel panel2 = new JPanel();
        panel2.setLayout(flowLayout2);
        panel2.add(gameLoadingTextLabel);
        panel2.setOpaque(false);

        this.setLayout(gridLayout);
        this.setOpaque(false);
        this.add(panel1);
        this.add(panel2);
    }

    public void loadingGame() {
        int times = 3;
        for (int i = 0; i < times; i++) {
            this.gameLoadingPlaneLabel.setIcon(this.gameLoadingPlaneImgList[i]);
            try {
                Thread.sleep(Config.GAME_LOADING_INTERVAL);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
