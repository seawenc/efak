package org.smartloli.kafka.eagle.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * kafka消息消费者测试
 */
public class KafkaConsumer {
    public static void main(String[] args) throws Exception {
        String topics="AIC_HSD_GJ_M";
        String groupId="group3";
        Properties properties = KafkaHelper.getKafkaConf();
        //earliest:offset偏移至最早时候开始消费；latest：偏移到从最新开始消费（默认）
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        properties.setProperty("group.id",groupId);
        properties.setProperty("auto.offset.reset","earliest");
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topics.split(",")));
        System.out.println("kafka完整参数：");
        properties.forEach((k,v)-> System.out.println(k+"="+v));
        SimpleDateFormat sdf =new SimpleDateFormat("MM-dd HH:mm:ss");
        while (true) {
            Thread.sleep(100);
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String value = record.value();

                System.out.println(String.format(sdf.format(new Date(record.timestamp()))+" offset = %d, partition = %s, value = %s%n", record.offset(),record.partition(), value));
            }
        }
    }
}