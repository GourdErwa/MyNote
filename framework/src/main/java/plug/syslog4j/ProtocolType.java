package plug.syslog4j;

/**
 * 协议枚举
 */
public enum ProtocolType {

    TCP,
    UDP,
    SNMP,
    SYSLOG;

    public static ProtocolType fromName(String protocol) {
        return valueOf(protocol.toUpperCase());
    }
}
