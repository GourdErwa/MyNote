package io.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnaTestServer {

    private static final int PORT = 9292;
    private final static Map<String, CSocket> ALIVE_SOCKET = new ConcurrentHashMap<>();
    private final ExecutorService pool;
    private ServerSocket serverSocket = null;

    private AnaTestServer() {
        pool = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {
        new AnaTestServer().start();
    }

    private static void sendForUser(byte[] msg, String sendKey) {
        final String s = new String(msg);
        final Set<Map.Entry<String, CSocket>> entries = ALIVE_SOCKET.entrySet();
        for (Map.Entry<String, CSocket> entry : entries) {
            final String key = entry.getKey();
            final CSocket cSocket = entry.getValue();
            if (key.equals(sendKey)) {
                cSocket.send("(m) " + s);
            } else {
                cSocket.send("(o) " + s);
            }
        }
    }

    private static void sendForServer(byte[] msg) {
        final String s = new String(msg);
        for (CSocket cSocket : ALIVE_SOCKET.values()) {
            cSocket.send("(s) " + s);
        }
    }

    private static void sendForServer(String msg) {
        sendForServer(msg.getBytes());
    }

    private static void sendConnNum() {

        sendForServer(("conn num:" + ALIVE_SOCKET.size() + "").getBytes());
    }

    private static void closedSocket(String key) {

        final CSocket cSocket = ALIVE_SOCKET.remove(key);
        if (cSocket != null) {
            cSocket.close();
        }
    }

    private void start() {

        if (this.serverSocket != null) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException ignored) {
                    }
                    sendForServer("(heartbeat)");
                    final int size = ALIVE_SOCKET.size();
                    if (size < 2) {
                        sendForServer("alert : conn num = " + size);
                    }
                }
            }
        }).start();

        CSocket as = null;
        try {
            this.serverSocket = new ServerSocket(PORT);
            System.out.println("start succeed...");
            while (true) {
                Socket socket = serverSocket.accept();
                if (ALIVE_SOCKET.size() > 2) {
                    System.exit(0);
                }
                socket.setSoLinger(true, 0);
                socket.setSoTimeout(5 * 60 * 1000);
                as = new CSocket(socket);
                final String key = socket.getInetAddress().toString() + "-" + socket.getPort();
                ALIVE_SOCKET.put(key, as);
                sendConnNum();
                pool.execute(as);
            }
        } catch (Exception ignored) {
        } finally {
            if (as != null) {
                as.close();
            }
        }
    }

    private class CSocket implements Runnable {
        private String key;
        private Socket socket;
        private OutputStream out = null;
        private InputStream in = null;
        private boolean run = true;

        CSocket(Socket socket) throws IOException {
            this.key = socket.getInetAddress().toString() + "-" + socket.getPort();
            this.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
        }

        void send(String msg) {
            try {
                if (this.socket.isConnected() && !this.socket.isClosed()) {
                    out.write(msg.getBytes());
                    out.flush();
                } else {
                    closedSocket(key);
                    sendConnNum();
                }
            } catch (Exception ignored) {
                closedSocket(key);
                sendConnNum();
            }
        }

        @Override
        public void run() {

            try {
                byte bytes[];
                while (run) {
                    final int available = in.available();
                    if (available < 1) {
                        Thread.sleep(100L);
                        continue;
                    }
                    bytes = new byte[available];
                    in.read(bytes);
                    AnaTestServer.sendForUser(bytes, key);
                }
            } catch (Exception ignored) {
                closedSocket(key);
            } finally {
                this.close();
            }
        }

        private void close() {

            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                    socket.shutdownOutput();
                    socket.shutdownOutput();
                }

                run = false;
                closedSocket(key);
            } catch (Exception ignored) {
            }
        }
    }

}
