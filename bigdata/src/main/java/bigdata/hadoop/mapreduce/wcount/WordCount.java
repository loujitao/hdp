package bigdata.hadoop.mapreduce.wcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2817:15
 **/
public class WordCount {
    static {
        try {
            //报错，找不到加载驱动，添加此静态代码块
            System.load("E:\\hadoop-2.7.2\\bin\\hadoop.dll");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        //获取配置信息以及封装任务
        Configuration config=new Configuration();
        //是否运行为本地模式，就是看这个参数值是否为local，默认就是local
//		 conf.set("mapreduce.framework.name", "local");
        //本地模式运行mr程序时，输入输出的数据可以在本地，也可以在hdfs上
        //到底在哪里，就看以下两行配置你用哪行，默认就是file:///
		/*conf.set("fs.defaultFS", "hdfs://mini1:9000/");*/
		/*conf.set("fs.defaultFS", "file:///");*/
        Job job = Job.getInstance(config);

        //设置jar加载路径
        job.setJarByClass(WordCount.class);

        //设置map和reduce类
        job.setMapperClass(WordcountMapper.class);
        job.setReducerClass(WordcountReducer.class);

        //设置map的输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //设置reduce的输出  最终输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //指定job的输入原始文件所在目录
//		FileInputFormat.setInputPaths(job, new Path(args[0]));
        //指定job的输出结果所在目录
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
        FileInputFormat.setInputPaths(job, new Path("E:\\bigdataWork\\hadoop\\wordCountinput"));
        FileOutputFormat.setOutputPath(job, new Path("E:\\bigdataWork\\hadoop\\wordCountOutput"));

        //提交
        boolean flag=job.waitForCompletion(true);
        System.exit(flag?0:1);
    }
}
