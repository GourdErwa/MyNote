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

package com.gourd.erwa.game.charplanewar.config;


import com.gourd.erwa.game.charplanewar.GamePlaneWarMain;

public class Config {

    // non-changeable configuration
    public final static String LOGO_IMG = GamePlaneWarMain.CONF_PATH + "logo.png";
    public final static String SHOOT_BACKGROUND_IMG = GamePlaneWarMain.CONF_PATH + "shoot_background.png";
    public final static String SHOOT_IMG = GamePlaneWarMain.CONF_PATH + "shoot.png";
    public final static String FONT_IMG = GamePlaneWarMain.CONF_PATH + "font.png";
    public final static String BUTTON_BG_IMG = GamePlaneWarMain.CONF_PATH + "button_bg.png";
    public final static String BUTTON_HOVER_BG_IMG = GamePlaneWarMain.CONF_PATH + "button_hover_bg.png";

    public final static String ACHIEVEMENT_AUDIO = GamePlaneWarMain.CONF_PATH + "achievement.wav";

    public final static String SMALL_PLANE_KILLED_AUDIO = GamePlaneWarMain.CONF_PATH + "small_plane_killed.wav";
    public final static String BIG_PLANE_KILLED_AUDIO = GamePlaneWarMain.CONF_PATH + "big_plane_killed.wav";
    public final static String BOSS_PLANE_KILLED_AUDIO = GamePlaneWarMain.CONF_PATH + "boss_plane_killed.wav";

    public final static String BOSS_PLANE_FLYING_AUDIO = GamePlaneWarMain.CONF_PATH + "boss_plane_flying.wav";
    public final static String POP_WEAPON_AUDIO = GamePlaneWarMain.CONF_PATH + "pop_weapon.wav";

    public final static String USER_BOMB_AUDIO = GamePlaneWarMain.CONF_PATH + "use_bomb.wav";
    public final static String FIRE_BULLET_AUDIO = GamePlaneWarMain.CONF_PATH + "fire_bullet.wav";

    public final static String GAME_MUSIC_AUDIO = GamePlaneWarMain.CONF_PATH + "game_music.wav";
    public final static String GAME_OVER_AUDIO = GamePlaneWarMain.CONF_PATH + "game_over.wav";

    public final static String GET_BOMB_AUDIO = GamePlaneWarMain.CONF_PATH + "get_bomb.wav";
    public final static String GET_DOUBLE_LASER_AUDIO = GamePlaneWarMain.CONF_PATH + "get_double_laser.wav";

    public final static String BUTTON_ACTION_AUDIO = GamePlaneWarMain.CONF_PATH + "button.wav";

    public static final String HELP_FILE_PATH = GamePlaneWarMain.CONF_PATH + "help.html";
    public static final String SCORE_FILE = GamePlaneWarMain.CONF_PATH + "score.dat";

    public final static int MAIN_FRAME_WIDTH = 480;
    public final static int MAIN_FRAME_HEIGHT = 700;

    public final static int POP_UP_MENU_PANEL_WIDTH = 160;
    public final static int POP_UP_MENU_PANEL_HEIGHT = 248;

    public final static int POP_UP_SCORE_PANEL_WIDTH = 160;
    public final static int POP_UP_SCORE_PANEL_HEIGHT = 634;

    public final static int HELP_DIALOG_WIDTH = 800;
    public final static int HELP_DIALOG_HEIGHT = 600;

    public final static int SCORE_IMG_POS_X = 5;
    public final static int SCORE_IMG_POS_Y = 5;

    public final static int CAUGHT_BOMB_IMG_POS_X = 5;
    public final static int CAUGHT_BOMB_IMG_POS_Y = 770;

    public final static int KILL_SMALL_PLANE_SCORE = 1000;
    public final static int KILL_BIG_PLANE_SCORE = 6000;
    public final static int KILL_BOSS_PLANE_SCORE = 30000;

    public final static int BULLET_COUNT_TO_KILL_SMALL_PLANE = 1;
    public final static int BULLET_COUNT_TO_KILL_BIG_PLANE = 10;
    public final static int BULLET_COUNT_TO_KILL_BOSS_PLANE = 20;

    public final static int SMALL_PLANE_STATUS_CHANGE_INTERVAL = 90;
    public final static int BIG_PLANE_STATUS_CHANGE_INTERVAL = 90;
    public final static int BOSS_PLANE_STATUS_CHANGE_INTERVAL = 170;

    public final static int GAME_LOADING_INTERVAL = 600;
    public final static int MAX_SCORE_COUNT = 10;

    public final static int NUMBER_0 = 0;
    public final static int NUMBER_1 = 1;
    public final static int NUMBER_2 = 2;
    public final static int NUMBER_3 = 3;
    public final static int NUMBER_4 = 4;
    public final static int NUMBER_5 = 5;
    public final static int NUMBER_6 = 6;
    public final static int NUMBER_7 = 7;
    public final static int NUMBER_8 = 8;
    public final static int NUMBER_9 = 9;

    public final static int BOMB_MAX_HOLD_COUNT = 3;
    public final static int ONE_BOMB = 1;
    public final static int TWO_BOMB = 2;
    public final static int THREE_BOMB = 3;

    // changeable configuration
    public static int DOUBLE_LASER_LAST_TIME = 28000;// 28 seconds
    /**
     * 子弹开火时间间隔
     */
    public static int BULLET_FIRE_INTERVAL = 120;
    /**
     * 游戏画面刷新时间间隔80ms
     */
    public static int GAME_PANEL_REPAINT_INTERVAL = 80;

    public static int YELLOW_BULLET_MOVE_SPEED = 50;
    public static int BLUE_BULLET_MOVE_SPEED = 50;

    public static int ENEMY_PLANE_MOVE_SPEED_MIN = 30;
    public static int ENEMY_PLANE_MOVE_SPEED_MAX = 40;

    public static int POP_WEAPON_ANIMATION_MOVE_FORWARD_SPEED = 60;
    public static int POP_WEAPON_ANIMATION_MOV_BACK_SPEED = 200;

    public static int POP_WEAPON_MOVE_SPEED = 30;

    public static int POP_DOUBLE_LASER_INTERVAL = 30000;
    public static int POP_BOMBO_INTERVAL = 58000;
    /**
     * 小型敌机刷出时间间隔为300ms
     */
    public static int POP_SMALL_ENEMY_PLANE_INTERVAL = 300;
    /**
     * 中型敌机刷出时间间隔为6000ms
     */
    public static int POP_BIG_ENEMY_PLANE_INTERVAL = 6000;
    /**
     * boss型敌机刷出时间间隔为12000ms
     */
    public static int POP_BOSS_ENEMY_PLANE_INTERVAL = 12000;

}
