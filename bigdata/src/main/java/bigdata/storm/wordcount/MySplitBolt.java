package bigdata.storm.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2812:35
 **/
public class MySplitBolt extends BaseRichBolt {
      OutputCollector outputCollector;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector=outputCollector;
    }
    @Override
    public void execute(Tuple tuple) {
        String string = tuple.getString(0);
        String[] strings = string.split(" ");
        System.err.println(Thread.currentThread().getName()+" ====== splitbolt words ");
        for (String str:strings ) {
            outputCollector.emit(new Values(str,1));
        }
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("wordKey","wordNum"));
    }
}
