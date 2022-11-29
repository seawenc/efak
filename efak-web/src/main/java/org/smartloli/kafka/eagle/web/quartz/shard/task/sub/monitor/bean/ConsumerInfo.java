package org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumerInfo {
    //连接的node
    private String node ="";
    // group名称
    private String group="";
    //group的offset情况
    private List<MonitorOffsetInfo> offsets=new ArrayList<>();
    // 1分钟内的消费量
    private long consumption1m =0;

    public ConsumerInfo(){}

    public ConsumerInfo(JSONObject row){
        node =row.getString("node");
        group=row.getString("group");
    }

    public long getLag(){
        return offsets.stream().mapToLong(offset-> offset.getLag()).sum();
    }

    /**
     * 1分钟内的消费情况
     * @param consumer1mAgo 5分钟前的消费情况
     * @return
     */
    public void setConsumption1m(ConsumerInfo consumer1mAgo){
        if(consumer1mAgo==null){
            return;
        }
        long offsetCurrent = offsets.stream().mapToLong(offset -> offset.getOffset()).sum();
        long offset1mAgo = consumer1mAgo.getOffsets().stream().mapToLong(offset -> offset.getOffset()).sum();
        this.consumption1m = offsetCurrent-offset1mAgo;
    }

    public long getConsumption1m() {
        return consumption1m;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<MonitorOffsetInfo> getOffsets() {
        return offsets;
    }

    public void setOffsets(List<MonitorOffsetInfo> offsets) {
        this.offsets = offsets;
    }
}
