package quartz;


import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lw on 14-7-16.
 * <p>
 * CronTrigger
 */
public class CronTrigger_Example {

    private static Logger _log = LoggerFactory.getLogger(CronTrigger_Example.class);

    /**
     * @param helloJob helloJob
     * @throws SchedulerException SchedulerException
     */
    protected static void buildScheduleJob(HelloJob helloJob) throws SchedulerException {

        String task = helloJob.getTask();
        String className = helloJob.getClass().getName();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(className, "HelloJob" + task)
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .build();


        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity(className, "group1" + task)
                .build();

        job.getJobDataMap().put("data", task);

        QuartzSchedule.scheduleJob(job, trigger);

    }

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 10; i++) {
            HelloJob helloJob = new HelloJob();
            helloJob.setTask(i + "");
            buildScheduleJob(helloJob);
        }

        QuartzSchedule.start();
    }

}