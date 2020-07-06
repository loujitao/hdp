package bigdata.hadoop.mapreduce.groupingComparator;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//现在需要求出每一个订单中最贵的商品。 一个订单有多个商品
//  订单id	商品id	成交金额
//0000001	Pdt_01	222.8
//0000002	Pdt_05	722.4
//0000001	Pdt_02	33.8
//0000003	Pdt_06	232.8
//0000003	Pdt_02	33.8
//0000002	Pdt_03	522.8
//0000002	Pdt_04	122.4
public class OrderDriver {

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

    public static void main(String[] args) throws Exception, IOException {

// 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args  = new String[]{"E:\\bigdataWork\\hadoop\\groupComparatorin" , "E:\\bigdataWork\\hadoop\\groupComparatorout"};
        // 1 获取配置信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        // 2 设置jar包加载路径
        job.setJarByClass(OrderDriver.class);
        // 3 加载map/reduce类
        job.setMapperClass(OrderMapper.class);
        job.setReducerClass(OrderReducer.class);
        // 4 设置map输出数据key和value类型
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);
        // 5 设置最终输出数据的key和value类型
        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);
        // 6 设置输入数据和输出数据路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 8 设置reduce端的分组
        job.setGroupingComparatorClass(OrderGroupingComparator.class);

        // 7 提交
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}