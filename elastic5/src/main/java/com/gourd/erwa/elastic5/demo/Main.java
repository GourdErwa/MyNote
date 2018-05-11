package com.gourd.erwa.elastic5.demo;

import org.elasticsearch.client.Client;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wei.Li by 2017/4/10
 */
public class Main {


    public static void main(String[] args) {


        final Client client = EsClient.conn();

        final Runnable runnable = () -> {
            try {
                new Bulk().bulkRequestBuilder(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);

    }
}
