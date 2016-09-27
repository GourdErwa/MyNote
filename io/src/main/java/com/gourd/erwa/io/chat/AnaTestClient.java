package com.gourd.erwa.io.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * The type Ana test client.
 *
 * @author wei.Li
 */
public class AnaTestClient {

    //private static final String IP = "192.168.1.166";
    private static final String IP = "127.0.0.1";
    private static final int PORT = 9292;
    private static final String HEART_BEAT = "(heartbeat)";
    private static final Base64Str BASE_64_STR = new Base64Str();
    private static InputStream in;
    private static OutputStream outputStream;
    private static Scanner sc;

    private AnaTestClient() {
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        new AnaTestClient().invoke();
    }

    private void invoke() {

        try {
            Socket socket = new Socket();
            socket.setSoLinger(true, 0);
            socket.setSoTimeout(5 * 60 * 1000);
            socket.connect(new InetSocketAddress(IP, PORT));
            if (socket.isConnected()) {
                System.out.println("conn succeed ...");
                in = socket.getInputStream();
                outputStream = socket.getOutputStream();
                sc = new Scanner(System.in);
                new Thread(new InputMonitor()).start();
                new Thread(new OutputMonitor()).start();
            } else {
                System.err.println("conn failure");
            }
        } catch (IOException e) {
            System.err.println("conn failure : " + e.getMessage());
        }
    }

    private static class InputMonitor implements Runnable {

        @Override
        public void run() {

            try {
                byte[] b;

                while (true) {

                    final int available = in.available();
                    if (available <= 0) {
                        continue;
                    } else {
                        Thread.sleep(200L);
                    }

                    b = new byte[available];
                    in.read(b);
                    String x = new String(b);
                    x = BASE_64_STR.decrypt(x);

                    if (!x.equals(HEART_BEAT)) {
                        System.out.println(x);
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        }
    }

    private static class OutputMonitor implements Runnable {

        @Override
        public void run() {
            try {
                String line;
                while (true) {
                    line = sc.nextLine();
                    if (line == null || line.isEmpty()) {
                        continue;
                    }
                    outputStream.write(BASE_64_STR.encrypt(line).getBytes(Charset.forName("utf-8")));
                    outputStream.flush();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        }
    }

    private static final class Base64Str {

        private String key;

        private Base64Str() {
            try {
                this.key = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                System.err.println(e.getMessage());
            }

        }

        /**
         * Encrypt string.
         *
         * @param s the s
         * @return the string
         */
        String encrypt(String s) {
            String str = "";
            int ch;
            if (!(s == null)) {
                for (int i = 0, j = 0; i < s.length(); i++, j++) {
                    if (j > key.length() - 1) {
                        j = j % key.length();
                    }
                    ch = s.codePointAt(i) + key.codePointAt(j);
                    if (ch > 65535) {
                        ch = ch % 65535;
                    }
                    str += (char) ch;
                }
            }
            return str;
        }

        /**
         * Decrypt string.
         *
         * @param s the s
         * @return the string
         */
        String decrypt(String s) {
            String str = "";
            int ch;
            for (int i = 0, j = 0; i < s.length(); i++, j++) {
                if (j > key.length() - 1) {
                    j = j % key.length();
                }
                ch = (s.codePointAt(i) + 65535 - key.codePointAt(j));
                if (ch > 65535) {
                    ch = ch % 65535;
                }
                str += (char) ch;
            }
            return str;
        }

    }


}
