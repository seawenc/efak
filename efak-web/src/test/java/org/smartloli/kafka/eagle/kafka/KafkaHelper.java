package org.smartloli.kafka.eagle.kafka;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaHelper {
    /**
     * 获得kafka的配置,包含groupId
     * @return Properties
     * @throws Exception 异常
     */
    public static synchronized Properties getKafkaConf() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("acks", "all");
        properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("max.request.size","10485760");

//        setCqrdKvmKafka(properties);
//        setLocalKafka(properties);
        setChanglongKafka(properties);
//        setCqrdKafka(properties);
        System.out.println("......... kafka props:"+JSON.toJSONString(properties));
        return properties;
    }

    private static void setCqrdKvmKafka(Properties properties){
//        properties.setProperty("sasl.mechanism", "SCRAM-SHA-256");
        properties.setProperty("bootstrap.servers", "172.26.23.192:9092,172.26.23.193:9092,172.26.23.194:9092");
        properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"msg\" password=\"oIKbAjOV\";");
    }

    private static void setCqrdKafka(Properties properties){
//        properties.setProperty("sasl.mechanism", "SCRAM-SHA-256");
        properties.setProperty("bootstrap.servers", "kafkan0.b.x:31090,kafkan1.b.x:31091,kafkan2.b.x:31092");
//        properties.setProperty("sasl.mechanism", "PLAIN");
//        properties.setProperty("security.protocol", "Plaintext");
//        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"msg\" password=\"oIKbAjOV\";");
    }

    private static void setLocalKafka(Properties properties){
        properties.setProperty("bootstrap.servers", "192.168.56.10:9092,192.168.56.11:9092,192.168.56.12:9092");
        properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"aaBB1122\";");
    }

    private static void setChanglongKafka(Properties properties){
        properties.setProperty("bootstrap.servers", "10.1.132.132:9093,10.1.132.133:9093,10.1.132.134:9093");
        properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"aaBB@1122\";");
    }
}