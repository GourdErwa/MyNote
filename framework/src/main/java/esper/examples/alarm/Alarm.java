package esper.examples.alarm;

import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

/**
 * @author wei.Li by 14-8-14.
 */
public class Alarm {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Alarm.class);

    private String id; //编号
    private String keyunique;//流+类型+开始时间  创建唯一标识
    private long start_time;//开始时间
    private long end_time;//结束时间
    private String type;//告警类型 0基线告警 1条件告警
    private int num;//实际数值 = 监视的对象流 num
    private int Offset;//偏移值
    private int duration;//持续时间
    private boolean merged = false;//是否已告警合并
    private Integer merged_at = null;//告警合并后的最后创建时间
    private int merged_count = 0;//告警合并数量


    public Alarm() {
        this.id = UUID.randomUUID().toString();
        this.start_time = System.currentTimeMillis();

        //LOGGER.debug("Random AlarmData`s type : <{}> , num : <{}>", this.type, this.num);
    }

    //获取随机的数据对象
    protected static Alarm getInstance(final Stream stream, String type) {
        Alarm alarm = new Alarm();
        int num = stream.getNum();
        alarm.setNum(num);
        alarm.setType(type);
        alarm.setKeyunique(String.format("%s#%s#", stream.getStream_id(), type));
        return alarm;
    }

    @Override
    public String toString() {
        return "AlarmData{" +
                "id='" + id + '\'' +
                ", keyunique='" + keyunique + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", type=" + type +
                ", num=" + num +
                ", Offset=" + Offset +
                ", duration=" + duration +
                ", merged=" + merged +
                ", merged_at=" + merged_at +
                ", merged_count=" + merged_count +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyunique() {
        return keyunique;
    }

    public void setKeyunique(String keyunique) {
        this.keyunique = keyunique;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getOffset() {
        return Offset;
    }

    public void setOffset(int offset) {
        Offset = offset;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public Integer getMerged_at() {
        return merged_at;
    }

    public void setMerged_at(Integer merged_at) {
        this.merged_at = merged_at;
    }

    public int getMerged_count() {
        return merged_count;
    }

    public void setMerged_count(int merged_count) {
        this.merged_count = merged_count;
    }
}

/**
 * 被监视的对象流
 */
class Stream {

    private int stream_id;//流id

    private int num;//随机产生的数值

    Stream() {
        Random random = new Random();
        this.stream_id = random.nextInt(3);
        this.num = random.nextInt(1000);
    }

    Stream(int stream_id, int num) {
        this.stream_id = stream_id;
        this.num = num;
    }

    public static Stream getRandom() {
        return new Stream();
    }

    @Override
    public String toString() {
        return "Stream{" +
                "stream_id=" + stream_id +
                ", num=" + num +
                '}';
    }

    public int getStream_id() {
        return stream_id;
    }

    public void setStream_id(int stream_id) {
        this.stream_id = stream_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

/**
 * 基线值-动态修改
 */
class Baseline implements Runnable {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Baseline.class);

    public static int num;//当前基线值

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            num = random.nextInt(1);
            //LOGGER.debug("Baseline update num is : <{}>", num);
            try {
                Thread.sleep(random.nextInt(20000));
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

/**
 * 条件值动态修改
 */
class Condition implements Runnable {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Condition.class);

    public static int num;//当前条件设置值

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            num = random.nextInt(1);
            //LOGGER.debug("Condition update num is : <{}>", num);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
