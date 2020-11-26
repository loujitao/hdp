package com.steve.flume;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: flume自定义拦截器
 * @Author: SteveTao
 * @Date: 2020/9/2115:29
 * 参考： https://blog.csdn.net/jinywum/article/details/82598947
 **/
public class AddLogInterceptor implements Interceptor {
//    public static Logger log = Logger.getLogger(AddLogInterceptor.class);
    public static final Map<String,String> header=new HashMap<>(8);
        @Override
        public void initialize() {
//            log.info("=== initialize() ====");
        }

        @Override
        public void close() {
//            log.info("=== close() ====");
        }

        /**
         * 拦截source发送到通道channel中的消息
         *
         * @param event 接收过滤的event
         * @return event    根据业务处理后的event
         */
        @Override
        public Event intercept(Event event) {
//            if('1'<=event.getBody()[0]&&event.getBody()[0]<='9'){
//                event.getHeaders().put("topic","number");
//            }else if('a'<=event.getBody()[0]&&event.getBody()[0]<='z'){
//                event.getHeaders().put("topic","letter");
//            }
            event.getHeaders().put("dataType","logs");
            return event;
        }
        // 接收被过滤事件集合
        @Override
        public List<Event> intercept(List<Event> events) {
            List<Event> list = new ArrayList<>();
            for (Event event : events) {
                list.add(intercept(event));
            }
            return list;
        }

        public static class Builder implements Interceptor.Builder {
            // 获取配置文件的属性
            @Override
            public Interceptor build() {
                return new AddLogInterceptor();
            }

            @Override
            public void configure(Context context) {

            }
        }
}
