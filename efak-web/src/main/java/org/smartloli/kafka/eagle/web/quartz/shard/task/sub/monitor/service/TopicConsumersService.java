package org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.smartloli.kafka.eagle.common.protocol.DisplayInfo;
import org.smartloli.kafka.eagle.common.protocol.OffsetInfo;
import org.smartloli.kafka.eagle.common.protocol.offsets.TopicOffsetInfo;
import org.smartloli.kafka.eagle.common.util.SystemConfigUtils;
import org.smartloli.kafka.eagle.web.controller.StartupListener;
import org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.bean.ConsumerInfo;
import org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.bean.MonitorOffsetInfo;
import org.smartloli.kafka.eagle.web.service.ConsumerService;
import org.smartloli.kafka.eagle.web.service.OffsetService;

import java.util.List;
import java.util.stream.Collectors;

public class TopicConsumersService {

    String[] clusterAliass = SystemConfigUtils.getPropertyArray("efak.zk.cluster.alias", ",");

    ConsumerService consumerService=StartupListener.getBean("consumerServiceImpl", ConsumerService.class);
    OffsetService offsetService=StartupListener.getBean("offsetServiceImpl", OffsetService.class);

    public List<ConsumerInfo> findAllConsumersDetail(String topic){
        List<ConsumerInfo> allConsumers = findAllConsumers();

        for(ConsumerInfo consumer:allConsumers){
            TopicOffsetInfo topicOffset = new TopicOffsetInfo();
            topicOffset.setCluster(clusterAliass[0]);
            topicOffset.setFormatter("kafka");
            topicOffset.setGroup(consumer.getGroup());
            topicOffset.setPageSize(500);
            topicOffset.setStartPage(0);
            topicOffset.setTopic(topic);
            List<OffsetInfo> logSizes = offsetService.getConsumerOffsets(topicOffset);
            consumer.setOffsets(logSizes.stream().map(offset-> new MonitorOffsetInfo(offset)).collect(Collectors.toList()));
        }
        return allConsumers;
    }

    private List<ConsumerInfo> findAllConsumers() {
        DisplayInfo page = new DisplayInfo();
        page.setiDisplayLength(500);
        page.setiDisplayStart(0);
        page.setSearch("");
        JSONArray consumers = JSON.parseArray(consumerService.getConsumer(clusterAliass[0], "kafka", page));
        List<ConsumerInfo> groupInfos = (List<ConsumerInfo>) consumers.stream().map(row -> new ConsumerInfo((JSONObject) row)).collect(Collectors.toList());
        return groupInfos;
    }
}
