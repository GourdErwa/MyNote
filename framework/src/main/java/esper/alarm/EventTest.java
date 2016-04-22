package esper.alarm;

import java.io.Serializable;

public class EventTest implements Serializable {

    /**
     * 告警类型, 响应率, 响应数量, 成功率, 成功数量, 吞吐率, 吞吐量, 基线, 关联基线, 自定义
     */
    private String type;

    /**
     * 触发类型 阈值 0 |基线 1 |关联基线 2
     */
    private int trigger_type;

    /**
     * 告警数据抽样开始时间
     */
    private Integer start_time;
    /**
     * 告警数据抽样结束时间
     */
    private Integer end_time;

    /**
     * 实际告警激发数值
     */
    private Double real_value;

    /**
     * 级别, 采用syslog level, 和alarmdata.level重复， 放在这里，主要是告警事件常用
     */
    private int severity;


    private AlarmHandleSetting alarmHandleSetting;

    public EventTest(String type, int trigger_type, Integer start_time, Integer end_time, Double real_value, int severity, AlarmHandleSetting alarmHandleSetting) {
        this.type = type;
        this.trigger_type = trigger_type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.real_value = real_value;
        this.severity = severity;
        this.alarmHandleSetting = alarmHandleSetting;
    }

    public String getType() {
        return type;
    }

    public int getTrigger_type() {
        return trigger_type;
    }

    public Integer getStart_time() {
        return start_time;
    }

    public Integer getEnd_time() {
        return end_time;
    }

    public Double getReal_value() {
        return real_value;
    }

    public int getSeverity() {
        return severity;
    }

    public AlarmHandleSetting getAlarmHandleSetting() {
        return alarmHandleSetting;
    }
}
