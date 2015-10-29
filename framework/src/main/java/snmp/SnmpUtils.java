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

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.Vector;

/**
 * @author wei.Li by 15/2/27 (gourderwa@163.com).
 */
public class SnmpUtils {


    private Snmp snmp = null;

    private Address targetAddress = null;

    public static void main(String[] args) {
        try {
            SnmpUtils util = new SnmpUtils();
            util.initComm();
            util.sendPDU();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initComm() throws IOException {

        // 设置Agent方的IP和端口
        targetAddress = GenericAddress.parse("udp:127.0.0.1/161");
        TransportMapping transport = new
                DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }

    public void sendPDU() throws IOException {
        // 设置 target
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        // 通信不成功时的重试次数
        target.setRetries(2);
        // 超时时间
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version1);
        // 创建 PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(new int[]{1, 3, 6, 1, 2, 1, 1, 5, 0})));
        // MIB的访问方式
        pdu.setType(PDU.GET);
        // 向Agent发送PDU，并接收Response
        ResponseEvent respEvnt = snmp.send(pdu, target);
        // 解析Response
        if (respEvnt != null && respEvnt.getResponse() !=
                null) {
            Vector<VariableBinding> recVBs = respEvnt.getResponse().getVariableBindings();
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i);
                System.out.println(
                        recVB.getOid() + " : " +
                                recVB.getVariable()
                );
            }
        }
    }
}
