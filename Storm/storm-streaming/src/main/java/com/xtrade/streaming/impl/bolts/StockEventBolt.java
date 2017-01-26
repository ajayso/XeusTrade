/**
 * Created by jmedel on 8/16/16.
 */

package com.xtrade.streaming.impl.bolts;

import org.apache.storm.task.TopologyContext;
//import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.task.OutputCollector;
//import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.log4j.Logger;
import java.util.Properties;
import java.util.Map;

import static org.apache.storm.utils.Utils.tuple;

public class StockEventBolt extends BaseRichBolt {

    private static final Logger LOG = Logger.getLogger(StockEventBolt.class);

    private OutputCollector outputCollector;
    
    public StockEventBolt(Properties topologyConfig) {
          LOG.info("The Stock Event Bolt");
      }

    @SuppressWarnings("rawtypes")
    public void prepare(Map stormConf, TopologyContext context, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    // Output driverId with a count of 1
    // HBase will increment the counter "incidentTotalCount
    public void execute(Tuple input) {
        LOG.info("Stock Id = " + input.getValue(0));
        this.outputCollector.emit(tuple(input.getValue(0), 1));
    }

    public void cleanup() {

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("BuySellFlag", "incidentBuySellFlag"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
