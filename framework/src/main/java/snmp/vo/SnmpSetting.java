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

import java.util.List;

/**
 * "targetAddress": "udp:127.0.0.1/161",
 * "pdus": [
 * {
 * "version": 3,
 * "usmUser": {
 * "userName": "MD5DES",
 * "securityName": "MD5DES",
 * "authenticationProtocol": "1.3.6.1.6.3.10.1.1.2",
 * "authenticationPassphrase": "MD5DESUserAuthPassword",
 * "privacyProtocol": "1.3.6.1.6.3.10.1.2.2",
 * "privacyPassphrase": "MD5DESUserPrivPassword",
 * "SecurityLevel": 3,
 * "securityName": "MD5DES"
 * },
 * "communityTarget": null,
 * "oid": "1.3.6.1.2.1.1.1.0",
 * "pduType": "GETNEXT",
 * "retries": 1,
 * "timeout": 2000,
 * "isSynchronization": true,
 * "isBroadcast": false
 * },
 *
 * @author wei.Li by 15/2/27 (gourderwa@163.com).
 */
public class SnmpSetting {

    private String targetAddress;
    private List<PduSetting> pduSettings;
    private int version;

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public List<PduSetting> getPduSettings() {
        return pduSettings;
    }

    public void setPduSettings(List<PduSetting> pduSettings) {
        this.pduSettings = pduSettings;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SnmpSetting{" +
                "targetAddress='" + targetAddress + '\'' +
                ", pdus=" + pduSettings +
                ", version=" + version +
                '}';
    }
}

class PduSetting {
    private int version;
    private UsmUser usmUser;
    private CommunityTarget communityTarget;
    private List<variableBindingSetting> variableBindingSettings;
    private String pduType;
    private int retries;
    private int timeout;
    private boolean synchronization;
    private boolean broadcast;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public UsmUser getUsmUser() {
        return usmUser;
    }

    public void setUsmUser(UsmUser usmUser) {
        this.usmUser = usmUser;
    }

    public CommunityTarget getCommunityTarget() {
        return communityTarget;
    }

    public void setCommunityTarget(CommunityTarget communityTarget) {
        this.communityTarget = communityTarget;
    }

    public List<variableBindingSetting> getVariableBindingSettings() {
        return variableBindingSettings;
    }

    public void setVariableBindingSettings(List<variableBindingSetting> variableBindingSettings) {
        this.variableBindingSettings = variableBindingSettings;
    }

    public String getPduType() {
        return pduType;
    }

    public void setPduType(String pduType) {
        this.pduType = pduType;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isSynchronization() {
        return synchronization;
    }

    public void setSynchronization(boolean isSynchronization) {
        this.synchronization = isSynchronization;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean isBroadcast) {
        this.broadcast = isBroadcast;
    }

    @Override
    public String toString() {
        return "Pdu{" +
                ", version=" + version +
                ", usmUser=" + usmUser +
                ", communityTarget=" + communityTarget +
                ", variableBindings='" + variableBindingSettings + '\'' +
                ", pduType='" + pduType + '\'' +
                ", retries=" + retries +
                ", timeout=" + timeout +
                ", synchronization=" + synchronization +
                ", broadcast=" + broadcast +
                '}';
    }
}

class variableBindingSetting {
    private String oid;
    private String variable;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return "variableBinding{" +
                "oid='" + oid + '\'' +
                ", variable='" + variable + '\'' +
                '}';
    }
}

class CommunityTarget {
    private String community;

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    @Override
    public String toString() {
        return "CommunityTarget{" +
                "community='" + community + '\'' +
                '}';
    }
}

class UsmUser {
    private String userName;
    private String securityName;
    private String authenticationProtocol;
    private String authenticationPassphrase;
    private String privacyProtocol;
    private String privacyPassphrase;
    private int securityLevel;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getAuthenticationProtocol() {
        return authenticationProtocol;
    }

    public void setAuthenticationProtocol(String authenticationProtocol) {
        this.authenticationProtocol = authenticationProtocol;
    }

    public String getAuthenticationPassphrase() {
        return authenticationPassphrase;
    }

    public void setAuthenticationPassphrase(String authenticationPassphrase) {
        this.authenticationPassphrase = authenticationPassphrase;
    }

    public String getPrivacyProtocol() {
        return privacyProtocol;
    }

    public void setPrivacyProtocol(String privacyProtocol) {
        this.privacyProtocol = privacyProtocol;
    }

    public String getPrivacyPassphrase() {
        return privacyPassphrase;
    }

    public void setPrivacyPassphrase(String privacyPassphrase) {
        this.privacyPassphrase = privacyPassphrase;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Override
    public String toString() {
        return "UsmUser{" +
                "userName='" + userName + '\'' +
                ", securityName='" + securityName + '\'' +
                ", authenticationProtocol='" + authenticationProtocol + '\'' +
                ", authenticationPassphrase='" + authenticationPassphrase + '\'' +
                ", privacyProtocol='" + privacyProtocol + '\'' +
                ", privacyPassphrase='" + privacyPassphrase + '\'' +
                ", securityLevel=" + securityLevel +
                '}';
    }
}