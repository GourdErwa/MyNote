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

package quartz.examples.example5;

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
 * Demonstrates the behavior of <code>StatefulJob</code>s, as well as how
 * misfire instructions affect the firings of triggers of <code>StatefulJob</code>
 * s - when the jobs take longer to execute that the frequency of the trigger's
 * repitition.
 * <p>
 * <p>
 * While the example is running, you should note that there are two triggers
 * with identical schedules, firing identical工作。 The triggers "want" to fire
 * 时间间隔3 seconds, but the jobs take 10 seconds to execute. Therefore, by the
 * time the jobs complete their execution, the triggers have already "misfired"
 * (unless the scheduler's "misfire threshold" has been set to more than 7
 * seconds). You should see that one of the jobs has its misfire instruction
 * set to <code>SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT</code>,
 * which causes it to fire immediately, when the misfire is detected. The other
 * trigger uses the default "smart policy" misfire instruction, which causes
 * the trigger to advance to its next fire time (skipping those that it has
 * missed) - so that it does not refire immediately, but rather at the next
 * scheduled time.
 *
 * @author <a href="mailto:bonhamcm@thirdeyeconsulting.com">Chris Bonham</a>
 */
public class MisfireExample {


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {

        MisfireExample example = new MisfireExample();
        example.run();
    }

    /**
     * Run.
     *
     * @throws Exception the exception
     */
    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(MisfireExample.class);

        log.info("------- 初始化 -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- 初始化完成 -----------");

        log.info("------- 调度工作 -----------");

        // jobs can be scheduled before start() has been called

        // get a "nice round" time a few seconds in the future...
        Date startTime = nextGivenSecondDate(null, 15);

        // statefulJob1 will run时间间隔three seconds
        // (but it will delay for ten seconds)
        JobDetail job = newJob(StatefulDumbJob.class)
                .withIdentity("statefulJob1", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
                .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever())
                .build();

        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        // statefulJob2 will run时间间隔three seconds
        // (but it will delay for ten seconds - and therefore purposely misfire after a few iterations)
        job = newJob(StatefulDumbJob.class)
                .withIdentity("statefulJob2", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
                .build();

        trigger = newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever()
                        .withMisfireHandlingInstructionNowWithExistingCount()) // set misfire instructions
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() +
                " 将运行于: " + ft +
                " 重复次数: " + trigger.getRepeatCount() +
                " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");

        log.info("------- 开始调度工作 ----------------");

        // jobs don't start firing until start() has been called...
        sched.start();

        log.info("------- 调度工作开始 -----------------");

        try {
            // sleep for ten minutes for triggers to file....
            Thread.sleep(600L * 1000L);
        } catch (InterruptedException e) {
        }

        log.info("------- 关闭 ---------------------");

        sched.shutdown(true);

        log.info("------- 关闭完成 -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("执行" + metaData.getNumberOfJobsExecuted() + "工作。");
    }

}
