package com.gourd.erwa.concurrent.mypool;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lw
 * Date: 14-4-22
 */
public abstract class Task implements Runnable {
    // private static Logger logger = Logger.getLogger(Task.class);

    public List<String> taksModel;
    /* 产生时间 */
    private Date generateTime = null;
    /* 提交执行时间 */
    private Date submitTime = null;
    /* 开始执行时间 */
    private Date beginExceuteTime = null;
    /* 执行完成时间 */
    private Date finishTime = null;

    private long taskId;

    public Task() {
        this.generateTime = new Date();
    }

    /**
     * 任务执行入口
     */
    public void run() {

        // 增加新产生的任务
        try {
            System.out.println("run......" + this.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 所有任务的核心 所有特别的业务逻辑执行之处
     *
     * @return Task[]
     * @throws Exception Exception
     */
    public abstract Task[] taskCore() throws Exception;

    /**
     * 是否用到数据库
     *
     * @return 是否用到数据库
     */
    protected abstract boolean useDb();

    /**
     * 是否需要立即执行
     *
     * @return boolean
     */
    protected abstract boolean needExecuteImmediate();

    /**
     * 任务信息
     *
     * @return String
     */
    public abstract String info();

    public Date getGenerateTime() {
        return generateTime;
    }

    public Date getBeginExceuteTime() {
        return beginExceuteTime;
    }

    public void setBeginExceuteTime(Date beginExceuteTime) {
        this.beginExceuteTime = beginExceuteTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

}
