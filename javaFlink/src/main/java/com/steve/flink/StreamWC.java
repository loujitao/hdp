package com.steve.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class StreamWC {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        ParameterTool fromArgs = ParameterTool.fromArgs(args);
//        String host = fromArgs.get("host");
//        int port = fromArgs.getInt("port");

        String host = "172.18.168.10";
        int port = 7777;

        DataStreamSource<String> streamSource = environment.socketTextStream(host,port);

        DataStream<Tuple2<String, Integer>> sum = streamSource.flatMap(new wc.MyFlatMapper())
                .keyBy(0)
                .sum(1);

        sum.print();

        environment.execute();
    }

}
