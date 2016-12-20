package plug.syslog4j;

import org.productivity.java.syslog4j.server.SyslogServerMain;

/**
 * 服务端启动接受 log
 *
 * @author lw by 14-7-14.
 */
public class syslog4JService {
    /**
     * org.productivity.java.syslog4j.server.SyslogServerMain
     * <p>
     * -h 192.168.1.58 -p 1514 -o /lw/workfile/log/syslog4j_test/syslog4j.log -a udp
     * <p>
     * Usage:
     * <p>
     * SyslogServer [-h <host>] [-p <port>] [-o <file>] [-a] [-q] <protocol>
     * <p>
     * -h <host>    host or IP to bind
     * -p <port>    port to bind
     * -t <timeout> socket timeout (in milliseconds)
     * -o <file>    file to write entries (overwrites by default)
     * <p>
     * -a           append to file (instead of overwrite)
     * -q           do not write anything to standard out
     * <p>
     * protocol     Syslog4j protocol implementation (tcp, udp, ...)
     */


    private static final String SYSLOGSERVER_ARGS;

    static {
        SYSLOGSERVER_ARGS = "-h 192.168.1.105 -p 1541 -o /lw/workfile/test/syslog4j.log -a udp";
    }

    //启动服务端接受syslog
    public static void main(String[] args) throws Exception {
        SyslogServerMain.main(SYSLOGSERVER_ARGS.split(" "));
    }


}
