/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 * Email :	gourderwa@163.com
 *
 *
 *
 *
 *
 *
 */

package kaimo;

import java.io.*;

/**
 * @author wei.Li by 15/8/16
 */
public class CopyFileTest {

    public static final String READ_FILE_PATH = "/lw/temp/read.txt";
    public static final String WRITE_FILE_PATH = "/lw/temp/write.txt";


    public static void main(String[] args) {

        copyFile(READ_FILE_PATH, WRITE_FILE_PATH);
    }


    /**
     * copy 文件
     *
     * @param sourceFilePath 源文件路径
     * @param targetFilePath copy 的目的文件路径
     * @return copy 是否成功,成功为 true,失败为 false
     */
    private static boolean copyFile(String sourceFilePath, String targetFilePath) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {

            reader = new BufferedReader(new FileReader(sourceFilePath));
            writer = new BufferedWriter(new FileWriter(targetFilePath));

            String all;

            while ((all = reader.readLine()) != null) {
                writer.write(all);
                System.out.println(writer);
            }
        } catch (Exception e) {
            System.out.println("蜜有介个文件");
            File f = new File(targetFilePath);
            if (f.exists()) {
                f.delete();
            }
            return false;

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ignored) {

            }
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }

            } catch (Exception ignored) {

            }
        }
        return true;
    }


}
