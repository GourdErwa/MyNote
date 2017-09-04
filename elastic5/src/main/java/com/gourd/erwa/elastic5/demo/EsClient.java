package com.gourd.erwa.elastic5.demo;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wei.Li by 2017/4/10
 */
class EsClient {

    private static TransportClient client;

    static Client conn() {

        synchronized (EsClient.class) {
            if (client != null) {
                return client;
            }
        }

        Settings esSettings = Settings.builder()

                .put("cluster.name", "ktcs-bi-cluster") //设置ES实例的名称
                //.put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .build();


        // on startup
        try {
            client = new PreBuiltTransportClient(esSettings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("180.76.152.158"), 9301))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("180.76.151.161"), 9302))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("180.76.151.161"), 9303));

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }



        /*this.client = new PreBuiltTransportClient(esSettings);*/

        return client;
    }


    public static void main(String[] args) {
        System.out.println(conn().admin().cluster().prepareClusterStats().get().getNodesStats().getVersions());
    }

}
