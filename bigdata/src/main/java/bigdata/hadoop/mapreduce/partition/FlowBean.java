package bigdata.hadoop.mapreduce.partition;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Description:
 *自定义bean对象实现序列化接口（Writable）
 * （1）必须实现Writable接口
 * （2）反序列化时，需要反射调用空参构造函数，所以必须有空参构造
 * （3）重写序列化方法
 * （4）重写反序列化方法
 * （5）注意反序列化的顺序和序列化的顺序完全一致
 * （6）要想把结果显示在文件中，需要重写toString()，可用”\t”分开，方便后续用。
 * （7）如果需要将自定义的bean放在key中传输，则还需要实现Comparable接口，因为MapReduce框中的Shuffle过程要求对key必须能排序。
 * @Author: SteveTao
 * @Date: 2020/2/2818:16
 **/
// 1 实现writable接口
public class FlowBean implements Writable{
//    统计每一个手机号耗费的总上行流量、下行流量、总流量
        private long upFlow;
        private long downFlow;
        private long sumFlow;

        //2  反序列化时，需要反射调用空参构造函数，所以必须有
        public FlowBean() {
            super();
        }

    public FlowBean(long upFlow, long downFlow) {
        super();
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    //方便一次性设置值
        public void set(long upFlow, long downFlow) {
            this.upFlow = upFlow;
            this.downFlow = downFlow;
            this.sumFlow = upFlow + downFlow;
        }

        //3  写序列化方法
        @Override
        public void write(DataOutput out) throws IOException {
            out.writeLong(upFlow);
            out.writeLong(downFlow);
            out.writeLong(sumFlow);
        }

        //4 反序列化方法
        //5 反序列化方法读顺序必须和写序列化方法的写顺序必须一致
        @Override
        public void readFields(DataInput in) throws IOException {
            this.upFlow  = in.readLong();
            this.downFlow = in.readLong();
            this.sumFlow = in.readLong();
        }

        // 6 编写toString方法，方便后续打印到文本
        @Override
        public String toString() {
            return upFlow + "\t" + downFlow + "\t" + sumFlow;
        }

        public long getUpFlow() {
            return upFlow;
        }

        public void setUpFlow(long upFlow) {
            this.upFlow = upFlow;
        }

        public long getDownFlow() {
            return downFlow;
        }

        public void setDownFlow(long downFlow) {
            this.downFlow = downFlow;
        }

        public long getSumFlow() {
            return sumFlow;
        }

        public void setSumFlow(long sumFlow) {
            this.sumFlow = sumFlow;
        }
    }