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

package quartz.examples.example12;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bill Kratzer
 * <p>
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RemoteServerExample {

    public static void main(String[] args) throws Exception {

        RemoteServerExample example = new RemoteServerExample();
        example.run();
    }

    /**
     * This example will spawn a large number of jobs to run
     *
     * @throws Exception exception
     * @author James House, Bill Kratzer
     */
    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(RemoteServerExample.class);

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- 初始化完成 -----------");

        log.info("------- (Not Scheduling any Jobs - relying on a remote client to schedule jobs --");

        log.info("------- 开始调度工作 ----------------");

        // start the schedule
        sched.start();

        log.info("------- 调度工作开始 -----------------");

        log.info("------- 等待 ten minutes... ------------");

        // wait five minutes to give our jobs a chance to run
        try {
            Thread.sleep(600L * 1000L);
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
