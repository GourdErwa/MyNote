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

package quartz.examples.example2;

import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This Example will demonstrate all of the basics of scheduling capabilities
 * of Quartz using Simple Triggers.
 *
 * @author Bill Kratzer
 */
public class SimpleTriggerExample {


    public static void main(String[] args) throws Exception {

        SimpleTriggerExample example = new SimpleTriggerExample();
        example.run();

    }

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(SimpleTriggerExample.class);

        log.info("------- 初始化 -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- 初始化完成 --------");

        log.info("------- 调度工作 ----------------");

        // jobs can be scheduled before sched.start() has been called

        // get a "nice round" time a few seconds in the future...
        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // job1 will only fire once at com.gourd.erwa.date/time "ts"
        JobDetail job = newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();

        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .build();

        // schedule it to run!
        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // job2 will only fire once at com.gourd.erwa.date/time "ts"
        job = newJob(SimpleJob.class)
                .withIdentity("job2", "group1")
                .build();

        trigger = (SimpleTrigger) newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // job3 will run 11 times (run once 重复次数 10 more times)
        // job3 will repeat时间间隔10 seconds
        job = newJob(SimpleJob.class)
                .withIdentity("job3", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(10))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // the same job (job3) will be scheduled by a another trigger
        // this time will only repeat twice at a 70 second interval

        trigger = newTrigger()
                .withIdentity("trigger3", "group2")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(2))
                .forJob(job)
                .build();

        ft = sched.scheduleJob(trigger);
        log.info(job.getKey() +
                " will [also] run at: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // job4 will run 6 times (run once 重复次数 5 more times)
        // job4 will repeat时间间隔10 seconds
        job = newJob(SimpleJob.class)
                .withIdentity("job4", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger4", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(5))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // job5 will run once, five minutes in the future
        job = newJob(SimpleJob.class)
                .withIdentity("job5", "group1")
                .build();

        trigger = (SimpleTrigger) newTrigger()
                .withIdentity("trigger5", "group1")
                .startAt(futureDate(5, IntervalUnit.MINUTE))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // job6 will run indefinitely,时间间隔40 seconds
        job = newJob(SimpleJob.class)
                .withIdentity("job6", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger6", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        log.info("------- 开始调度工作 ----------------");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        sched.start();

        log.info("------- 调度工作开始 -----------------");

        // jobs can also be scheduled after start() has been called...
        // job7 will repeat 20 times, repeat时间间隔five minutes
        job = newJob(SimpleJob.class)
                .withIdentity("job7", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger7", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(5)
                        .withRepeatCount(20))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // jobs can be fired directly... (rather than waiting for a trigger)
        job = newJob(SimpleJob.class)
                .withIdentity("job8", "group1")
                .storeDurably()
                .build();

        sched.addJob(job, true);

        log.info("'Manually' triggering job8...");
        sched.triggerJob(jobKey("job8", "group1"));

        log.info("------- 等待 30 秒... --------------");

        try {
            // wait 33 seconds to show jobs
            Thread.sleep(30L * 1000L);
            // executing...
        } catch (Exception e) {
        }

        // jobs can be re-scheduled...
        // job 7 will run immediately 重复次数 10 times for时间间隔second
        log.info("------- Rescheduling... --------------------");
        trigger = newTrigger()
                .withIdentity("trigger7", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(5)
                        .withRepeatCount(20))
                .build();

        ft = sched.rescheduleJob(trigger.getKey(), trigger);
        log.info("job7 rescheduled to run at: " + ft);

        log.info("------- 等待 five minutes... ------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(300L * 1000L);
            // executing...
        } catch (Exception e) {
        }

        log.info("------- 关闭 ---------------------");

        sched.shutdown(true);

        log.info("------- 关闭完成 -----------------");

        // display some stats about the schedule that just ran
        SchedulerMetaData metaData = sched.getMetaData();
        log.info("执行" + metaData.getNumberOfJobsExecuted() + "工作。");

    }

}
