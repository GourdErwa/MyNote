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
import com.gourd.erwa.game.charplanewar.entity.Score;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class Top10ScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    static String OK_BUTTON = "TOP_SCORE_OK_BUTTON";
    private GameButton[] scoreButtons;

    Top10ScorePanel(GamePlaneWarMainFrame gamePlaneWarMainFrame) {
        this.initComponents(gamePlaneWarMainFrame);
    }

    private void initComponents(GamePlaneWarMainFrame gamePlaneWarMainFrame) {
        JLabel top10ScoreLabel = new JLabel("<html><font size='5'>Top 10 Scores</font></html>");
        JPanel labelPanel = new JPanel();
        labelPanel.setOpaque(false);
        labelPanel.add(top10ScoreLabel);

        JPanel scorePanel = new JPanel();
        GridLayout gridLayout = new GridLayout(12, 1, 0, 5);
        scorePanel.setLayout(gridLayout);
        scorePanel.setOpaque(false);

        scorePanel.add(labelPanel);

        int SCORE_COUNT = 10;
        this.scoreButtons = new GameButton[SCORE_COUNT];
        for (int i = 0; i < SCORE_COUNT; i++) {
            this.scoreButtons[i] = new GameButton();
            scorePanel.add(this.scoreButtons[i]);
        }

        GameButton okButton = new GameButton("OK");
        okButton.setActionCommand(OK_BUTTON);
        okButton.addActionListener(gamePlaneWarMainFrame);
        scorePanel.add(okButton);

        Dimension d = new Dimension(Config.POP_UP_SCORE_PANEL_WIDTH, Config.POP_UP_SCORE_PANEL_HEIGHT);
        scorePanel.setSize(d);
        scorePanel.setPreferredSize(d);

        this.add(scorePanel);
        this.setOpaque(false);
    }

    public void loadScore(List<Score> sortedScoreList) {
        int scoreSize = sortedScoreList.size();
        for (int i = 0; i < scoreSize; i++) {
            Score score = sortedScoreList.get(i);
            this.scoreButtons[i].setText(score.getScore() + "");
        }
    }
}
