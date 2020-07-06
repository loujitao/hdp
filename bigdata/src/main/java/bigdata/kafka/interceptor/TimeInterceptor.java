package bigdata.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/3/422:29
 **/
public class TimeInterceptor implements ProducerInterceptor<String,String> {
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        //ProducerRecord没有set的方法去修改值，而且这里不能返回null，否则影响消息发送
        // 创建一个新的record，把时间戳写入消息体的最前部
        return new ProducerRecord(record.topic(), record.partition(),
                    record.timestamp(),
                    record.key(),
                System.currentTimeMillis() + "," + record.value().toString());
    }

    private int errorCounter = 0;
    private int successCounter = 0;
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
// 统计成功和失败的次数
        if (exception == null) {
            successCounter++;
        } else {
            errorCounter++;
        }
    }

    @Override
    public void close() {
        // 保存结果  producer关闭的时候调用，这里统计结果输出
        System.out.println("Successful sent: " + successCounter);
        System.out.println("Failed sent: " + errorCounter);
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
