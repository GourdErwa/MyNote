package plug.syslog4j;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.net.udp.UDPNetSyslogConfig;

/**
 * 客户端发送 log
 *
 * @author lw by 14-7-14.
 */
public class Syslog4j_Client {


    static int i = 0;
    static int temp = 1;

    private static void writeLog(SyslogIF syslog) {
        syslog.info("ez02|0|20150121|10:58:00|192.168.1.11|1|测试基线_ALL_1|测试-自定义组件编号|网银交易|0.00|0.00|0.00|0.00|60|##");
    }

    private static SyslogIF getSyslog() {


        final UDPNetSyslogConfig udpNetSyslogConfig = new UDPNetSyslogConfig();

        final SyslogIF udp = Syslog.getInstance("UDP");
        //final SyslogConfigIF udpNetSyslogConfig = udp.getConfig();

        // System.out.println(udpNetSyslogConfig);
        if (i % 2 != 0) {
            udpNetSyslogConfig.setHost("192.168.1.57");
        } else {
            udpNetSyslogConfig.setHost("192.168.1.111");
        }
        udpNetSyslogConfig.setPort(1514);
        udpNetSyslogConfig.setCharSet("gbk");
        udpNetSyslogConfig.setFacility(SyslogConstants.FACILITY_LOCAL1);
        return Syslog.createInstance("udp" + i, udpNetSyslogConfig);
    }

    public static void main(String[] args) {
        for (; i < 4; i++) {
            final SyslogIF syslog = getSyslog();
            final SyslogConfigIF syslogConfig = syslog.getConfig();
            final String syslogStr = syslogConfig.getHost() + "#" + syslogConfig.getPort() + "#" + syslog.getProtocol();
            System.out.println(syslogStr);
        }

    }
}
