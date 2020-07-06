package bigdata.storm.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2812:36
 **/
public class MyCountBolt extends BaseRichBolt {
    OutputCollector outputCollector;
    Map<String,Integer> map=new HashMap<>();
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector=outputCollector;
    }
    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getString(0);
        Integer num = tuple.getInteger(1);
        if(map.containsKey(word)){
            Integer integer = map.get(word);
            map.put(word,integer+num);
        }else {
            map.put(word,num);
        }
        System.out.println(Thread.currentThread().getName()+"  ====== "+map);
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
