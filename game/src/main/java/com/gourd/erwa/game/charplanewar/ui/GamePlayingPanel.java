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


import com.gourd.erwa.game.charplanewar.config.CatchableWeaponType;
import com.gourd.erwa.game.charplanewar.config.Config;
import com.gourd.erwa.game.charplanewar.config.EnemyPlaneType;
import com.gourd.erwa.game.charplanewar.config.ImageConstants;
import com.gourd.erwa.game.charplanewar.entity.*;
import com.gourd.erwa.game.charplanewar.factory.CatchableWeaponFactory;
import com.gourd.erwa.game.charplanewar.factory.EnemyPlaneFactory;
import com.gourd.erwa.game.charplanewar.listener.BulletListener;
import com.gourd.erwa.game.charplanewar.listener.CatchableWeaponListener;
import com.gourd.erwa.game.charplanewar.listener.EnemyPlaneListener;
import com.gourd.erwa.game.charplanewar.util.Images;
import com.gourd.erwa.game.charplanewar.util.SoundPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 游戏进行时处理画面
 */
public class GamePlayingPanel extends JPanel implements MouseListener, MouseMotionListener, BulletListener,
        EnemyPlaneListener, CatchableWeaponListener, ImageObserver {
    private static final long serialVersionUID = 1L;
    private static int ANIMATION_STEP_1 = 1;
    private static int ANIMATION_STEP_2 = 2;
    private final Vector<Bullet> bullets = new Vector<>();
    private volatile List<EnemyPlane> enemyPlanes;
    private int score;
    private MyPlane myPlane;

    private CatchableWeapon popBomb;
    private CatchableWeapon popDoubleLaser;

    private Thread paintThread;

    private int remainTimeToPopSmallPlane;
    private int remainTimeToPopBigPlane;
    private int remainTimeToPopBossPlane;

    private int remainTimeToPopBomb;
    private int remainTimeToPopDoubleLaser;

    private int remainTimeDoubleLaserRunOut;

    private int bombAnomationStep;
    private int doubleLaserAnimationStep;

    private SoundPlayer smallPlaneKilledSoundPlayer;
    private SoundPlayer bigPlaneKilledSoundPlayer;
    private SoundPlayer bossPlaneKilledSoundPlayer;

    private SoundPlayer bossPlaneFlyingSoundPlayer;
    private SoundPlayer popWeaponSoundPlayer;

    private SoundPlayer fireBulletSoundPlayer;
    private SoundPlayer useBombSoundPlayer;

    private SoundPlayer getDoubleLaserSoundPlayer;
    private SoundPlayer getBombSoundPlayer;

    private SoundPlayer gameMusicSoundPlayer;
    private SoundPlayer gameOverSoundPlayer;

    public GamePlayingPanel()
            throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        this.initSoundPlayer();
        this.initComponents();
    }

    private void initComponents() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setSize(Config.MAIN_FRAME_WIDTH, Config.MAIN_FRAME_HEIGHT);
        this.setDoubleBuffered(true);
        this.setOpaque(false);
    }

    private void initSoundPlayer() throws
            LineUnavailableException, UnsupportedAudioFileException, IOException {

        smallPlaneKilledSoundPlayer = new SoundPlayer(Config.SMALL_PLANE_KILLED_AUDIO);
        bigPlaneKilledSoundPlayer = new SoundPlayer(Config.BIG_PLANE_KILLED_AUDIO);
        bossPlaneKilledSoundPlayer = new SoundPlayer(Config.BOSS_PLANE_KILLED_AUDIO);

        bossPlaneFlyingSoundPlayer = new SoundPlayer(Config.BOSS_PLANE_FLYING_AUDIO);
        popWeaponSoundPlayer = new SoundPlayer(Config.POP_WEAPON_AUDIO);

        fireBulletSoundPlayer = new SoundPlayer(Config.FIRE_BULLET_AUDIO);
        useBombSoundPlayer = new SoundPlayer(Config.USER_BOMB_AUDIO);

        getDoubleLaserSoundPlayer = new SoundPlayer(Config.GET_DOUBLE_LASER_AUDIO);
        getBombSoundPlayer = new SoundPlayer(Config.GET_BOMB_AUDIO);

        gameMusicSoundPlayer = new SoundPlayer(Config.GAME_MUSIC_AUDIO);
        gameOverSoundPlayer = new SoundPlayer(Config.GAME_OVER_AUDIO);
    }

    @Override
    public boolean onBulletLocationChanged(Bullet b) {

        boolean isDel = false;

        if (b != null) {

            final int posY = b.getPosY();

            b.setPosY(posY - b.getSpeed());
            if (posY <= 0) {
                isDel = true;
            }

            EnemyPlane enemyPlane = b.hitEnemyPlanes();
            if (enemyPlane != null) {
                enemyPlane.drawFighting(this.getComponentGraphics(this.getGraphics()));
                if (enemyPlane.isKilled()) {
                    switch (enemyPlane.getEnemyType()) {
                        case SMALL_ENEMY_PLANE:
                            this.smallPlaneKilledSoundPlayer.play();
                            break;
                        case BIG_ENEMY_PLANE:
                            this.bigPlaneKilledSoundPlayer.play();
                            break;
                        case BOSS_ENEMY_PLANE:
                            this.bossPlaneFlyingSoundPlayer.stop();
                            this.bossPlaneKilledSoundPlayer.play();
                            break;
                    }
                    synchronized (this) {
                        this.score += enemyPlane.getKilledScore();
                    }
                    synchronized (this.enemyPlanes) {
                        this.enemyPlanes.remove(enemyPlane);
                    }

                    isDel = true;

                    enemyPlane.drawKilled(this.getComponentGraphics(this.getGraphics()));
                }
            }
        }

        return isDel;
    }

    @Override
    public boolean onEnemyPlaneLocationChanged(EnemyPlane p) {
        boolean isDel = false;

        if (p != null && !p.isKilled()) {
            p.setPosY(p.getPosY() + p.getSpeed());
            if (p.getPosY() >= this.getHeight()) {
                if (p.getEnemyType().equals(EnemyPlaneType.BOSS_ENEMY_PLANE)) {
                    this.bossPlaneFlyingSoundPlayer.stop();
                }
                isDel = true;
            } else {
                if (p.getRectangle().intersects(myPlane.getRectange())) {
                    // game ends
                    synchronized (myPlane) {
                        if (myPlane.isAlive()) {
                            this.stopGame();
                        }
                    }
                }
            }
        }
        return isDel;
    }

    @Override
    public void onCatchableWeaponLocationChanged(CatchableWeapon weapon) {
        if (weapon != null) {
            synchronized (weapon) {
                int posY = weapon.getPosY();
                if (weapon.isUseAnimation()) {
                    switch (weapon.getWeaponType()) {
                        case BOMB:
                            if (this.bombAnomationStep == ANIMATION_STEP_1) {
                                posY += Config.POP_WEAPON_ANIMATION_MOVE_FORWARD_SPEED;
                                this.bombAnomationStep++;
                            } else if (this.bombAnomationStep == ANIMATION_STEP_2) {
                                posY -= Config.POP_WEAPON_ANIMATION_MOV_BACK_SPEED;
                                this.bombAnomationStep = 0;
                                weapon.setUseAnimation(false);
                                weapon.setUseAnimationDone(true);
                            }
                            break;
                        case DOUBLE_LASER:
                            if (this.doubleLaserAnimationStep == ANIMATION_STEP_1) {
                                posY += Config.POP_WEAPON_ANIMATION_MOVE_FORWARD_SPEED;
                                this.doubleLaserAnimationStep++;
                            } else if (this.doubleLaserAnimationStep == ANIMATION_STEP_2) {
                                posY -= Config.POP_WEAPON_ANIMATION_MOV_BACK_SPEED;
                                this.doubleLaserAnimationStep = 0;
                                weapon.setUseAnimation(false);
                                weapon.setUseAnimationDone(true);
                            }
                            break;
                    }
                } else {
                    posY += weapon.getSpeed();
                }

                weapon.setPosY(posY);

                if (!weapon.isUseAnimationDone() && weapon.getPosY() >= this.getHeight() / 5) {
                    weapon.setUseAnimation(true);
                    switch (weapon.getWeaponType()) {
                        case BOMB:
                            if (this.bombAnomationStep == 0) {
                                this.bombAnomationStep++;
                            }
                            break;
                        case DOUBLE_LASER:
                            if (this.doubleLaserAnimationStep == 0) {
                                this.doubleLaserAnimationStep++;
                            }
                            break;
                    }
                }

                if (weapon.getPosY() >= this.getHeight()) {
                    weapon.setWeaponDisappear(true);
                } else {
                    if (weapon.getRectangle().intersects(myPlane.getRectange())) {
                        switch (weapon.getWeaponType()) {
                            case BOMB:
                                if (myPlane.getHoldBombCount() < Config.BOMB_MAX_HOLD_COUNT) {
                                    myPlane.getHoldBombList().add((Bomb) weapon);
                                    this.getBombSoundPlayer.play();
                                }
                                break;
                            case DOUBLE_LASER:
                                myPlane.setHitDoubleLaser(true);
                                this.getDoubleLaserSoundPlayer.play();
                                break;
                        }
                        weapon.setWeaponDisappear(true);
                    }
                }
            }
        }
    }

    private void drawScore(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<Integer> intList = new ArrayList<>();
        int scoreCopy = this.score;
        int quotient;
        while ((quotient = scoreCopy / 10) != 0) {
            intList.add(scoreCopy % 10);
            scoreCopy = quotient;
        }
        intList.add(scoreCopy % 10);
        // draw
        int posX = Config.SCORE_IMG_POS_X;
        int posY = Config.SCORE_IMG_POS_Y;
        g2d.drawImage(Images.SCORE_IMG, posX, posY, ImageConstants.SCORE_IMG_WIDTH, ImageConstants.SCORE_IMG_HEIGHT,
                this);
        posX += ImageConstants.SCORE_IMG_WIDTH;
        posY += ImageConstants.SCORE_IMG_HEIGHT - ImageConstants.NUMBER_0_HEIGHT;
        int size = intList.size();
        for (int i = size - 1; i >= 0; i--) {
            switch (intList.get(i)) {
                case Config.NUMBER_0:
                    g2d.drawImage(Images.NUMBER_0_IMG, posX, posY, ImageConstants.NUMBER_0_WIDTH,
                            ImageConstants.NUMBER_0_HEIGHT, this);
                    posX += ImageConstants.NUMBER_0_WIDTH;
                    break;
                case Config.NUMBER_1:
                    g2d.drawImage(Images.NUMBER_1_IMG, posX, posY, ImageConstants.NUMBER_1_WIDTH,
                            ImageConstants.NUMBER_1_HEIGHT, this);
                    posX += ImageConstants.NUMBER_1_WIDTH;
                    break;
                case Config.NUMBER_2:
                    g2d.drawImage(Images.NUMBER_2_IMG, posX, posY, ImageConstants.NUMBER_2_WIDTH,
                            ImageConstants.NUMBER_2_HEIGHT, this);
                    posX += ImageConstants.NUMBER_2_WIDTH;
                    break;
                case Config.NUMBER_3:
                    g2d.drawImage(Images.NUMBER_3_IMG, posX, posY, ImageConstants.NUMBER_3_WIDTH,
                            ImageConstants.NUMBER_3_HEIGHT, this);
                    posX += ImageConstants.NUMBER_3_WIDTH;
                    break;
                case Config.NUMBER_4:
                    g2d.drawImage(Images.NUMBER_4_IMG, posX, posY, ImageConstants.NUMBER_4_WIDTH,
                            ImageConstants.NUMBER_4_HEIGHT, this);
                    posX += ImageConstants.NUMBER_4_WIDTH;
                    break;
                case Config.NUMBER_5:
                    g2d.drawImage(Images.NUMBER_5_IMG, posX, posY, ImageConstants.NUMBER_5_WIDTH,
                            ImageConstants.NUMBER_5_HEIGHT, this);
                    posX += ImageConstants.NUMBER_5_WIDTH;
                    break;
                case Config.NUMBER_6:
                    g2d.drawImage(Images.NUMBER_6_IMG, posX, posY, ImageConstants.NUMBER_6_WIDTH,
                            ImageConstants.NUMBER_6_HEIGHT, this);
                    posX += ImageConstants.NUMBER_6_WIDTH;
                    break;
                case Config.NUMBER_7:
                    g2d.drawImage(Images.NUMBER_7_IMG, posX, posY, ImageConstants.NUMBER_7_WIDTH,
                            ImageConstants.NUMBER_7_HEIGHT, this);
                    posX += ImageConstants.NUMBER_7_WIDTH;
                    break;
                case Config.NUMBER_8:
                    g2d.drawImage(Images.NUMBER_8_IMG, posX, posY, ImageConstants.NUMBER_8_WIDTH,
                            ImageConstants.NUMBER_8_HEIGHT, this);
                    posX += ImageConstants.NUMBER_8_WIDTH;
                    break;
                case Config.NUMBER_9:
                    g2d.drawImage(Images.NUMBER_9_IMG, posX, posY, ImageConstants.NUMBER_9_WIDTH,
                            ImageConstants.NUMBER_9_HEIGHT, this);
                    posX += ImageConstants.NUMBER_9_WIDTH;
                    break;
            }
        }
    }

    private void drawBomb(Graphics g) {
        if (this.myPlane.getHoldBombCount() > 0) {
            Graphics2D g2d = (Graphics2D) g;
            int posX = Config.CAUGHT_BOMB_IMG_POS_X;
            int posY = Config.CAUGHT_BOMB_IMG_POS_Y;
            g2d.drawImage(Images.CAUGHT_BOMB_IMG, posX, posY, ImageConstants.CAUGHT_BOMB_WIDTH,
                    ImageConstants.CAUGHT_BOMB_HEIGHT, this);

            posX += ImageConstants.CAUGHT_BOMB_WIDTH;
            posY += (ImageConstants.CAUGHT_BOMB_HEIGHT - ImageConstants.X_MARK_HEIGHT) / 2;

            g2d.drawImage(Images.X_MARK_IMG, posX, posY, ImageConstants.X_MARK_WIDTH, ImageConstants.X_MARK_HEIGHT,
                    this);
            posX += ImageConstants.X_MARK_WIDTH;
            switch (this.myPlane.getHoldBombCount()) {
                case Config.ONE_BOMB:
                    g2d.drawImage(Images.NUMBER_1_IMG, posX, posY, ImageConstants.NUMBER_1_WIDTH,
                            ImageConstants.NUMBER_1_HEIGHT, this);
                    break;
                case Config.TWO_BOMB:
                    g2d.drawImage(Images.NUMBER_2_IMG, posX, posY, ImageConstants.NUMBER_2_WIDTH,
                            ImageConstants.NUMBER_2_HEIGHT, this);
                    break;
                case Config.THREE_BOMB:
                    g2d.drawImage(Images.NUMBER_3_IMG, posX, posY, ImageConstants.NUMBER_3_WIDTH,
                            ImageConstants.NUMBER_3_HEIGHT, this);
                    break;
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScore(g);
        drawBomb(g);
        myPlane.draw(g);
        for (EnemyPlane enemyPlane : this.enemyPlanes) {
            enemyPlane.draw(g);
        }

        synchronized (this.bullets) {
            for (Bullet b : this.bullets) {
                b.draw(g);
            }
        }
        if (this.popBomb != null && !this.popBomb.isWeaponDisappear()) {
            this.popBomb.draw(g);
        }
        if (this.popDoubleLaser != null && !this.popDoubleLaser.isWeaponDisappear()) {
            this.popDoubleLaser.draw(g);
        }
    }

    public void startGame() {
        this.score = 0;
        this.bombAnomationStep = 0;
        this.doubleLaserAnimationStep = 0;
        this.remainTimeToPopSmallPlane = Config.POP_SMALL_ENEMY_PLANE_INTERVAL;
        this.remainTimeToPopBigPlane = Config.POP_BIG_ENEMY_PLANE_INTERVAL;
        this.remainTimeToPopBossPlane = Config.POP_BOSS_ENEMY_PLANE_INTERVAL;
        this.remainTimeToPopBomb = Config.POP_BOMBO_INTERVAL;
        this.remainTimeToPopDoubleLaser = Config.POP_DOUBLE_LASER_INTERVAL;
        this.remainTimeDoubleLaserRunOut = Config.DOUBLE_LASER_LAST_TIME;

        this.enemyPlanes = new LinkedList<>();
        this.myPlane = new MyPlane(this);
        this.myPlane.setAlive(true);
        this.myPlane.setPosX((Config.MAIN_FRAME_WIDTH - ImageConstants.MY_PLANE_WIDTH) / 2);
        this.myPlane.setPosY(Config.MAIN_FRAME_HEIGHT - ImageConstants.MY_PLANE_HEIGHT);
        this.gameMusicSoundPlayer.loop();
        this.fireBulletSoundPlayer.loop();
        this.paintThread = new Thread(new PaintThread());
        this.paintThread.start();
    }

    public void stopGame() {
        this.myPlane.setAlive(false);
        this.gameMusicSoundPlayer.stop();
        this.fireBulletSoundPlayer.stop();
        this.bossPlaneFlyingSoundPlayer.stop();
        this.gameOverSoundPlayer.play();
    }

    private void useBomb() {
        if (this.myPlane.getHoldBombCount() > 0) {
            Graphics g = this.getComponentGraphics(this.getGraphics());
            for (EnemyPlane enemyPlane : this.enemyPlanes) {
                synchronized (this) {
                    this.score += enemyPlane.getKilledScore();
                }
                switch (enemyPlane.getEnemyType()) {
                    case SMALL_ENEMY_PLANE:
                        this.smallPlaneKilledSoundPlayer.play();
                        break;
                    case BIG_ENEMY_PLANE:
                        this.bigPlaneKilledSoundPlayer.play();
                        break;
                    case BOSS_ENEMY_PLANE:
                        this.bossPlaneKilledSoundPlayer.play();
                        break;
                }
                enemyPlane.drawKilled(g);
            }
            synchronized (this.bullets) {
                this.bullets.clear();
            }
            synchronized (this.enemyPlanes) {
                this.enemyPlanes.clear();
            }
            this.popBomb = null;
            this.popDoubleLaser = null;

            this.myPlane.getHoldBombList().remove(0);

            this.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // nothing to do
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.myPlane != null && this.myPlane.isAlive()) {
            myPlane.mouseMoved(e);
            this.repaint();
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<EnemyPlane> getEnemyPlanes() {
        return enemyPlanes;
    }

    public MyPlane getMyPlane() {
        return myPlane;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.myPlane.isAlive() && this.myPlane.getHoldBombCount() > 0) {
            useBombSoundPlayer.play();
            useBomb();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // nothing to do
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // nothing to do
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // nothing to do
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // nothing to do
    }

    @SuppressWarnings("EmptyCatchBlock")
    class PaintThread implements Runnable {

        @Override

        public void run() {
            while (myPlane.isAlive()) {

                synchronized (bullets) {
                    final Iterator<Bullet> bulletIterator = bullets.iterator();
                    while (bulletIterator.hasNext()) {
                        final boolean b = onBulletLocationChanged(bulletIterator.next());
                        if (b) {
                            bulletIterator.remove();
                        }
                    }
                }
                /*for (Bullet b : bullets) {
                    onBulletLocationChanged(b);
                }*/

                final Iterator<EnemyPlane> enemyPlaneIterator = enemyPlanes.iterator();
                while (enemyPlaneIterator.hasNext()) {

                    final boolean b = onEnemyPlaneLocationChanged(enemyPlaneIterator.next());
                    if (b) {
                        enemyPlaneIterator.remove();
                    }
                }
                /*for (EnemyPlane enemyPlane : enemyPlanes) {
                    onEnemyPlaneLocationChanged(enemyPlane);
                }*/

                // ADD PLANE
                if (remainTimeToPopSmallPlane > 0) {
                    remainTimeToPopSmallPlane -= Config.GAME_PANEL_REPAINT_INTERVAL;
                } else {
                    // pop a small enemy plane
                    EnemyPlane smallPlane = EnemyPlaneFactory.createEnemyPlane(GamePlayingPanel.this,
                            EnemyPlaneType.SMALL_ENEMY_PLANE);
                    synchronized (GamePlayingPanel.this.enemyPlanes) {
                        enemyPlanes.add(smallPlane);
                    }
                    remainTimeToPopSmallPlane = Config.POP_SMALL_ENEMY_PLANE_INTERVAL;
                }

                if (remainTimeToPopBigPlane > 0) {
                    remainTimeToPopBigPlane -= Config.GAME_PANEL_REPAINT_INTERVAL;
                } else {
                    // pop a big enemy plane
                    EnemyPlane bigPlane = EnemyPlaneFactory.createEnemyPlane(GamePlayingPanel.this,
                            EnemyPlaneType.BIG_ENEMY_PLANE);
                    synchronized (GamePlayingPanel.this.enemyPlanes) {
                        enemyPlanes.add(bigPlane);
                    }
                    remainTimeToPopBigPlane = Config.POP_BIG_ENEMY_PLANE_INTERVAL;
                }

                if (remainTimeToPopBossPlane > 0) {
                    remainTimeToPopBossPlane -= Config.GAME_PANEL_REPAINT_INTERVAL;
                } else {
                    // pop a boss enemy plane
                    EnemyPlane bossPlane = EnemyPlaneFactory.createEnemyPlane(GamePlayingPanel.this,
                            EnemyPlaneType.BOSS_ENEMY_PLANE);
                    synchronized (GamePlayingPanel.this.enemyPlanes) {
                        enemyPlanes.add(bossPlane);
                    }
                    remainTimeToPopBossPlane = Config.POP_BOSS_ENEMY_PLANE_INTERVAL;
                    bossPlaneFlyingSoundPlayer.loop();
                }

                // ADD BOMB
                if (remainTimeToPopBomb > 0) {
                    remainTimeToPopBomb -= Config.GAME_PANEL_REPAINT_INTERVAL;
                } else {
                    // pop bomb
                    popBomb = CatchableWeaponFactory.createCatchableWeapon(GamePlayingPanel.this,
                            CatchableWeaponType.BOMB);
                    remainTimeToPopBomb = Config.POP_BOMBO_INTERVAL;
                    popWeaponSoundPlayer.play();
                }

                if (popBomb != null) {
                    onCatchableWeaponLocationChanged(popBomb);
                }

                // ADD DOUBLE LASER
                if (remainTimeToPopDoubleLaser > 0) {
                    remainTimeToPopDoubleLaser -= Config.GAME_PANEL_REPAINT_INTERVAL;
                } else {
                    // pop double laser
                    popDoubleLaser = CatchableWeaponFactory.createCatchableWeapon(GamePlayingPanel.this,
                            CatchableWeaponType.DOUBLE_LASER);
                    remainTimeToPopDoubleLaser = Config.POP_DOUBLE_LASER_INTERVAL;
                    popWeaponSoundPlayer.play();
                }

                if (popDoubleLaser != null) {
                    onCatchableWeaponLocationChanged(popDoubleLaser);
                }

                // CHECK DOUBLE LASER BULLETS RUN OUT
                if (remainTimeDoubleLaserRunOut > 0) {
                    remainTimeDoubleLaserRunOut -= Config.GAME_PANEL_REPAINT_INTERVAL;
                } else {
                    myPlane.setHitDoubleLaser(false);
                    popDoubleLaser = null;
                    remainTimeDoubleLaserRunOut = Config.DOUBLE_LASER_LAST_TIME;
                }

                if (popBomb != null && popBomb.isWeaponDisappear()) {
                    popBomb = null;
                }

                if (popDoubleLaser != null && popDoubleLaser.isWeaponDisappear()) {
                    popDoubleLaser = null;
                }

                GamePlayingPanel.this.repaint();

                try {
                    Thread.sleep(Config.GAME_PANEL_REPAINT_INTERVAL);
                } catch (InterruptedException ignored) {

                }
            }
        }

    }

}
