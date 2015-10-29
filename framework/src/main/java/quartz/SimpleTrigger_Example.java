package quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;


/**
 * Created by lw on 14-7-17.
 * 在一个给定的时刻,选择重复在指定的时间间隔。
 */
public class SimpleTrigger_Example {

    private static Logger _log = LoggerFactory.getLogger(SimpleTrigger_Example.class);

    /**
     * @param helloJob helloJob
     * @throws org.quartz.SchedulerException SchedulerException
     */
    protected static void buildScheduleJob(HelloJob helloJob) throws SchedulerException {

        String task = helloJob.getTask();
        String className = helloJob.getClass().getName();

        Date startTime = DateBuilder.nextGivenSecondDate(null, 10);//20s后执行

        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity(className, "group1" + task)
                .startAt(startTime)
                .withSchedule(simpleSchedule() //
                        .withIntervalInSeconds(10)      //执行时间间隔
                        .withRepeatCount(10))           //执行次数
                .build();
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity(className, "group1" + task)
                .build();

        job.getJobDataMap().put("data", task);

        QuartzSchedule.scheduleJob(job, simpleTrigger);
        // schedule it to run!
        _log.info(job.getKey() +
                "  repeat: " + simpleTrigger.getRepeatCount() +
                " times, every " + simpleTrigger.getRepeatInterval() / 1000 + " seconds");
    }

    public static void main(String[] args) throws Exception {
        QuartzSchedule.start();
        for (int i = 0; i < 2; i++) {
            HelloJob helloJob = new HelloJob();
            helloJob.setTask(i + "");
            buildScheduleJob(helloJob);
        }


    }

}
