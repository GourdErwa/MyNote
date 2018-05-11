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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author wkratzer
 * <p>
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Job1Listener implements JobListener {

    private static Logger _log = LoggerFactory.getLogger(Job1Listener.class);

    public String getName() {
        return "job1_to_job2";
    }

    public void jobToBeExecuted(JobExecutionContext inContext) {
        _log.info("Job1Listener says: Job Is about to be executed.");
    }

    public void jobExecutionVetoed(JobExecutionContext inContext) {
        _log.info("Job1Listener says: Job Execution was vetoed.");
    }

    public void jobWasExecuted(JobExecutionContext inContext,
                               JobExecutionException inException) {
        _log.info("Job1Listener says: Job was executed.");

        // Simple job #2
        JobDetail job2 = newJob(SimpleJob2.class)
                .withIdentity("job2")
                .build();

        Trigger trigger = (SimpleTrigger) newTrigger()
                .withIdentity("job2Trigger")
                .startNow()
                .build();

        try {
            // schedule the job to run!
            inContext.getScheduler().scheduleJob(job2, trigger);
        } catch (SchedulerException e) {
            _log.warn("无法安排 job2!");
            e.printStackTrace();
        }

    }

}
