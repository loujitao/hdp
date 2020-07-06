package bigdata.hadoop.mapreduce.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2817:16
 **/
public class WordcountMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
        Text k=new Text();
        IntWritable v=new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1、获取一行数据
         String line=value.toString();
         //2、切割
        String[] strings = line.split(" ");
        //3、输出
        for (String str: strings){
            k.set(str);
            context.write(k,v);
        }
    }
}
