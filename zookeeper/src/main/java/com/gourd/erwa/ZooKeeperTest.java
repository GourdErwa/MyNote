package com.gourd.erwa;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author wei.Li
 */
public class ZooKeeperTest {

    private static final int TIME_OUT = 2000;
    private static final String HOST = "localhost:9898";
    private static final String PATH = "/lw/temp/ZooKeeper";

    public static void main(String[] args) throws Exception {

        final ZooKeeper zookeeper = new ZooKeeper(HOST, TIME_OUT, null);
        Thread.sleep(5000L);
        final ZooKeeper.States state = zookeeper.getState();
        System.out.println(state);

        System.out.println("=========创建节点===========");
        if (zookeeper.exists(PATH, false) == null) {
            zookeeper.create(PATH, "node1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("=============查看节点是否安装成功===============");
        System.out.println(new String(zookeeper.getData(PATH, false, null)));

        System.out.println("=========修改节点的数据==========");
        String data = "zNode2";
        zookeeper.setData(PATH, data.getBytes(), -1);

        System.out.println("========查看修改的节点是否成功=========");
        System.out.println(new String(zookeeper.getData(PATH, false, null)));

        System.out.println("=======删除节点==========");
        zookeeper.delete(PATH, -1);

        System.out.println("==========查看节点是否被删除============");
        System.out.println("节点状态：" + zookeeper.exists(PATH, false));

        zookeeper.close();
    }

}
