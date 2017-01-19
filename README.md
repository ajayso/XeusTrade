"# XeusTrade" 

XeusTrade is experimental code for Stock Trading with Fundamental Analysis.

In a fast pace world, Analysis on the fly requires a very different architecture. Most of the current architecture involve stored datawarehouses and use conventional methods for business intelligence. 
Moving into the zone of utlra low latency decision making requires a very different architecture and set of technologies. We are talking of real time business analytics and decision making with latencies in the order of milli or probably micro seconds.

Apache Kafka -is used for building real-time data pipelines and streaming apps. It is horizontally scalable, fault-tolerant, wicked fast, and runs in production in thousands of companies.

Apache - Storm is a free and open source distributed realtime computation system. Storm makes it easy to reliably process unbounded streams of data, doing for realtime processing what Hadoop did for batch processing. 
It is scalable, fault-tolerant, guarantees your data will be processed, and is easy to set up and operate.

Storm integrates with the queueing and database technologies you already use. A Storm topology consumes streams of data and processes those streams in arbitrarily complex ways, repartitioning the streams between each stage of the computation however needed. 

For starters we are going to build a basic Storm Kafka integration similar to what one would expect in Stock Trading Fundamental Analysis. Use Apache Nifi supports powerful and scalable directed graphs of data routing, transformation, and system mediation logic.
NiFi works with Apache Kafka, Apache Storm, Apache HBase and Spark for real-time distributed messaging of streaming data. NiFi is an excellent platform for ingesting real-time streaming data sources, such as the internet of things, sensors and transactional systems. If the data that comes in is garbage data, NiFi offers tools to filter out the undesired data. Additionally, NiFi can also act as a messenger and send data to the desired location.

As we go further in code we incorporate R models for fundamental stock prediction which will be invoked by Storm.

A Small Web front end which stiches all the pieces together and show how things work together.

Thank you,
Ajay Solanki