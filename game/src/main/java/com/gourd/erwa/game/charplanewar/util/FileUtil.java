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

import com.gourd.erwa.game.charplanewar.entity.Score;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件读取工具类
 */
public class FileUtil {


    /**
     * Read file to string string.
     *
     * @param filePath the file path
     * @return the string
     * @throws IOException the io exception
     */
    public static String readFileToString(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(new String(line.getBytes(), "utf-8")).append("\r\n");
        }
        br.close();
        return sb.toString();
    }

    /**
     * 序列化成绩
     *
     * @param scoreList scoreList
     * @param filePath  文件路径
     * @throws IOException the io exception
     */
    public static void writeScore(List<Score> scoreList, String filePath) throws IOException {
        ObjectOutputStream objOutputStream = null;
        try {
            objOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
            for (Score score : scoreList) {
                objOutputStream.writeObject(score);
            }
            objOutputStream.writeObject(null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objOutputStream != null) {
                objOutputStream.flush();
                objOutputStream.close();
            }
        }
    }

    /**
     * 反序列化成绩
     *
     * @param filePath 文件路径
     * @return 读取后的成绩 list
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static List<Score> readScore(String filePath) throws IOException,
            ClassNotFoundException {
        List<Score> scoreList = new ArrayList<>();
        ObjectInputStream objInputStream = new ObjectInputStream(new FileInputStream(filePath));
        Object obj;
        while ((obj = objInputStream.readObject()) != null) {
            scoreList.add((Score) obj);
        }
        objInputStream.close();
        return scoreList;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        final String resourceAsStream = FileUtil.class.getResource("/resources").getPath();
        System.out.println(resourceAsStream);
        final String resource = System.getProperty("user.dir");
        File file = new File(resource + "shoot_background.png");
        try {
            System.out.println(file.getCanonicalPath());
            System.out.println(file.exists());
            final BufferedImage read = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
