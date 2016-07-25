package io;


import java.io.*;

/**
 * 且套流关闭问题
 *
 * @author wei.Li
 */
public class CloseQuestion {

    public static void main(String[] args) throws FileNotFoundException {

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ignored) {
            }
        }

    }

}
