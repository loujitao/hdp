package com.steve.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class wc {

    public static void main(String[] args) throws Exception{

        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();

        String filePath="D:\\ideaWork\\hdp\\javaFlink\\src\\main\\resources\\wordcount.txt";
//        String filePath="wordcount.txt";

        DataSource<String> textFile = environment.readTextFile(filePath);

        DataSet<Tuple2<String, Integer>> sum = textFile.flatMap(new MyFlatMapper())
                .groupBy(0)
                .sum(1);

        sum.print();
    }

    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String,
                Integer>> {
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws
                Exception {
            String[] words = value.split(" ");
            for (String word : words) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }


}
