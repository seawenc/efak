package org.smartloli.kafka.eagle.web.quartz.shard.task.sub.monitor.helper;

/**
 * 用于耗时打印
 */
public class ExecTimeCost {

    /**
     * 记录开始时间
     */
    long costBegin;

    /**
     * 构造方法
     */
    public ExecTimeCost(){
        costBegin=System.currentTimeMillis();
    }

    /**
     * 获得耗时
     * @return
     */
    public long get(){
        long now = System.currentTimeMillis();
        long cost = now - costBegin;
        return cost;
    }

    public String getFormat(){
        return formatCost(get());
    }

    public static String formatCost(long cost){
        if(cost>1000*60*30){
            return (cost/(1000*60))+"分钟";
        }
        if(cost>1000*60*10){
            return (cost/1000)+"秒";
        }
        return cost+"ms";
    }
}
