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

package quartz.examples.example8;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.quartz.DateBuilder.dateOf;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This example will demonstrate how calendars can be used
 * to exclude periods of time when scheduling should not
 * take place.
 */
public class CalendarExample {

    public static void main(String[] args) throws Exception {

        CalendarExample example = new CalendarExample();
        example.run();
    }

    public void run() throws Exception {
        final Logger log = LoggerFactory.getLogger(CalendarExample.class);

        log.info("------- 初始化 ----------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- 初始化完成 -----------");

        log.info("------- 调度工作 -------------------");

        // Add the holiday calendar to the schedule
        AnnualCalendar holidays = new AnnualCalendar();

        // fourth of July (July 4)
        Calendar fourthOfJuly = new GregorianCalendar(2005, 6, 4);
        holidays.setDayExcluded(fourthOfJuly, true);
        // halloween (Oct 31)
        Calendar halloween = new GregorianCalendar(2005, 9, 31);
        holidays.setDayExcluded(halloween, true);
        // christmas (Dec 25)
        Calendar christmas = new GregorianCalendar(2005, 11, 25);
        holidays.setDayExcluded(christmas, true);

        // tell the schedule about our holiday calendar
        sched.addCalendar("holidays", holidays, false, false);


        // schedule a job to run hourly, starting on halloween
        // at 10 am
        Date runDate = dateOf(0, 0, 10, 31, 10);

        JobDetail job = newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(runDate)
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(1)
                        .repeatForever())
                .modifiedByCalendar("holidays")
                .build();

        // schedule the job and print the first run com.gourd.erwa.date
        Date firstRunTime = sched.scheduleJob(job, trigger);

        // print out the first execution com.gourd.erwa.date.
        // Note:  Since Halloween (Oct 31) is a holiday, then
        // we will not run until the next day! (Nov 1)
        log.info(job.getKey() +
                " 将运行于: " + firstRunTime +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        log.info("------- 开始调度工作 ----------------");
        sched.start();

        // wait 30 seconds:
        // note:  nothing will run
        log.info("------- 等待 30 秒... --------------");
        try {
            // wait 30 seconds to show jobs
            Thread.sleep(30L * 1000L);
            // executing...
        } catch (Exception e) {
        }


        // shut down the scheduler
        log.info("------- 关闭 ---------------------");
        sched.shutdown(true);
        log.info("------- 关闭完成 -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("执行" + metaData.getNumberOfJobsExecuted() + "工作。");

    }

}
