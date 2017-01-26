package com.xtrade.streaming.impl.topologies;


import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import com.xtrade.streaming.impl.bolts.*;
import com.xtrade.streaming.impl.bolts.hdfs.FileTimeRotationPolicy;
import org.apache.log4j.Logger;
import org.apache.storm.hdfs.bolt.HdfsBolt;
import org.apache.storm.hdfs.bolt.format.DefaultFileNameFormat;
import org.apache.storm.hdfs.bolt.format.DelimitedRecordFormat;
import org.apache.storm.hdfs.bolt.format.FileNameFormat;
import org.apache.storm.hdfs.bolt.format.RecordFormat;
import org.apache.storm.hdfs.bolt.sync.CountSyncPolicy;
import org.apache.storm.hdfs.bolt.sync.SyncPolicy;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.StringScheme;


public class StockEventProcessorKafka extends StockEventTopology {

  private static final Logger LOG = Logger.getLogger(StockEventProcessorKafka.class);


  public StockEventProcessorKafka(String configFileLocation) throws Exception {
    super(configFileLocation);
  }

  public static void main(String[] args) throws Exception {
    String configFileLocation = args[0];
    StockEventProcessorKafka stockTopology = new StockEventProcessorKafka(configFileLocation);
    stockTopology.buildAndSubmit();
  }

  public void buildAndSubmit() throws Exception {
    TopologyBuilder builder = new TopologyBuilder();

		/* Set up Kafka Spout to ingest from */
    configureKafkaSpout(builder);
		/* Setup Prediction Bolt for calling ML model */
    configureStockBolt(builder);
		/* Setup Prediction Web Socket Bolt for prediction alerts */
    configureStockPredictionBolt(builder);
    Config conf = new Config();
    conf.setDebug(true);
		/* Set the number of workers that will be spun up for this topology.
		 * Each worker represents a JVM where executor thread will be spawned from */
    Integer topologyWorkers = 2 ;//Integer.valueOf(topologyConfig.getProperty("storm.stocks.topology.workers"));
    conf.put(Config.TOPOLOGY_WORKERS, topologyWorkers);

    //Read the nimbus host in from the config file as well
    String nimbusHost = topologyConfig.getProperty("nimbus.host");
    conf.put(Config.NIMBUS_HOST, nimbusHost);

    try {
      StormSubmitter.submitTopology("stock-event-processor", conf, builder.createTopology());
    } catch (Exception e) {
      LOG.error("Error submiting Topology", e);
    }

  }

 

  public int configureStockPredictionBolt(TopologyBuilder builder) {
	    // Check config and choose the appropriate prediction bolt
		  
		  builder.setBolt("stock-prediction-bolt", new StockPredictionBolt(topologyConfig), 1).shuffleGrouping("stock-bolt");
		  int boltCount = Integer.valueOf(topologyConfig.getProperty("bolt.thread.count"));
	      return boltCount;
	  }


  public int configureStockBolt(TopologyBuilder builder) {
    // Check config and choose the appropriate prediction bolt
	  
	  builder.setBolt("stock-bolt", new StockEventBolt(topologyConfig), 1).shuffleGrouping("kafkaSpout");
	  int boltCount = Integer.valueOf(topologyConfig.getProperty("bolt.thread.count"));
      return boltCount;
  }

    
  public int configureKafkaSpout(TopologyBuilder builder) {
    KafkaSpout kafkaSpout = constructKafkaSpout();

    int spoutCount = Integer.valueOf(topologyConfig.getProperty("spout.thread.count"));
    int boltCount = Integer.valueOf(topologyConfig.getProperty("bolt.thread.count"));

    builder.setSpout("kafkaSpout", kafkaSpout, spoutCount);
    return boltCount;
  }

  private KafkaSpout constructKafkaSpout() {
    KafkaSpout kafkaSpout = new KafkaSpout(constructKafkaSpoutConf());
    return kafkaSpout;
  }

  private SpoutConfig constructKafkaSpoutConf() {
    BrokerHosts hosts = new ZkHosts(topologyConfig.getProperty("kafka.zookeeper.host.port"));
    String topic = "single-stock-event";
    String zkRoot = topologyConfig.getProperty("kafka.zkRoot");
    String consumerGroupId = topologyConfig.getProperty("kafka.consumer.group.id");

    SpoutConfig spoutConfig = new SpoutConfig(hosts, topic, zkRoot, consumerGroupId);

	spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

    return spoutConfig;
  }

}





