package bigdata.storm.wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * @Description:
 * 运行结果，会有四个线程跑countBolt, spout和split个两个
 * @Author: SteveTao
 * @Date: 2020/2/2812:27
 **/
public class WordCount {

    public static void main(String[] args) {
        //1、拓扑创建类
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        //指定spout和并发度
        topologyBuilder.setSpout("mySpout",new MySpout(),2);
        //指定切割句子的bolt,指定并发度
        topologyBuilder.setBolt("splitBolt",new MySplitBolt(),2)
                .shuffleGrouping("mySpout"); //指定接受数据来源的spout，以及接收分组策略
        //指定统计单词的bolt，指定并发度
        topologyBuilder.setBolt("countBolt",new MyCountBolt(),4)
                .fieldsGrouping("splitBolt",new Fields("wordKey"));
        //指定数据来源的bolt。 fieldsGrouping根据单词hash取模分组，所以还要指定field具体是哪个

        //2、创建一个configuration，用来指定当前topology 需要的worker的数量
        Config config=new Config();
        config.setNumWorkers(2);

        //3、提交任务  -----两种模式 本地模式和集群模式
//        StormSubmitter.submitTopology("mywordcount",config,topologyBuilder.createTopology());
        LocalCluster cluster=new LocalCluster( );
        cluster.submitTopology("myWordCount",config,topologyBuilder.createTopology());
    }
}
