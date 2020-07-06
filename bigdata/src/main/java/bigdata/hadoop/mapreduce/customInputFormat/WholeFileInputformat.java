package bigdata.hadoop.mapreduce.customInputFormat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2819:20
 **/
// 定义类继承FileInputFormat
public class WholeFileInputformat extends FileInputFormat<Text, BytesWritable> {

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    @Override
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context)	throws IOException, InterruptedException {

        WholeRecordReader recordReader = new WholeRecordReader();
        recordReader.initialize(split, context);

        return recordReader;
    }
}
