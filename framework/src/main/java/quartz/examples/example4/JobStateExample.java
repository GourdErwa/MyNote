/*
 * Copyright 2005 - 2009 Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */

package quartz.examples.example4;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This Example will demonstrate how job parameters can be
 * passed into jobs and how state can be maintained
 *
 * @author Bill Kratzer
 */
public class JobStateExample {

    public static void main(String[] args) throws Exception {

        JobStateExample example = new JobStateExample();
        example.run();
    }

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(JobStateExample.class);

        log.info("------- 初始化 -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- 初始化完成 --------");

        log.info("------- 调度工作 ----------------");

        // get a "nice round" time a few seconds in the future....
        Date startTime = nextGivenSecondDate(null, 10);

        // job1 will only run 5 times (at start time, plus 4 repeats),时间间隔10 seconds
        JobDetail job1 = newJob(ColorJob.class)
                .withIdentity("job1", "group1")
                .build();

        SimpleTrigger trigger1 = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(4))
                .build();

        // pass initialization parameters into the job
        job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Green");
        job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        // schedule the job to run
        Date scheduleTime1 = sched.scheduleJob(job1, trigger1);
        log.info(job1.getKey() +
                " 将运行于: " + scheduleTime1 +
                " 重复次数: " + trigger1.getRepeatCount() +
                " times,时间间隔" + trigger1.getRepeatInterval() / 1000 + " seconds");

        // job2 will also run 5 times,时间间隔10 seconds
        JobDetail job2 = newJob(ColorJob.class)
                .withIdentity("job2", "group1")
                .build();

        SimpleTrigger trigger2 = newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(4))
                .build();

        // pass initialization parameters into the job
        // this job has a different favorite color!
        job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Red");
        job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        // schedule the job to run
        Date scheduleTime2 = sched.scheduleJob(job2, trigger2);
        log.info(job2.getKey().toString() +
                " 将运行于: " + scheduleTime2 +
                " 重复次数: " + trigger2.getRepeatCount() +
                " times,时间间隔" + trigger2.getRepeatInterval() / 1000 + " seconds");


        log.info("------- 开始调度工作 ----------------");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        sched.start();

        log.info("------- 调度工作开始 -----------------");

        log.info("------- 等待 60 秒... -------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(60L * 1000L);
            // executing...
        } catch (Exception e) {
        }

        log.info("------- 关闭 ---------------------");

        sched.shutdown(true);

        log.info("------- 关闭完成 -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("执行" + metaData.getNumberOfJobsExecuted() + "工作。");

    }

}
