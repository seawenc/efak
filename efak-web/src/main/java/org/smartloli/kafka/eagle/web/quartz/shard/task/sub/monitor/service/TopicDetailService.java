package org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.service;

import org.smartloli.kafka.eagle.common.protocol.MetadataInfo;
import org.smartloli.kafka.eagle.common.protocol.PartitionsInfo;
import org.smartloli.kafka.eagle.common.util.SystemConfigUtils;
import org.smartloli.kafka.eagle.web.controller.StartupListener;
import org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.bean.TopicDetailInfo;
import org.smartloli.kafka.eagle.web.service.TopicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.MonitorTask.TOPIC_MONITOR;

/**
 * topic信息
 */
public class TopicDetailService {

    String[] clusterAliass = SystemConfigUtils.getPropertyArray("efak.zk.cluster.alias", ",");

    TopicService topicService = StartupListener.getBean("topicServiceImpl", TopicService.class);
    TopicConsumersService consumersService=new TopicConsumersService();

    /**
     * 获得topic明细信息
     * @return
     */
    public List<TopicDetailInfo> findAllTopicsDetailInfo(){
        // 生成基本信息
        List<TopicDetailInfo> topicDetailInfos = findPartitionsInfo().stream().map(p -> new TopicDetailInfo(p)).collect(Collectors.toList());
        // 设置数据大小
        topicDetailInfos.stream().forEach(detail-> detail.writeSize(findTopicSize(detail.getTopic())));
        // 设置分区信息
        topicDetailInfos.stream().forEach(detail-> detail.setPartitions(findPartitionMeta(detail.getTopic())));
        // 设置消费者信息
        topicDetailInfos.stream().forEach(detail-> detail.setConsumers(consumersService.findAllConsumersDetail(detail.getTopic())));
        return topicDetailInfos;
    }

    private List<MetadataInfo> findPartitionMeta(String topicName){
        Map<String, Object> map = new HashMap<>();
        map.put("start", 1);
        map.put("length", 500);
        List<MetadataInfo> metadatas = topicService.metadata(clusterAliass[0], topicName, map);
        return metadatas;
    }

    private List<PartitionsInfo> findPartitionsInfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("search", "");
        map.put("start", 0);
        map.put("length", 500);
        List<PartitionsInfo> topics = topicService.list(clusterAliass[0], map).stream().filter(t-> !t.getTopic().equals(TOPIC_MONITOR)).collect(Collectors.toList());
        return topics;
    }

    public String findTopicSize(String topicName){
        return topicService.getTopicSizeAndCapacity(clusterAliass[0], topicName);
    }
}
