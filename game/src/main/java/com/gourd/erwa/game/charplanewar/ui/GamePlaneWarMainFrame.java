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

import com.gourd.erwa.game.charplanewar.GamePlaneWarMain;
import com.gourd.erwa.game.charplanewar.config.Config;
import com.gourd.erwa.game.charplanewar.config.ImageConstants;
import com.gourd.erwa.game.charplanewar.entity.Score;
import com.gourd.erwa.game.charplanewar.util.FileUtil;
import com.gourd.erwa.game.charplanewar.util.ImageLoader;
import com.gourd.erwa.game.charplanewar.util.Images;
import com.gourd.erwa.game.charplanewar.util.SoundPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 游戏主画面
 */
public class GamePlaneWarMainFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private GameLoadingPanel gameLoadingPanel;
    private GamePlayingPanel gamePlayingPanel;

    private PopupMenuPanel popupMenuPanel;
    private Top10ScorePanel popupScorePanel;
    private HelpDialog helpDialog;

    private SoundPlayer achievementSoundPlayer;

    private List<Score> scoreList;

    /**
     * 主面板初始化
     *
     * @throws IOException                   the io exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     */
    public GamePlaneWarMainFrame() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        try {
            this.scoreList = FileUtil.readScore(Config.SCORE_FILE);
        } catch (Exception e) {
            this.scoreList = new ArrayList<>();
        }
        this.loadImage();
        this.initSoundPlayer();
        this.initComponents();
        this.setBackgroundImage();
    }

    /**
     * 载入配置的图片
     */
    private void loadImage() throws IOException {

        ImageLoader imgLoader = new ImageLoader(Config.SHOOT_BACKGROUND_IMG);
        Images.GAME_LOADING_IMG1 = imgLoader.getImage(ImageConstants.GAME_LOADING_PLANE_1_POS_X,
                ImageConstants.GAME_LOADING_PLANE_1_POS_Y, ImageConstants.GAME_LOADING_PLANE_1_WIDTH,
                ImageConstants.GAME_LOADING_PLANE_1_HEIGHT);
        Images.GAME_LOADING_IMG2 = imgLoader.getImage(ImageConstants.GAME_LOADING_PLANE_2_POS_X,
                ImageConstants.GAME_LOADING_PLANE_2_POS_Y, ImageConstants.GAME_LOADING_PLANE_2_WIDTH,
                ImageConstants.GAME_LOADING_PLANE_2_HEIGHT);
        Images.GAME_LOADING_IMG3 = imgLoader.getImage(ImageConstants.GAME_LOADING_PLANE_3_POS_X,
                ImageConstants.GAME_LOADING_PLANE_3_POS_Y, ImageConstants.GAME_LOADING_PLANE_3_WIDTH,
                ImageConstants.GAME_LOADING_PLANE_3_HEIGHT);

        Images.SHOOT_BACKGROUND_IMG = imgLoader.getImage(ImageConstants.GAME_BACKGROUND_IMG_POS_X,
                ImageConstants.GAME_BACKGROUND_IMG_POS_Y, ImageConstants.GAME_BACKGROUND_IMG_WIDTH,
                ImageConstants.GAME_BACKGROUND_IMG_HEIGHT);

        Images.GAME_LOADING_TEXT_IMG = imgLoader.getImage(ImageConstants.GAME_LOADING_TEXT_IMG_POS_X,
                ImageConstants.GAME_LOADING_TEXT_IMG_POS_Y, ImageConstants.GAME_LOADING_TEXT_IMG_WIDTH,
                ImageConstants.GAME_LOADING_TEXT_IMG_HEIGHT);

        imgLoader = new ImageLoader(Config.SHOOT_IMG);
        Images.YELLOW_BULLET_IMG = imgLoader.getImage(ImageConstants.YELLOW_BULLET_POS_X,
                ImageConstants.YELLOW_BULLET_POS_Y, ImageConstants.YELLOW_BULLET_WIDTH,
                ImageConstants.YELLOW_BULLET_HEIGHT);
        Images.BLUE_BULLET_IMG = imgLoader.getImage(ImageConstants.BLUE_BULLET_POS_X,
                ImageConstants.BLUE_BULLET_POS_Y, ImageConstants.BLUE_BULLET_WIDTH, ImageConstants.BLUE_BULLET_HEIGHT);
        Images.MY_PLANE_IMG = imgLoader.getImage(ImageConstants.MY_PLANE_POS_X, ImageConstants.MY_PLANE_POS_Y,
                ImageConstants.MY_PLANE_WIDTH, ImageConstants.MY_PLANE_HEIGHT);
        Images.MY_PLANE_FLYING_IMG = imgLoader.getImage(ImageConstants.MY_PLANE_FLYING_POS_X,
                ImageConstants.MY_PLANE_FLYING_POS_Y, ImageConstants.MY_PLANE_FLYING_WIDTH,
                ImageConstants.MY_PLANE_FLYING_HEIGHT);
        Images.SMALL_PLANE_IMG = imgLoader.getImage(ImageConstants.SMALL_PLANE_POS_X,
                ImageConstants.SMALL_PLANE_POS_Y, ImageConstants.SMALL_PLANE_WIDTH, ImageConstants.SMALL_PLANE_HEIGHT);
        Images.BIG_PLANE_IMG = imgLoader.getImage(ImageConstants.BIG_PLANE_POS_X, ImageConstants.BIG_PLANE_POS_Y,
                ImageConstants.BIG_PLANE_WIDTH, ImageConstants.BIG_PLANE_HEIGHT);
        Images.BOSS_PLANE_IMG = imgLoader.getImage(ImageConstants.BOSS_PLANE_POS_X,
                ImageConstants.BOSS_PLANE_POS_Y, ImageConstants.BOSS_PLANE_WIDTH, ImageConstants.BOSS_PLANE_HEIGHT);
        Images.BOMB_IMG = imgLoader.getImage(ImageConstants.BOMB_POS_X, ImageConstants.BOMB_POS_Y,
                ImageConstants.BOMB_WIDTH, ImageConstants.BOMB_HEIGHT);
        Images.CAUGHT_BOMB_IMG = imgLoader.getImage(ImageConstants.CAUGHT_BOMB_POS_X,
                ImageConstants.CAUGHT_BOMB_POS_Y, ImageConstants.CAUGHT_BOMB_WIDTH, ImageConstants.CAUGHT_BOMB_HEIGHT);
        Images.DOUBLE_LASER_IMG = imgLoader.getImage(ImageConstants.DOUBLE_LASER_POS_X,
                ImageConstants.DOUBLE_LASER_POS_Y, ImageConstants.DOUBLE_LASER_WIDTH,
                ImageConstants.DOUBLE_LASER_HEIGHT);

        Images.SMALL_PLANE_FIGHTING_IMG = imgLoader.getImage(ImageConstants.SMALL_PLANE_FIGHTING_POS_X,
                ImageConstants.SMALL_PLANE_FIGHTING_POS_Y, ImageConstants.SMALL_PLANE_FIGHTING_WIDTH,
                ImageConstants.SMALL_PLANE_FIGHTING_HEIGHT);
        Images.SMALL_PLANE_KILLED_IMG = imgLoader.getImage(ImageConstants.SMALL_PLANE_KILLED_POS_X,
                ImageConstants.SMALL_PLANE_KILLED_POS_Y, ImageConstants.SMALL_PLANE_KILLED_WIDTH,
                ImageConstants.SMALL_PLANE_KILLED_HEIGHT);
        Images.SMALL_PLANE_ASHED_IMG = imgLoader.getImage(ImageConstants.SMALL_PLANE_ASHED_POS_X,
                ImageConstants.SMALL_PLANE_ASHED_POS_Y, ImageConstants.SMALL_PLANE_ASHED_WIDTH,
                ImageConstants.SMALL_PLANE_ASHED_HEIGHT);

        Images.BIG_PLANE_FIGHTING_IMG = imgLoader.getImage(ImageConstants.BIG_PLANE_FIGHTING_POS_X,
                ImageConstants.BIG_PLANE_FIGHTING_POS_Y, ImageConstants.BIG_PLANE_FIGHTING_WIDTH,
                ImageConstants.BIG_PLANE_FIGHTING_HEIGHT);
        Images.BIG_PLANE_HITTED_IMG = imgLoader.getImage(ImageConstants.BIG_PLANE_HITTED_POS_X,
                ImageConstants.BIG_PLANE_HITTED_POS_Y, ImageConstants.BIG_PLANE_HITTED_WIDTH,
                ImageConstants.BIG_PLANE_HITTED_HEIGHT);
        Images.BIG_PLANE_BADDLY_WOUNDED_IMG = imgLoader.getImage(ImageConstants.BIG_PLANE_BADDLY_WOUNDED_POS_X,
                ImageConstants.BIG_PLANE_BADDLY_WOUNDED_POS_Y, ImageConstants.BIG_PLANE_BADDLY_WOUNDED_WIDTH,
                ImageConstants.BIG_PLANE_BADDLY_WOUNDED_HEIGHT);
        Images.BIG_PLANE_KILLED_IMG = imgLoader.getImage(ImageConstants.BIG_PLANE_KILLED_POS_X,
                ImageConstants.BIG_PLANE_KILLED_POS_Y, ImageConstants.BIG_PLANE_KILLED_WIDTH,
                ImageConstants.BIG_PLANE_KILLED_HEIGHT);
        Images.BIG_PLANE_ASHED_IMG = imgLoader.getImage(ImageConstants.BIG_PLANE_ASHED_POS_X,
                ImageConstants.BIG_PLANE_ASHED_POS_Y, ImageConstants.BIG_PLANE_ASHED_WIDTH,
                ImageConstants.BIG_PLANE_ASHED_HEIGHT);

        Images.BOSS_PLANE_FIGHTING_IMG = imgLoader.getImage(ImageConstants.BOSS_PLANE_FIGHTING_POS_X,
                ImageConstants.BOSS_PLANE_FIGHTING_POS_Y, ImageConstants.BOSS_PLANE_FIGHTING_WIDTH,
                ImageConstants.BOSS_PLANE_FIGHTING_HEIGHT);
        Images.BOSS_PLANE_HITTED_IMG = imgLoader.getImage(ImageConstants.BOSS_PLANE_HITTED_POS_X,
                ImageConstants.BOSS_PLANE_HITTED_POS_Y, ImageConstants.BOSS_PLANE_HITTED_WIDTH,
                ImageConstants.BOSS_PLANE_HITTED_HEIGHT);
        Images.BOSS_PLANE_BADDLY_WOUNDED_IMG = imgLoader.getImage(ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_POS_X,
                ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_POS_Y, ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_WIDTH,
                ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_HEIGHT);
        Images.BOSS_PLANE_KILLED_IMG = imgLoader.getImage(ImageConstants.BOSS_PLANE_KILLED_POS_X,
                ImageConstants.BOSS_PLANE_KILLED_POS_Y, ImageConstants.BOSS_PLANE_KILLED_WIDTH,
                ImageConstants.BOSS_PLANE_KILLED_HEIGHT);
        Images.BOSS_PLANE_ASHED_IMG = imgLoader.getImage(ImageConstants.BOSS_PLANE_ASHED_POS_X,
                ImageConstants.BOSS_PLANE_ASHED_POS_Y, ImageConstants.BOSS_PLANE_ASHED_WIDTH,
                ImageConstants.BOSS_PLANE_ASHED_HEIGHT);

        Images.SCORE_IMG = imgLoader.getImage(ImageConstants.SCORE_IMG_POS_X, ImageConstants.SCORE_IMG_POS_Y,
                ImageConstants.SCORE_IMG_WIDTH, ImageConstants.SCORE_IMG_HEIGHT);

        imgLoader = new ImageLoader(Config.FONT_IMG);
        Images.X_MARK_IMG = imgLoader.getImage(ImageConstants.X_MARK_POS_X, ImageConstants.X_MARK_POS_Y,
                ImageConstants.X_MARK_WIDTH, ImageConstants.X_MARK_HEIGHT);

        Images.NUMBER_0_IMG = imgLoader.getImage(ImageConstants.NUMBER_0_POS_X, ImageConstants.NUMBER_0_POS_Y,
                ImageConstants.NUMBER_0_WIDTH, ImageConstants.NUMBER_0_HEIGHT);
        Images.NUMBER_1_IMG = imgLoader.getImage(ImageConstants.NUMBER_1_POS_X, ImageConstants.NUMBER_1_POS_Y,
                ImageConstants.NUMBER_1_WIDTH, ImageConstants.NUMBER_1_HEIGHT);
        Images.NUMBER_2_IMG = imgLoader.getImage(ImageConstants.NUMBER_2_POS_X, ImageConstants.NUMBER_2_POS_Y,
                ImageConstants.NUMBER_2_WIDTH, ImageConstants.NUMBER_2_HEIGHT);
        Images.NUMBER_3_IMG = imgLoader.getImage(ImageConstants.NUMBER_3_POS_X, ImageConstants.NUMBER_3_POS_Y,
                ImageConstants.NUMBER_3_WIDTH, ImageConstants.NUMBER_3_HEIGHT);
        Images.NUMBER_4_IMG = imgLoader.getImage(ImageConstants.NUMBER_4_POS_X, ImageConstants.NUMBER_4_POS_Y,
                ImageConstants.NUMBER_4_WIDTH, ImageConstants.NUMBER_4_HEIGHT);
        Images.NUMBER_5_IMG = imgLoader.getImage(ImageConstants.NUMBER_5_POS_X, ImageConstants.NUMBER_5_POS_Y,
                ImageConstants.NUMBER_5_WIDTH, ImageConstants.NUMBER_5_HEIGHT);
        Images.NUMBER_6_IMG = imgLoader.getImage(ImageConstants.NUMBER_6_POS_X, ImageConstants.NUMBER_6_POS_Y,
                ImageConstants.NUMBER_6_WIDTH, ImageConstants.NUMBER_6_HEIGHT);
        Images.NUMBER_7_IMG = imgLoader.getImage(ImageConstants.NUMBER_7_POS_X, ImageConstants.NUMBER_7_POS_Y,
                ImageConstants.NUMBER_7_WIDTH, ImageConstants.NUMBER_7_HEIGHT);
        Images.NUMBER_8_IMG = imgLoader.getImage(ImageConstants.NUMBER_8_POS_X, ImageConstants.NUMBER_8_POS_Y,
                ImageConstants.NUMBER_8_WIDTH, ImageConstants.NUMBER_8_HEIGHT);
        Images.NUMBER_9_IMG = imgLoader.getImage(ImageConstants.NUMBER_9_POS_X, ImageConstants.NUMBER_9_POS_Y,
                ImageConstants.NUMBER_9_WIDTH, ImageConstants.NUMBER_9_HEIGHT);
    }

    private void initComponents() {
        this.setTitle(GamePlaneWarMain.GAME_TITLE);
        this.setIconImage(new ImageIcon(Config.LOGO_IMG).getImage());
        this.setSize(Config.MAIN_FRAME_WIDTH, Config.MAIN_FRAME_HEIGHT);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((d.width - Config.MAIN_FRAME_WIDTH) / 2, (d.height - Config.MAIN_FRAME_HEIGHT) / 2,
                Config.MAIN_FRAME_WIDTH, Config.MAIN_FRAME_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * 初始化文件播放
     *
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private void initSoundPlayer() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        achievementSoundPlayer = new SoundPlayer(Config.ACHIEVEMENT_AUDIO);
    }

    /**
     * 设置背景图片
     */
    private void setBackgroundImage() {
        ImageIcon bgImgIcon = new ImageIcon(Images.SHOOT_BACKGROUND_IMG);
        JLabel bgLabel = new JLabel(bgImgIcon);
        this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
        bgLabel.setBounds(0, 0, bgImgIcon.getIconWidth(), bgImgIcon.getIconHeight());
        ((JPanel) this.getContentPane()).setOpaque(false);
    }

    private void popupMenuPanel() {
        Container c = this.getContentPane();
        c.removeAll();
        this.repaint();
        if (this.popupMenuPanel == null) {
            this.popupMenuPanel = new PopupMenuPanel(this);
        }
        BoxLayout boxLayout = new BoxLayout(c, BoxLayout.Y_AXIS);
        c.setLayout(boxLayout);
        c.add(Box.createVerticalGlue());
        c.add(this.popupMenuPanel);
        c.add(Box.createVerticalGlue());
        this.validate();
    }

    /**
     * 载入游戏画面
     *
     * @throws LineUnavailableException      the line unavailable exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws IOException                   the io exception
     */
    public void loadGame() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        Container c = this.getContentPane();
        c.removeAll();
        this.repaint();
        if (this.gameLoadingPanel == null) {
            this.gameLoadingPanel = new GameLoadingPanel();
        }

        BoxLayout boxLayout = new BoxLayout(c, BoxLayout.Y_AXIS);
        c.setLayout(boxLayout);
        c.add(Box.createVerticalGlue());
        c.add(this.gameLoadingPanel);
        c.add(Box.createVerticalGlue());
        this.gameLoadingPanel.loadingGame();

        this.startGame();
    }

    /**
     * 开始游戏
     *
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private void startGame() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        Container c = this.getContentPane();
        c.removeAll();
        this.repaint();
        BorderLayout borderLayout = new BorderLayout();
        c.setLayout(borderLayout);
        this.gamePlayingPanel = new GamePlayingPanel();
        c.add(this.gamePlayingPanel, BorderLayout.CENTER);
        this.gamePlayingPanel.startGame();
        long startTime = System.currentTimeMillis();
        while (this.gamePlayingPanel.getMyPlane().isAlive()) {
            try {
                Thread.sleep(Config.GAME_PANEL_REPAINT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        // add to score list
        this.addScore(this.gamePlayingPanel.getScore(), endTime - startTime);
        int option = JOptionPane.showConfirmDialog(this, "游戏结束，分数:" + this.gamePlayingPanel.getScore()
                + ",  继续 ?", "游戏结束", JOptionPane.YES_NO_OPTION);
        switch (option) {
            case JOptionPane.YES_OPTION:
                loadGame();
                break;
            case JOptionPane.NO_OPTION:
                stopGame();
                break;
        }
    }

    /**
     * 添加分数
     *
     * @param score            score
     * @param lastMilliSeconds lastMilliSeconds
     * @throws IOException
     */
    private void addScore(int score, long lastMilliSeconds) throws IOException {
        Score s = new Score(new Date(System.currentTimeMillis()), score, lastMilliSeconds);
        int size = this.scoreList.size();
        if (this.scoreList.contains(s)) {
            return;
        }
        if (size < Config.MAX_SCORE_COUNT) {
            this.scoreList.add(s);
        } else {
            Score lastScore = this.scoreList.get(size - 1);
            if (s.compareTo(lastScore) > 0) {
                this.scoreList.remove(lastScore);
                this.scoreList.add(s);
            }
        }
        Collections.sort(this.scoreList);
        Collections.reverse(this.scoreList);
        FileUtil.writeScore(scoreList, Config.SCORE_FILE);
    }

    /**
     * 结束游戏
     */
    public void stopGame() {
        popupMenuPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCmd = e.getActionCommand();
        if (actionCmd.equals(PopupMenuPanel.START_GAME_BUTTON)) {
            startGameAction();
        } else if (actionCmd.equals(PopupMenuPanel.TOP_10_SCORES_BUTTON)) {
            this.achievementSoundPlayer.play();
            popupScorePanel(this.scoreList);
        } else if (actionCmd.equals(PopupMenuPanel.EXIT_GAME_BUTTON)) {
            exitGameAction();
        } else if (actionCmd.equals(PopupMenuPanel.HELP_BUTTON)) {
            helpAction();
        } else if (actionCmd.equals(Top10ScorePanel.OK_BUTTON)) {
            this.popupMenuPanel();
        }
    }

    /**
     * 弹出成绩面板
     *
     * @param sortedScoreList 所有成绩
     */
    private void popupScorePanel(List<Score> sortedScoreList) {
        Container c = this.getContentPane();
        c.removeAll();
        this.repaint();
        if (this.popupScorePanel == null) {
            this.popupScorePanel = new Top10ScorePanel(this);
        }
        this.popupScorePanel.loadScore(sortedScoreList);
        BoxLayout boxLayout = new BoxLayout(c, BoxLayout.Y_AXIS);
        c.setLayout(boxLayout);
        c.add(Box.createVerticalGlue());
        c.add(this.popupScorePanel);
        c.add(Box.createVerticalGlue());
        this.validate();
    }

    /**
     * 开始游戏线程启动
     */
    private void startGameAction() {
        new Thread(new StartGameActionClass()).start();
    }

    /**
     * 退出游戏事件
     */
    private void exitGameAction() {
        System.exit(0);
    }

    /**
     * 帮助按钮事件
     */
    private void helpAction() {
        if (this.helpDialog == null) {
            this.helpDialog = new HelpDialog();
        }
        this.helpDialog.setVisible(true);
    }

    /**
     * 开始游戏 线程
     */
    class StartGameActionClass implements Runnable {

        @Override
        public void run() {
            try {
                loadGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
