package com.steve.stream;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/*
     将每一条日志（一行数据）尾部添加一串“！！！”
 */
public class LoggerProcessor  implements Processor<byte[],byte[]>{
    //将上下文对象提出来
    private ProcessorContext context;

    public void init(ProcessorContext context) {
        this.context = context;
    }

    //编写处理流程    key  value:这里就是需要处理的数据
    public void process(byte[] key, byte[] value) {
        String line = new String(value);
        line = line+"!!!";
        byte[] newValue = line.getBytes();
        context.forward(key,newValue);
    }

    public void punctuate(long timestamp) {

    }

    public void close() {
    }
}
