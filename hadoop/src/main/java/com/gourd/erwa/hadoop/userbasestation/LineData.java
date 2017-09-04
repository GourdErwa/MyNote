package com.gourd.erwa.hadoop.userbasestation;

import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wei.Li by 2017/3/31
 */
class LineData {

    private String userId;//用户标识
    private String pos;//位置信息
    private String time;//原始时间信息
    private String timeFlag;//所需要的时间段信息,从time字段中得到
    private Date day;//所需要的时间信息,需要转为unix格式,从time字段中德奥
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 数据格式化的方法,并提供了验证数据合法性的流程
     *
     * @param line  每行数据
     * @param isPos 该行数据是POS还是NET
     */
    void format(String line, Boolean isPos) throws LineException {

        final String[] words = line.split("\\t");
        //根据不同数据的格式截取需要的信息
        if (isPos) {
            this.userId = words[0];
            this.pos = words[3];
            this.time = words[4];
        } else {
            this.userId = words[0];
            this.pos = words[2];
            this.time = words[3];
        }
        //将字符串的time字段转成时间类型的day字段,如果该时间格式不正确,则过滤并统计异常
        try {
            this.day = this.simpleDateFormat.parse(this.time);
        } catch (ParseException e) {
            throw new LineException("Incorrect date format!", 0);
        }

        //根据time字段计算timeFlag过程
        //从time中获得当前的小时信息
        this.timeFlag = time.split(" ")[1].split(":")[0];
    }

    /**
     * 将 useId 和 timeFlag 作为 key 输出
     */
    Text outKey() {
        return new Text(this.userId + "," + this.timeFlag);
    }

    /**
     * 将 pos 和 day()
     */
    Text outValue() {
        return new Text(this.pos + "," + this.day.getTime() / 1000L);
    }

}
