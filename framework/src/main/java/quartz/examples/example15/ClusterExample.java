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

package quartz.examples.example15;

import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class ClusterExample {

    private static Logger _log = LoggerFactory.getLogger(ClusterExample.class);

    public static void main(String[] args) throws Exception {
        boolean clearJobs = false;
        boolean scheduleJobs = true;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("clearJobs")) {
                clearJobs = true;
            } else if (args[i].equalsIgnoreCase("dontScheduleJobs")) {
                scheduleJobs = false;
            }
        }

        ClusterExample example = new ClusterExample();
        example.run(clearJobs, scheduleJobs);
    }

    public void run(boolean inClearJobs, boolean inScheduleJobs)
            throws Exception {

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        if (inClearJobs) {
            _log.warn("***** Deleting existing jobs/triggers *****");
            sched.clear();
        }

        _log.info("------- 初始化完成 -----------");

        if (inScheduleJobs) {

            _log.info("------- 调度工作 ------------------");

            String schedId = sched.getSchedulerInstanceId();

            int count = 1;

            JobDetail job = newJob(SimpleRecoveryJob.class)
                    .withIdentity("job_" + count, schedId) // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
                    .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went down...
                    .build();


            SimpleTrigger trigger = newTrigger()
                    .withIdentity("triger_" + count, schedId)
                    .startAt(futureDate(1, IntervalUnit.SECOND))
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(20)
                            .withIntervalInSeconds(5))
                    .build();


            _log.info(job.getKey() +
                    " 将运行于: " + trigger.getNextFireTime() +
                    " 重复次数: " + trigger.getRepeatCount() +
                    " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");
            sched.scheduleJob(job, trigger);

            count++;

            job = newJob(SimpleRecoveryJob.class)
                    .withIdentity("job_" + count, schedId) // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
                    .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went down...
                    .build();

            trigger = newTrigger()
                    .withIdentity("triger_" + count, schedId)
                    .startAt(futureDate(2, IntervalUnit.SECOND))
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(20)
                            .withIntervalInSeconds(5))
                    .build();

            _log.info(job.getKey() +
                    " 将运行于: " + trigger.getNextFireTime() +
                    " 重复次数: " + trigger.getRepeatCount() +
                    " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");
            sched.scheduleJob(job, trigger);

            count++;

            job = newJob(SimpleRecoveryStatefulJob.class)
                    .withIdentity("job_" + count, schedId) // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
                    .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went down...
                    .build();

            trigger = newTrigger()
                    .withIdentity("triger_" + count, schedId)
                    .startAt(futureDate(1, IntervalUnit.SECOND))
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(20)
                            .withIntervalInSeconds(3))
                    .build();

            _log.info(job.getKey() +
                    " 将运行于: " + trigger.getNextFireTime() +
                    " 重复次数: " + trigger.getRepeatCount() +
                    " times,时间间隔" + trigger.getRepeatInterval() / 1000 + " seconds");
            sched.scheduleJob(job, trigger);

            count++;

            job = newJob(SimpleRecoveryJob.class)
                    .withIdentity("job_" + count, schedId) // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
                    .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went down...
                    .build();

            trigger = newTrigger()
                    .withIdentity("triger_" + count, schedId)
                    .startAt(futureDate(1, IntervalUnit.SECOND))
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(20)
                            .withIntervalInSeconds(4))
                    .build();

            _log.info(job.getKey() + " 将运行于: "
                    + trigger.getNextFireTime() + " & repeat: "
                    + trigger.getRepeatCount() + "/"
                    + trigger.getRepeatInterval());
            sched.scheduleJob(job, trigger);

            count++;

            job = newJob(SimpleRecoveryJob.class)
                    .withIdentity("job_" + count, schedId) // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
                    .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went down...
                    .build();

            trigger = newTrigger()
                    .withIdentity("triger_" + count, schedId)
                    .startAt(futureDate(1, IntervalUnit.SECOND))
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(20)
                            .withIntervalInMilliseconds(4500L))
                    .build();

            _log.info(job.getKey() + " 将运行于: "
                    + trigger.getNextFireTime() + " & repeat: "
                    + trigger.getRepeatCount() + "/"
                    + trigger.getRepeatInterval());
            sched.scheduleJob(job, trigger);
        }

        // jobs don't start firing until start() has been called...
        _log.info("------- 开始调度工作 ---------------");
        sched.start();
        _log.info("------- 调度工作开始 ----------------");

        _log.info("------- 等待 for one hour... ----------");
        try {
            Thread.sleep(3600L * 1000L);
        } catch (Exception e) {
        }

        _log.info("------- 关闭 --------------------");
        sched.shutdown();
        _log.info("------- 关闭完成 ----------------");
    }
}
