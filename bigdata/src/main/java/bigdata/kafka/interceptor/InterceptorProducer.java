package bigdata.kafka.interceptor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/3/421:43
 **/
public class InterceptorProducer {

    public static void main(String[] args) {
        Properties prop=new Properties();
        //Kafka集群的 brokerlist
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hdp001:9092");
        //acks的数值：0，1，-1  要写字符串类型的
        prop.put(ProducerConfig.ACKS_CONFIG,"all");
        prop.put(ProducerConfig.RETRIES_CONFIG,0);//重试次数
        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, 1000);//处理消息批次
        prop.put(ProducerConfig.LINGER_MS_CONFIG,10); //send等待某批次消息完成时间
        //消息的K-V序列化类名
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        // 2 构建拦截链  可以自定义多个
        List<String> interceptors = new ArrayList<>();
        interceptors.add("bigdata.kafka.interceptor.TimeInterceptor");
        //设置拦截链进配置中
        prop.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,interceptors);

        //实例化Kafka的Producer，需要配置对象
        Producer<String, String> producer = new KafkaProducer<String, String>(prop);
        for (int i = 0; i <100 ; i++) {
            //异步的方式发送
            producer.send(new ProducerRecord<String, String>(
            "first",Integer.toString(i),"messages"+i));
            //同步方式，在上行代码后加  .get（）；获取回调值
        }
        //关闭
        producer.close();
    }
}
