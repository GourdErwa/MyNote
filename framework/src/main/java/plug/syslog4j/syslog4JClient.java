package plug.syslog4j;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.net.tcp.TCPNetSyslogConfig;
import org.productivity.java.syslog4j.impl.net.udp.UDPNetSyslogConfig;

import java.nio.charset.Charset;

/**
 * 客户端发送 log
 *
 * @author lw by 14-7-14.
 */
public class syslog4JClient {

    private static final int MAX_MESSAGE_LENGTH = 65535;
    private static final String MESSAGE = "ez02|0|20150121|10:58:00|192.168.1.11|1|测试基线_ALL_1|测试-自定义组件编号|网银交易|0.00|0.00|0.00|0.00|60|##";

    /**
     * @param protocol protocol
     * @return 获取协议对应的 SyslogConfigIF 设置
     */
    private static SyslogConfigIF getSyslogConfigIFOfProtocol(ProtocolType protocol) {

        if (ProtocolType.TCP.equals(protocol)) {
            final TCPNetSyslogConfig tcpNetSyslogConfig = new TCPNetSyslogConfig();
            tcpNetSyslogConfig.setMaxMessageLength(MAX_MESSAGE_LENGTH);
            return tcpNetSyslogConfig;
        } else if (ProtocolType.UDP.equals(protocol)) {
            final UDPNetSyslogConfig udpNetSyslogConfig = new UDPNetSyslogConfig();
            udpNetSyslogConfig.setMaxMessageLength(MAX_MESSAGE_LENGTH);
            return udpNetSyslogConfig;
        } else {
            throw new IllegalArgumentException("ProtocolType [" + protocol + "] nonsupport");
        }
    }

    public static void main(String[] args) {

        final SyslogConfigIF syslogConfigIF = getSyslogConfigIFOfProtocol(ProtocolType.TCP);
        final SyslogIF syslogIF = Syslog.createInstance(Joiner.on("#")
                .join("UDP", "192.168.1.137", 1541), syslogConfigIF);

        for (int i = 0; i < 5; i++) {
            final String s = Strings.padStart(MESSAGE, 10000, '哈');
            System.out.println(s.getBytes(Charset.defaultCharset()).length);
            syslogIF.info(s);
            syslogIF.flush();
        }

        syslogIF.shutdown();

    }
}
