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

package snmp;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;


/**
 * The type Snmp manager.
 *
 * @author wei.Li by 15/2/27 (gourderwa@163.com).
 */
public class SnmpManager {
    private Snmp snmp = null;
    private String version = null;

    /**
     * Instantiates a new Snmp manager.
     *
     * @param version version
     */
    public SnmpManager(String version) {
        try {
            this.version = version;
            TransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            if (version.equals("3")) {
                //设置安全模式
                USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
                        MPv3.createLocalEngineID()), 0);
                SecurityModels.getInstance().addSecurityModel(usm);
            }
            //开始监听消息
            transport.listen();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SnmpManager manager = new SnmpManager("2c");
        //构造报文
        PDU pdu = new PDU();
        //  PDU pdu = new ScopedPDU();
        //设置要获取的对象ID
        OID oids = new OID("1.3.6.1.2.1.1.1.0");
        pdu.add(new VariableBinding(oids));
        //设置报文类型
        pdu.setType(PDU.GETNEXT);
        //   ((ScopedPDU) pdu).setContextName(new OctetString("priv"));
        try {
            //发送消息   其中最后一个是想要发送的目标地址
            manager.sendMessage(false, true, pdu, "udp:192.168.1.255/161");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Send message.
     *
     * @param syn  是否是同步模式
     * @param bro  是否是广播
     * @param pdu  要发送的报文
     * @param addr 目标地址
     * @throws IOException the io exception
     */
    public void sendMessage(Boolean syn, final Boolean bro, PDU pdu,
                            String addr) throws IOException {
        //生成目标地址对象
        Address targetAddress = GenericAddress.parse(addr);
        Target target;
        if (version.equals("3")) {
            //添加用户
            snmp.getUSM().addUser(
                    new OctetString("MD5DES"),
                    new UsmUser(new OctetString("MD5DES"), AuthMD5.ID,
                            new OctetString("MD5DESUserAuthPassword"), PrivDES.ID,
                            new OctetString("MD5DESUserPrivPassword")));

            target = new UserTarget();
            //设置安全级别
            ((UserTarget) target).setSecurityLevel(SecurityLevel.AUTH_PRIV);
            ((UserTarget) target).setSecurityName(new OctetString("MD5DES"));
            target.setVersion(SnmpConstants.version3);
        } else {
            target = new CommunityTarget();
            if (version.equals("1")) {
                target.setVersion(SnmpConstants.version1);
                ((CommunityTarget) target).setCommunity(new OctetString("public"));
            } else {
                target.setVersion(SnmpConstants.version2c);
                ((CommunityTarget) target).setCommunity(new OctetString("public"));
            }

        }
        // 目标对象相关设置
        target.setAddress(targetAddress);
        target.setRetries(5);
        target.setTimeout(1000);


        if (syn.equals(true)) {
            //发送报文  并且接受响应
            ResponseEvent response = snmp.send(pdu, target);
            //处理响应
            System.out.println("Synchronize message from "
                    + response.getPeerAddress() + "/nrequest:"
                    + response.getRequest() + "/nresponse:"
                    + response.getResponse());
        } else {
            //设置监听对象
            ResponseListener listener = new ResponseListener() {
                @Override
                public void onResponse(ResponseEvent event) {
                    // TODO Auto-generated method stub
                    if (bro.equals(false)) {
                        ((Snmp) event.getSource()).cancel(event.getRequest(),
                                this);
                    }
                    //处理响应
                    PDU request = event.getRequest();
                    PDU response = event.getResponse();
                    System.out.println("Asynchronise message from "
                            + event.getPeerAddress() + "/nrequest:" + request
                            + "/nresponse:" + response);
                }
            };
            //发送报文
            snmp.send(pdu, target, null, listener);
        }
    }
}
