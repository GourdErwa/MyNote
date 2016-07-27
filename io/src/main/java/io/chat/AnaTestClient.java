package io.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author wei.Li
 */
public class AnaTestClient {

    private static final String IP = "192.168.1.166";
    //private static final String IP = "127.0.0.1";
    private static final int PORT = 9292;
    private static final String HEART_BEAT = "(heartbeat)";
    private static InputStream in;
    private static OutputStream outputStream;
    private static Scanner sc;

    private AnaTestClient() {
    }

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
                    final String x = new String(b);

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
                    outputStream.write(line.getBytes());
                    outputStream.flush();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        }
    }

}
