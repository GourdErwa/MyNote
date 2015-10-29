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

package jasperreport;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * @author wei.Li by 14/10/11.
 */
public class Es_Jdbc {

    private Client client;

   /* public Es_Jdbc(Client client) {
        this.client = client;
    }
*/

    /**
     * startup Transport Client
     * 启动es
     */
    protected void startupClient() {
        /**
         * 可以设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
         * 这样做的好 处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器。
         */
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.sniff", true).build();
        client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("192.168.1.233", 9300));
        //.addTransportAddress(new InetSocketTransportAddress("10.211.55.4", 9300));
    }

   /* private void writeData() throws JRException {
        //File reportFile = new File("jasper/sample.jasper");
        //JasperExportManager.exportReportToXmlFile("/lw/sample.jrxml", false);
        String jrxmlPath = "/lw/sample.jrxml";
        JasperReport report = JasperCompileManager.compileReport(jrxmlPath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report,
                null);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "/lw/a.html");

        *//*Digester digester = new Digester();
        digester.setClassLoader(ClassLoader.getSystemClassLoader());*//*
    }

    public static void main(String[] args) throws JRException {
        Es_Jdbc es_jdbc = new Es_Jdbc();
        es_jdbc.writeData();

    }*/


}
