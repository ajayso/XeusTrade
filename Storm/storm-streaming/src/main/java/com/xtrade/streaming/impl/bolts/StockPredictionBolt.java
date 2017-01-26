package com.xtrade.streaming.impl.bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import com.google.common.primitives.Doubles;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class StockPredictionBolt implements IRichBolt {

  private static final Logger LOG = Logger.getLogger(StockPredictionBolt.class);
  private String rModelPath;


  private Properties topologyConfig;

  private OutputCollector collector;
 


  public StockPredictionBolt(Properties topologyConfig) {
    this.topologyConfig = topologyConfig;
  }


  public void prepare(Map stormConf, TopologyContext context,
                      OutputCollector collector) {
    this.collector = collector;
   
  }


  public void execute(Tuple input) {

    LOG.info("Entered prediction bolt execute...");
    collector.ack(null);

  }



  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("prediction", "driverName",
        "routeName", "driverId", "truckId", "timeStamp",
        "longitude", "latitude", "certified",
        "wagePlan", "hours_logged", "miles_logged",
        "isFoggy", "isRainy", "isWindy"));
  }


  public Map<String, Object> getComponentConfiguration() {
    return null;
  }


  public void cleanup() {
  }


}




