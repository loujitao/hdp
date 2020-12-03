package com.steve.stream;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;

import java.util.Properties;

/**
 * 客户端通过订阅Kafka收集的数据，然后由Kafka Streams程序可以简单的进行实时数据的分析。
 例如：通过用户访问日志、系统日志数据可以很方便的实时统计出PV/UV以及实现日志监控报警系统等；
 *
 *
 * 简单的使用，利用KafkaStream做简单的日志清洗
 * 实例：
 *      将每一条日志（一行数据）尾部添加一串“！！！”
 *
 */
public class KafkaStream {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"log-stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.104:9092");

        KStreamBuilder builder = new KStreamBuilder();
        //构建拓扑流的结构    输入源头
        builder.addSource("dataInput","flumLog");
        //中间数据处理流程   指定多个数据来源source
        builder.addProcessor("addLog", new ProcessorSupplier() {
            public Processor get() {
                //需要直接实现一个处理类
                return new LoggerProcessor();
            }
        }, "flumLog");
        //输出
        builder.addSink("dataOutput","logger","addLog");

        KafkaStreams kafkaStreams = new KafkaStreams(builder, props);

        kafkaStreams.start();
        /*
            1、启动一个消费者，监听logger主题
            2、启动该程序；
            3、用生产者往Kafka的flumLog主题下发送原始数据；
            4、观察logger下的数据跟原始数据的差别
         */
    }
}
