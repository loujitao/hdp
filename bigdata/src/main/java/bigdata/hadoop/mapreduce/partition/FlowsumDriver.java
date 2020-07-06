package bigdata.hadoop.mapreduce.partition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Description: 手机号136、137、138、139开头都分别放到一个独立的4个文件中，其他开头的放到一个文件中。
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
        args = new String[] { "E:\\bigdataWork\\hadoop\\flowin", "E:\\bigdataWork\\hadoop\\flowout2" };

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

        // 8 指定自定义数据分区
        job.setPartitionerClass(ProvincePartitioner.class);
        // 9 同时指定相应数量的reduce task
        job.setNumReduceTasks(5);
/**
 * 分区规则，默认为根据key的hashcode / reduceTask，哈希取模
 *1)如果reducetask的数量 > getPartition的结果数，则会多产生几个空的输出文件part-000*
 * 2)如果1 <  reducetask数量 < getpartition的结果数，则会有部分数据找不到对应的reducetask，报错！！
 * 3)如果reducetask数量为1，则不管分区文件有多少，分区数为几，都会汇总到这个reducetask下，最终
 * 只会生成一个part-00000文件
 * 4)分区号必须从0开始，逐一累加
 */
        // 5 指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7 将job中配置的相关参数，以及job所用的java类所在的jar包， 提交给yarn去运行
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
