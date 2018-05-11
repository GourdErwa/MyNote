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

package quartz.examples.example9;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Demonstrates the behavior of <code>JobListener</code>s.  In particular,
 * this example will use a job listener to trigger another job after one
 * job succesfully executes.
 */
public class ListenerExample {

    public static void main(String[] args) throws Exception {

        ListenerExample example = new ListenerExample();
        example.run();
    }

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(ListenerExample.class);

        log.info("------- 初始化 ----------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- 初始化完成 -----------");

        log.info("------- 调度工作 -------------------");

        // schedule a job to run immediately

        JobDetail job = newJob(SimpleJob1.class)
                .withIdentity("job1")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("trigger1")
                .startNow()
                .build();

        // Set up the listener
        JobListener listener = new Job1Listener();
        Matcher<JobKey> matcher = KeyMatcher.keyEquals(job.getKey());
        sched.getListenerManager().addJobListener(listener, matcher);

        // schedule the job to run
        sched.scheduleJob(job, trigger);

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
