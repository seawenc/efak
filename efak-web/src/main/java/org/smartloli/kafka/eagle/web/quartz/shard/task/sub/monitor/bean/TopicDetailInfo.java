package org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.bean;

import com.alibaba.fastjson.JSONObject;
import org.smartloli.kafka.eagle.common.protocol.MetadataInfo;
import org.smartloli.kafka.eagle.common.protocol.PartitionsInfo;
import org.smartloli.kafka.eagle.common.util.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * kafka-eagle几个指标含义
 * https://blog.csdn.net/L13763338360/article/details/105427584
 */
public class TopicDetailInfo {
    private String topic = "";
    private List<MetadataInfo> partitions = new ArrayList<>();
    private List<ConsumerInfo> consumers=new ArrayList<>();


    // 数据倾斜比例 看作broker使用率，如kafka集群9个broker，某topic有7个partition，则broker spread: 7 / 9 = 77%
    private long brokersSpread;
    // partition是否存在倾斜，如kafka集群9个broker，某topic有18个partition，正常每个broker应该2个partition。若其中有3个broker上的partition数>2，则broker skew:  3 / 9 = 33%
    private long brokersSkewed;
    private long brokersLeaderSkewed;

    // 数据条数
    private long rows=0;
    // 1分钟内的数据量
    private long rows1m=0;
    // 占用的存储大小
    private double storageSize=0.0;
    // 占用的存储单位
    private String storagesizeUnit;

    public TopicDetailInfo(){}

    public TopicDetailInfo(PartitionsInfo partitionInfo){
        this.topic=partitionInfo.getTopic();
        this.brokersLeaderSkewed=partitionInfo.getBrokersLeaderSkewed();
        this.brokersSkewed=partitionInfo.getBrokersSkewed();
        this.brokersSpread=partitionInfo.getBrokersSpread();
    }

    /**
     * {"sizetype":"MB","topicsize":"8.10","logsize":100004}
     * @param sizeJson
     */
    public void writeSize(String sizeJson){
        JSONObject jsonObject=JSONObject.parseObject(sizeJson);
        try {
            this.storageSize=jsonObject.getDouble("topicsize");
            this.storagesizeUnit=jsonObject.getString("sizetype");
            this.rows=jsonObject.getLong("logsize");
        } catch (Exception e) {
            LoggerUtils.print(this.getClass()).error("写入size出错，sizeJson="+sizeJson);
        }
    }

    public void setRows1m(TopicDetailInfo topicDetail1mAgo) {
        if(topicDetail1mAgo!=null){
            this.rows1m = rows-topicDetail1mAgo.rows;
        }
    }

    public long getRows1m() {
        return rows1m;
    }

    public void setRows1m(long rows1m) {
        this.rows1m = rows1m;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<MetadataInfo> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<MetadataInfo> partitions) {
        this.partitions = partitions;
    }

    public long getBrokersSpread() {
        return brokersSpread;
    }

    public void setBrokersSpread(long brokersSpread) {
        this.brokersSpread = brokersSpread;
    }

    public long getBrokersSkewed() {
        return brokersSkewed;
    }

    public void setBrokersSkewed(long brokersSkewed) {
        this.brokersSkewed = brokersSkewed;
    }

    public long getBrokersLeaderSkewed() {
        return brokersLeaderSkewed;
    }

    public void setBrokersLeaderSkewed(long brokersLeaderSkewed) {
        this.brokersLeaderSkewed = brokersLeaderSkewed;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public double getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(double storageSize) {
        this.storageSize = storageSize;
    }

    public String getStoragesizeUnit() {
        return storagesizeUnit;
    }

    public void setStoragesizeUnit(String storagesizeUnit) {
        this.storagesizeUnit = storagesizeUnit;
    }

    public List<ConsumerInfo> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<ConsumerInfo> consumers) {
        this.consumers = consumers;
    }
}
