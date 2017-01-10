/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package snmp.vo;

import org.snmp4j.*;
import org.snmp4j.mp.StateReference;
import org.snmp4j.mp.StatusInformation;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * snmp 模拟服务端
 *
 * @author wei.Li by 15/2/27 (gourderwa@163.com).
 */
public class AgentVo {


    public static void main(String argv[]) {
        Handler h = new Handler();
        /** 初始化参数 * */
        h.configure();
        h.start();
        /** Do nothing loop * */
        while (true) {
            System.out.println("----------loop-------------");
            synchronized (AgentVo.class) {
                try {
                    AgentVo.class.wait();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static class Handler implements CommandResponder {
        protected String mAddress = null;
        protected int mPort = 0;
        protected String mMyCommunityName = null;
        protected TransportMapping mServerSocket = null;
        protected Snmp mSNMP = null;

        public Handler() {
        }

        public void configure() {
            mAddress = "192.168.1.101";
            mPort = 8999;
            mMyCommunityName = "OAagent";
        }

        public void start() {
            try {
                mServerSocket = new DefaultUdpTransportMapping(
                        new UdpAddress(java.net.InetAddress
                                .getByName(mAddress), mPort));
                mSNMP = new Snmp(mServerSocket);
                mSNMP.addCommandResponder(this);
                mServerSocket.listen();
            } catch (IOException vException) {
                vException.printStackTrace();
                System.err.println(vException.getMessage());
            }
        }

        public synchronized void processPdu(
                CommandResponderEvent aEvent) {
            String vCommunityName = new String(aEvent
                    .getSecurityName());
            System.out.println("Community name " + vCommunityName);
            PDU vPDU = aEvent.getPDU();
            Config config = new Config();
            if (vPDU == null) {
                System.out.println("Null pdu");
            } else {
                System.out.println("(rcv) " + vPDU.toString());
                switch (vPDU.getType()) {
                    case PDU.GET:
                    case PDU.GETNEXT:
                        break;
                    case PDU.SET:
                        System.out.println("------SET----------");
                        String reciv = vPDU.get(0).getVariable().getSyntaxString();
                        System.out.println("----set------" + vPDU.get(0).toString());
                        String setoid = vPDU.get(0).toString();
                        System.out.println("-----set-----" + setoid.substring(0, setoid.indexOf("=") - 1));
                        System.out.println("-----set-----" + setoid.substring(setoid.indexOf("=") + 1));
                        config.setValueByOID(setoid.substring(0, setoid.indexOf("=") - 1)
                                .trim(), setoid.substring(setoid.indexOf("=") + 1).trim());
                }
                StatusInformation statusInformation = new StatusInformation();
                StateReference ref = aEvent.getStateReference();
                try {
                    System.out.println("Sending response");
                    vPDU.setType(PDU.RESPONSE);

                    OID oid = vPDU.get(0).getOid();
                    String setoid = vPDU.get(0).toString();
                    System.out.println("----get------" + setoid.substring(0, setoid.indexOf("=") - 1));
                    System.out.println("-----get-----" + setoid.substring(setoid.indexOf("=") + 1));
                    vPDU.set(0, new VariableBinding(oid,
                            new OctetString(config.getValueByOID(setoid.substring(0, setoid.indexOf("=") - 1)
                                    .trim()))));

                    aEvent.getMessageDispatcher().returnResponsePdu(
                            aEvent.getMessageProcessingModel(),

                            aEvent.getSecurityModel(),
                            aEvent.getSecurityName(),

                            aEvent.getSecurityLevel(), vPDU,
                            aEvent.getMaxSizeResponsePDU(), ref,

                            statusInformation);
                } catch (MessageException vException) {
                    System.out.println(vException);
                }
            }
        }
    }
}

class Config {

    String mibFilePath = "/lw/workfile/intellij_work/my_note/src/main/java/com/framework_technology/snmp/vo/mib.properties";
    Properties properties;
    Map map;

    public Config() {
        properties = new Properties();

        try {
            properties.load(new FileInputStream(mibFilePath));
        } catch (IOException e) {
            System.out.println("读取properties文件错误");
            e.printStackTrace();
        }
    }

    //测试主函数
    public static void main(String[] args) {
        Config cfg = new Config();
        String oid = "1.3.6.1.2.1.1.8.0";
        System.out.println("---------" + cfg.getValueByOID(oid));

        cfg.setValueByOID(oid, "test");


        System.out.println("---------" + cfg.getValueByOID(oid));
    }

    /**
     * 根据oid获取value
     *
     * @param oid
     * @return
     */
    public String getValueByOID(String oid) {
        return properties.getProperty(oid);
    }

    public void setValueByOID(String oid, String value) {

        properties.setProperty(oid, value);
        try {
            properties.store(new FileOutputStream("mib.properties"), "mib.properties");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
