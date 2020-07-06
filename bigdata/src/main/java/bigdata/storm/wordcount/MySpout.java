package bigdata.storm.wordcount;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: SteveTao
 * @Date: 2020/2/2812:31
 **/
public class MySpout extends BaseRichSpout{
    SpoutOutputCollector spoutOutputCollector;
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
       this.spoutOutputCollector=spoutOutputCollector;
    }
    @Override
    public void nextTuple() {
        spoutOutputCollector.emit(new Values("hello world i am steve"));
        System.out.println(Thread.currentThread().getName()+" ====== spout ");
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("words"));
    }
}
