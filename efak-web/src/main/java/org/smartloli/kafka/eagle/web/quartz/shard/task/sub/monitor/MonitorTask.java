/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor;

import com.alibaba.fastjson.JSON;
import org.smartloli.kafka.eagle.api.im.queue.KafkaJob;
import org.smartloli.kafka.eagle.common.util.LoggerUtils;
import org.smartloli.kafka.eagle.common.util.SystemConfigUtils;
import org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.bean.ConsumerInfo;
import org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.helper.ExecTimeCost;
import org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.bean.TopicDetailInfo;
import org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.service.TopicDetailService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 监控topic明细，信息发送到kafka用于做报警监控
 */
public class MonitorTask extends Thread {


    private TopicDetailService topicDetailService;
    // 一分钟前的数据
    private static Map<String,TopicDetailInfo> detailInfos1mAgo=new HashMap<>();

    private static String[] clusterAlias = SystemConfigUtils.getPropertyArray("efak.zk.cluster.alias", ",");

    public final static String TOPIC_MONITOR = SystemConfigUtils.getProperty("efak.monitor.topic", "TOPIC_MONITOR");


    /**
     * 1、收到的报文数量（每分钟），Topic
     * 2、已消费的报文数（每分钟） =  消费速度
     * 3、未消费的报文数
     */
    @Override
    public synchronized void run() {
        ExecTimeCost cost=new ExecTimeCost();
        // 查找
        List<TopicDetailInfo> topicDetailInfos = getTopicDetailService().findAllTopicsDetailInfo();
        // 计算1分钟内的指标
        calc1mQuota(topicDetailInfos);
        // 打印执行日志
        log(cost, topicDetailInfos);
        // 发送到kafka
        sendToKafka(topicDetailInfos);
    }

    private void log(ExecTimeCost cost, List<TopicDetailInfo> topicDetailInfos) {
        LoggerUtils.print(this.getClass()).error("获得topic明细,耗时=" + cost.getFormat() + "," + JSON.toJSONString(topicDetailInfos));
    }

    private void sendToKafka(List<TopicDetailInfo> topicDetailInfos) {
        KafkaJob kafkaJob=new KafkaJob();
        topicDetailInfos.forEach(v-> kafkaJob.sendKafkaMsg(clusterAlias[0], TOPIC_MONITOR,JSON.toJSONString(v)));
    }

    /**
     * 计算1分钟内的指标
     * @param topicDetailInfos
     */
    private void calc1mQuota(List<TopicDetailInfo> topicDetailInfos) {
        // 过滤掉没有历史 的记录
        List<TopicDetailInfo> existHisTopicInfo = topicDetailInfos.stream().filter(detail -> detailInfos1mAgo.containsKey(detail.getTopic())).collect(Collectors.toList());
        for (TopicDetailInfo topicDetailInfo:existHisTopicInfo){
            // 计算1分钟数据产生量
            TopicDetailInfo topicDetail1mAgo = detailInfos1mAgo.get(topicDetailInfo.getTopic());
            topicDetailInfo.setRows1m(topicDetail1mAgo);
            // 计算1分钟消费量
            Map<String, ConsumerInfo> consumers1mAgo = topicDetail1mAgo.getConsumers().stream().collect(Collectors.toMap(topicDetail -> topicDetail.getGroup(), topicDetail -> topicDetail));
            topicDetailInfo.getConsumers().forEach(consumer -> consumer.setConsumption1m(consumers1mAgo.get(consumer.getGroup())));
        }
        detailInfos1mAgo=topicDetailInfos.stream().collect(Collectors.toMap(info->info.getTopic(),info-> info));
    }

    private TopicDetailService getTopicDetailService() {
        if(topicDetailService==null){
            topicDetailService=new TopicDetailService();
        }
        return topicDetailService;
    }
}
