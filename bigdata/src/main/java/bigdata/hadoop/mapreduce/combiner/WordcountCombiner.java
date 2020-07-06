package bigdata.hadoop.mapreduce.combiner;

/**
 * @Description:
 * 1)Combiner组件的父类就是Reducer
 * 2)跟reducer的区别在于运行位置
 *      combiner是在每一个MapTask所在的节点运行；
 *      Reducer是接受全局所有Mapper的输出结果
 * 3）combiner的意义就是对每一个maptask的输出进行局部汇总，以减少网络传输
 *4）combiner能够使用的业务前提是，不能影响最终的业务逻辑。而且combiner的输出要和
 *      reducer的输入KV类型对应起来
 * @Author: SteveTao
 * @Date: 2020/2/2820:27
 **/
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordcountCombiner extends Reducer<Text, IntWritable, Text, IntWritable>{

    IntWritable v = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // 1 汇总
        int sum = 0;
        for(IntWritable value :values){
            sum += value.get();
        }
        v.set(sum);
        // 2 写出
        context.write(key, v);
    }
}