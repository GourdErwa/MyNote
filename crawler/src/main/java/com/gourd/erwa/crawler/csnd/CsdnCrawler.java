package com.gourd.erwa.crawler.csnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.Li
 */
public class CsdnCrawler {

    private static final String CSDN_URL = "http://blog.csdn.net/";

    public static void main(String[] args) {

        new CsdnCrawler().crawler(CSDN_URL);

    }

    private void crawler(String ur) {

        URL url;
        try {

            url = new URL(ur);
            System.out.println(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        final List<String> list = new ArrayList<>();

        BufferedReader in = null;
        try {
            final URLConnection connection = url.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset()));


            String line;
            while ((line = in.readLine()) != null) {
                list.add(line);
                if (line.contains("href=\"")) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
        }
    }
}
