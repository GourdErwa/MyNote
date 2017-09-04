package com.gourd.erwa.hadoop.userbasestation;

/**
 * @author wei.Li by 2017/3/31
 */
public class LineException extends Exception {
    private static final long serialVersionUID = 9106687692223340702L;

    //异常的标识
    //-1:不是当前日期内
    //0:时间格式不正确
    //1:时间坐在的小时超出最大的时段
    private Integer flag;

    public LineException(String msg, Integer flag) {
        super(msg);
        this.flag = flag;
    }

    public Integer getFlag() {
        return flag;
    }
}
