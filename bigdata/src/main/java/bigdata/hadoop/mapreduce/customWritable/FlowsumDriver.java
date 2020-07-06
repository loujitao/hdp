package bigdata.hadoop.mapreduce.customWritable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2818:27
 **/
public class FlowsumDriver {
    static {
        try {
            //拷贝到 C:\Windows\System32 如果不生效，可使用该办法
            //报错，找不到加载驱动，添加此静态代码块
            System.load("E:\\hadoop-2.7.2\\bin\\hadoop.dll");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {

// 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args = new String[] { "E:\\bigdataWork\\hadoop\\flowin", "E:\\bigdataWork\\hadoop\\flowout" };

        // 1 获取配置信息，或者job对象实例
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        // 6 指定本程序的jar包所在的本地路径
        job.setJarByClass(FlowsumDriver.class);

        // 2 指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);

        // 3 指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        // 4 指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

//FileInputFormat实现类:
//       TextInputFormat:  整个文本的起始偏移量字节作Key, v为该行数据
//       KeyValueInputFormat：每行均为一条数据，按照分隔符分为key  value，默认为\t制表符分割
//       NLineInputFormat： 按照行数N划分切片，给maptask处理，不在按照文件大小分片。 分片= 总行数 / N ，有余+1
//      CombineTextInputFormat： 根据虚拟存储值划分切片，需要设置一个字节单位的数值。 将输入文件按此值整合或切分
// 输入数据：准备4个小文件         期望一个切片处理4个文件
// 如果不设置InputFormat，它默认用的是TextInputFormat.class
//job.setInputFormatClass(CombineTextInputFormat.class);
//虚拟存储切片最大值设置4m, 可以根据文件总大小调整该值
//CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);

        // 5 指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7 将job中配置的相关参数，以及job所用的java类所在的jar包， 提交给yarn去运行
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
