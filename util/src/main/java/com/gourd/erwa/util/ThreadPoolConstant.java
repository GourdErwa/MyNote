package com.gourd.erwa.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;

/**
 * @author wei.Li
 */
public interface ThreadPoolConstant {

    ListeningExecutorService JOB_THREAD_POOL = MoreExecutors.listeningDecorator(
            Executors.newCachedThreadPool(
                    new ThreadFactoryBuilder().setNameFormat("BasicThreadPoolJob-CachedThreadPool-%d").build()
            )
    );

    ListeningScheduledExecutorService JOB_SCHEDULED_THREAD_POOL = MoreExecutors.listeningDecorator(
            Executors.newScheduledThreadPool(
                    5,
                    new ThreadFactoryBuilder().setNameFormat("BasicScheduledThreadPool-%d").build()
            )
    );

}
