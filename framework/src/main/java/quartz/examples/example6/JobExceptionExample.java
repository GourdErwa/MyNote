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

package quartz.examples.example6;

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
 * This job demonstrates how Quartz can handle JobExecutionExceptions that are
 * thrown by工作。
 *
 * @author Bill Kratzer
 */
public class JobExceptionExample {

    public static void main(String[] args) throws Exception {

        JobExceptionExample example = new JobExceptionExample();
        example.run();
    }

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(JobExceptionExample.class);

        log.info("------- 初始化 ----------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- 初始化完成 ------------");

        log.info("------- 调度工作 -------------------");

        // jobs can be scheduled before start() has been called

        // get a "nice round" time a few seconds in the future...
        Date startTime = nextGivenSecondDate(null, 15);

        // badJob1 will run时间间隔10 seconds
        // this job will throw an exception and refire
        // immediately
        JobDetail job = newJob(BadJob1.class)
                .withIdentity("badJob1", "group1")
                .usingJobData("denominator", "0")
                .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();

        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 将运行于: " + ft + " 重复次数: "
                + trigger.getRepeatCount() + " times,时间间隔"
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // badJob2 will run时间间隔five seconds
        // this job will throw an exception and never
        // refire
        job = newJob(BadJob2.class)
                .withIdentity("badJob2", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 将运行于: " + ft + " 重复次数: "
                + trigger.getRepeatCount() + " times,时间间隔"
                + trigger.getRepeatInterval() / 1000 + " seconds");

        log.info("------- 开始调度工作 ----------------");

        // jobs don't start firing until start() has been called...
        sched.start();

        log.info("------- 调度工作开始 -----------------");

        try {
            // sleep for 30 seconds
            Thread.sleep(30L * 1000L);
        } catch (Exception e) {
        }

        log.info("------- 关闭 ---------------------");

        sched.shutdown(false);

        log.info("------- 关闭完成 -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("执行" + metaData.getNumberOfJobsExecuted() + "工作。");
    }

}
