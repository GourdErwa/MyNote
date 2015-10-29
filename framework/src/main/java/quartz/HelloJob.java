package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个简单的quartz调用job
 * Created by lw on 14-7-16.
 */
public class HelloJob implements Job {

    private static Logger _log = LoggerFactory.getLogger(HelloJob.class);

    private String task;

    public HelloJob() {
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        /*_log.info("获取模拟数据——》"
                + context.getJobDetail().getJobDataMap().get("data") + "\t"
                + Joda_Time.getNowTime());*/
    }

}