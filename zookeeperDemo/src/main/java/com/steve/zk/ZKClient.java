package com.steve.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/9/910:02
 **/
public class ZKClient {
    private static String connectString =
            "hdp001:2181,hdp002:2181,hdp003:2181";
    private static int sessionTimeout = 2000;
    private ZooKeeper zkClient = null;

    @Before
    public void init() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 收到事件通知后的回调函数（用户的业务逻辑）
                System.out.println(event.getType() + "--" + event.getPath());
                // 再次启动监听
                try {
                    zkClient.getChildren("/", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //在服务器上，使用zkCli.sh连接上集群，查看各个节点的状态和数据
    // 创建子节点
    @Test
    public void create() throws Exception {

        // 参数1：要创建的节点的路径； 参数2：节点数据 ； 参数3：节点权限 ；参数4：节点的类型
        String nodeCreated = zkClient.create("/steve", "jinlian".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    // 获取子节点
    @Test
    public void getChildren() throws Exception {

        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    // 判断znode是否存在
    @Test
    public void exist() throws Exception {
        Stat stat = zkClient.exists("/eclipse", false);
//        Stat stat = zkClient.exists("/hbase", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }








}
