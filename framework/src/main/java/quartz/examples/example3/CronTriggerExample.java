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

package quartz.examples.example3;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This Example will demonstrate all of the basics of scheduling capabilities of
 * Quartz using Cron Triggers.
 *
 * @author Bill Kratzer
 */
public class CronTriggerExample {


    public static void main(String[] args) throws Exception {

        CronTriggerExample example = new CronTriggerExample();
        example.run();
    }

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(CronTriggerExample.class);

        log.info("------- 初始化 -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- 初始化完成 --------");

        log.info("------- 调度工作 ----------------");

        // jobs can be scheduled before sched.start() has been called

        // job 1 will run时间间隔20 seconds
        JobDetail job = newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();

        CronTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0/20 * * * * ?"))
                .build();

        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 一直在调度运行于: " + ft
                + " 重复次数基于表达式: "
                + trigger.getCronExpression());

        // job 2 will run时间间隔other minute (at 15 seconds past the minute)
        job = newJob(SimpleJob.class)
                .withIdentity("job2", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger2", "group1")
                .withSchedule(cronSchedule("15 0/2 * * * ?"))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 一直在调度运行于: " + ft
                + " 重复次数基于表达式: "
                + trigger.getCronExpression());

        // job 3 will run时间间隔other minute but only between 8am and 5pm
        job = newJob(SimpleJob.class)
                .withIdentity("job3", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(cronSchedule("0 0/2 8-17 * * ?"))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 一直在调度运行于: " + ft
                + " 重复次数基于表达式: "
                + trigger.getCronExpression());

        // job 4 will run时间间隔three minutes but only between 5pm and 11pm
        job = newJob(SimpleJob.class)
                .withIdentity("job4", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger4", "group1")
                .withSchedule(cronSchedule("0 0/3 17-23 * * ?"))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 一直在调度运行于: " + ft
                + " 重复次数基于表达式: "
                + trigger.getCronExpression());

        // job 5 将运行于 10am on the 1st and 15th days of the month
        job = newJob(SimpleJob.class)
                .withIdentity("job5", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger5", "group1")
                .withSchedule(cronSchedule("0 0 10am 1,15 * ?"))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 一直在调度运行于: " + ft
                + " 重复次数基于表达式: "
                + trigger.getCronExpression());

        // job 6 will run时间间隔30 seconds but only on Weekdays (Monday through Friday)
        job = newJob(SimpleJob.class)
                .withIdentity("job6", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger6", "group1")
                .withSchedule(cronSchedule("0,30 * * ? * MON-FRI"))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 一直在调度运行于: " + ft
                + " 重复次数基于表达式: "
                + trigger.getCronExpression());

        // job 7 will run时间间隔30 seconds but only on Weekends (Saturday and Sunday)
        job = newJob(SimpleJob.class)
                .withIdentity("job7", "group1")
                .build();

        trigger = newTrigger()
                .withIdentity("trigger7", "group1")
                .withSchedule(cronSchedule("0,30 * * ? * SAT,SUN"))
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " 一直在调度运行于: " + ft
                + " 重复次数基于表达式: "
                + trigger.getCronExpression());

        log.info("------- 开始调度工作 ----------------");

        // All of the jobs have been added to the scheduler, but none of the
        // jobs
        // will run until the scheduler has been started
        sched.start();

        log.info("------- 调度工作开始 -----------------");

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

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("执行" + metaData.getNumberOfJobsExecuted() + "工作。");

    }

}
