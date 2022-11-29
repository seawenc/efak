package org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.bean;

import org.smartloli.kafka.eagle.common.protocol.OffsetInfo;

public class MonitorOffsetInfo {
    private int partition;
    private long logSize;
    private long offset;
    private long lag;
    private String owner;

    public MonitorOffsetInfo(){}

    public MonitorOffsetInfo(OffsetInfo offsetInfo){
        this.partition=offsetInfo.getPartition();
        this.logSize=offsetInfo.getPartition();
        this.offset=offsetInfo.getOffset();
        this.lag=offsetInfo.getLag();
        setOwner(offsetInfo.getOwner());
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
        if(owner!=null && owner.length()>2){
            this.owner=owner.split("-")[0];
        }
    }

    public int getPartition() {
        return partition;
    }

    public long getLogSize() {
        return logSize;
    }

    public long getOffset() {
        return offset;
    }

    public long getLag() {
        return lag;
    }

}
