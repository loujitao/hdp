package bigdata.hadoop.mapreduce.customInputFormat;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2819:24
 **/
public class SequenceFileMapper extends Mapper<Text, BytesWritable, Text, BytesWritable> {

    @Override
    protected void map(Text key, BytesWritable value, Context context)
            throws IOException, InterruptedException {
        context.write(key, value);
    }
}