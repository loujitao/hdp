package bigdata.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/3/422:05
 **/
public class CustomConsumer {

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"hdp001:9092");
        //消费者属于的消费者组ID   相同的ID属于相关的消费者组
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"test");
        //自动提交offset  默认打开
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        //反序列化类 K-V
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);
        while (true){
            //设置定义的topics  可设置多个
            consumer.subscribe(Arrays.asList("first"));
            //多久取一次数据 ms
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record:records) {
                System.out.println("offset: "+record.offset()+" key: "+record.key()
                +" value: "+record.value());
            }
            //同步提交offset 一直尝试
            consumer.commitSync();
            //异步提交，不保证成功
//            consumer.commitAsync();
        }
    }
}
