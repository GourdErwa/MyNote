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
 * 游戏主菜单
 */
class PopupMenuPanel extends JPanel {
    final static String START_GAME_BUTTON = "START_GAME_BUTTON";
    final static String EXIT_GAME_BUTTON = "EXIT_GAME_BUTTON";
    final static String TOP_10_SCORES_BUTTON = "TOP_10_SCORES_BUTTON";
    final static String HELP_BUTTON = "HELP_BUTTON";
    private static final long serialVersionUID = 1L;

    PopupMenuPanel(GamePlaneWarMainFrame gamePlaneWarMainFrame) {
        this.initComponents(gamePlaneWarMainFrame);
    }

    private void initComponents(GamePlaneWarMainFrame gamePlaneWarMainFrame) {
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(Images.MY_PLANE_IMG));

        GameButton startGameButton = new GameButton("开始游戏");
        startGameButton.addActionListener(gamePlaneWarMainFrame);
        startGameButton.setActionCommand(START_GAME_BUTTON);
        startGameButton.setOpaque(false);

        GameButton top10ScoresButton = new GameButton("分数记录");
        top10ScoresButton.addActionListener(gamePlaneWarMainFrame);
        top10ScoresButton.setActionCommand(TOP_10_SCORES_BUTTON);
        top10ScoresButton.setOpaque(false);

        /*GameButton helpButton = new GameButton("帮助信息");
        helpButton.addActionListener(gamePlaneWarMainFrame);
        helpButton.setActionCommand(HELP_BUTTON);
        helpButton.setOpaque(false);*/

        GameButton exitGameButton = new GameButton("退出游戏");
        exitGameButton.addActionListener(gamePlaneWarMainFrame);
        exitGameButton.setActionCommand(EXIT_GAME_BUTTON);
        exitGameButton.setOpaque(false);

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.add(logoLabel);

        GridLayout gridLayout = new GridLayout(4, 1, 0, 10);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(gridLayout);

        buttonPanel.add(startGameButton);
        buttonPanel.add(top10ScoresButton);
        //buttonPanel.add(helpButton);
        buttonPanel.add(exitGameButton);

        Dimension d = new Dimension(Config.POP_UP_MENU_PANEL_WIDTH, Config.POP_UP_MENU_PANEL_HEIGHT);
        buttonPanel.setSize(d);
        buttonPanel.setPreferredSize(d);

        BorderLayout mainLayout = new BorderLayout();
        mainLayout.setVgap(25);
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(mainLayout);
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        this.setOpaque(false);
        this.add(mainPanel);
    }

}
