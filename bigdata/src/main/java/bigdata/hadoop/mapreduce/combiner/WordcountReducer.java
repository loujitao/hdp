package bigdata.hadoop.mapreduce.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2817:22
 **/
public class WordcountReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
    int sum;
    IntWritable v=new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //累加求和，输出
        sum=0;
        for (IntWritable num: values){
            sum+=num.get();
        }
        v.set(sum);
        context.write(key,v);
    }
}
