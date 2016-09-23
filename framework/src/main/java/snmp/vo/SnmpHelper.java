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

import com.alibaba.fastjson.JSON;
import com.google.common.io.Files;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * The type Snmp helper.
 *
 * @author wei.Li by 15/2/27 (gourderwa@163.com).
 */
public class SnmpHelper {

    private static Snmp snmp = null;


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {


        String filePath = "/lw/workfile/intellij_work/my_note/src/main/java/com/framework_technology/snmp/vo/snmp.json";

        try {
            final SnmpSetting snmpSetting = initJsonFileToSnmpSetting(filePath);
            initSnmp(snmpSetting);
            sendMessage(snmpSetting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * init Json 文件为 javabean
     *
     * @param filePath json 文件位置
     * @return 转换后的 javabean 对象
     */
    public static SnmpSetting initJsonFileToSnmpSetting(String filePath) {
        try {
            //objectMapper.readValue(new File(filePath), SnmpSetting.class);
            final SnmpSetting snmpSetting = JSON.parseObject(Files.toString(new File(filePath), Charset.defaultCharset()), SnmpSetting.class);
            //是否含有 version ==3 的PDU
            final List<PduSetting> pduSettings = snmpSetting.getPduSettings();
            for (PduSetting pduSetting : pduSettings) {
                if (pduSetting.getVersion() == 3) {
                    snmpSetting.setVersion(3);
                    break;
                }
            }
            return snmpSetting;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * initSnmp
     *
     * @param snmpSetting the snmp setting
     * @throws IOException the io exception
     */
    public static void initSnmp(SnmpSetting snmpSetting) throws IOException {

        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        if (snmpSetting.getVersion() == 3) {
            //设置安全模式
            USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
                    MPv3.createLocalEngineID()), 0);
            SecurityModels.getInstance().addSecurityModel(usm);
        }
        snmp.listen();
    }


    /**
     * Send message.
     *
     * @param snmpSetting snmpSetting
     * @throws IOException the io exception
     */
    public static void sendMessage(SnmpSetting snmpSetting) throws IOException {

        //生成目标地址对象
        Address targetAddress = GenericAddress.parse(snmpSetting.getTargetAddress());
        final List<PduSetting> pduSettings = snmpSetting.getPduSettings();

        for (PduSetting pduSetting : pduSettings) {
            Target target = setTarget(targetAddress, pduSetting);
            setPDUAndHandleSynBro(target, pduSetting);
        }

    }

    /**
     * 生成PDU 处理同步异步、是否广播情况
     *
     * @param target     target
     * @param pduSetting pduSetting
     * @throws IOException
     */
    private static void setPDUAndHandleSynBro(Target target, PduSetting pduSetting) throws IOException {

        final boolean synchronization = pduSetting.isSynchronization();

        PDU pdu;
        try {
            pdu = setPDU(pduSetting);
        } catch (Exception e) {
            return;
        }

        if (synchronization) {
            //发送报文  并且接受响应
            ResponseEvent response = snmp.send(pdu, target);
            //处理响应
            System.out.println("Synchronize message from "
                    + response.getPeerAddress() + "   request:"
                    + response.getRequest() + "   response:"
                    + response.getResponse());
        } else {
            //设置监听对象
            ResponseListener listener = new ResponseListener() {
                @Override
                public void onResponse(ResponseEvent event) {
                    // TODO Auto-generated method stub
                    final boolean bro = pduSetting.isBroadcast();
                    if (bro) {
                        ((Snmp) event.getSource()).cancel(event.getRequest(), this);
                    }
                    //处理响应
                    PDU request = event.getRequest();
                    PDU response = event.getResponse();
                    System.out.println("Asynchronise message from "
                            + event.getPeerAddress() +
                            "   request:" + request
                            + " response:" + response);
                }
            };
            //发送报文
            snmp.send(pdu, target, null, listener);
        }
    }

    /**
     * set PDU
     *
     * @param pduSetting pduSetting
     * @return PDU obj
     */
    private static PDU setPDU(PduSetting pduSetting) throws Exception {

        final int version = pduSetting.getVersion();

        PDU pdu = version == 3 ? new ScopedPDU() : new PDU();

        //设置要获取的对象ID
        final List<variableBindingSetting> variableBindingSettings = pduSetting.getVariableBindingSettings();
        for (variableBindingSetting v : variableBindingSettings) {
            final String variable = v.getVariable();
            final OID oid = new OID(v.getOid());
            if (variable != null && variable.length() > 0) {
                pdu.add(new VariableBinding(oid, new OctetString(variable)));
            } else {
                pdu.add(new VariableBinding(oid));
            }
        }

        //设置报文类型
        final String pduType = pduSetting.getPduType();
        if (pduType.equalsIgnoreCase("GETNEXT")) {
            pdu.setType(PDU.GETNEXT);
        } else if (pduType.equalsIgnoreCase("GET")) {
            pdu.setType(PDU.GET);
        } else if (pduType.equalsIgnoreCase("TRAP")) {
            pdu.setType(PDU.TRAP);
        } else {
            throw new Exception("pdu setType error");
        }
        return pdu;
    }

    private static Target setTarget(Address targetAddress, PduSetting pduSetting) {

        Target target;
        final int pduVersion = pduSetting.getVersion();
        if (pduVersion == 3) {
            //添加用户
            final UsmUser usmUser = pduSetting.getUsmUser();
            snmp.getUSM().addUser(
                    new OctetString(usmUser.getUserName()),
                    new org.snmp4j.security.UsmUser(
                            new OctetString(usmUser.getSecurityName()),
                            new OID(usmUser.getAuthenticationProtocol()),
                            new OctetString(usmUser.getAuthenticationProtocol()),
                            new OID(usmUser.getPrivacyProtocol()),
                            new OctetString(usmUser.getAuthenticationPassphrase())
                    )
            );

            target = new UserTarget();
            //设置安全级别
            ((UserTarget) target).setSecurityLevel(usmUser.getSecurityLevel());
            ((UserTarget) target).setSecurityName(new OctetString(usmUser.getSecurityName()));
            target.setVersion(SnmpConstants.version3);

        } else {

            target = new org.snmp4j.CommunityTarget();
            final CommunityTarget communityTarget = pduSetting.getCommunityTarget();
            final String community = communityTarget.getCommunity();

            if (pduVersion == 1) {
                target.setVersion(SnmpConstants.version1);
                ((org.snmp4j.CommunityTarget) target).setCommunity(new OctetString(community));
            } else {
                target.setVersion(SnmpConstants.version2c);
                ((org.snmp4j.CommunityTarget) target).setCommunity(new OctetString(community));
            }
        }

        // 目标对象相关设置
        target.setAddress(targetAddress);
        target.setRetries(pduSetting.getRetries());
        target.setTimeout(pduSetting.getTimeout());

        return target;
    }


}
