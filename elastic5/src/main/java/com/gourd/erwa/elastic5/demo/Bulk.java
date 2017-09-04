package com.gourd.erwa.elastic5.demo;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author wei.Li by 2017/4/10
 */
class Bulk {


    private static int i = 0;

    void bulkRequestBuilder(Client client) throws IOException {

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        // either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(client.prepareIndex("test-2017-04", "bi")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "ktcy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );

        bulkRequest.add(client.prepareIndex("test-2017-04", "bi")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "sbk")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.err.println("bulk error!" + bulkResponse.buildFailureMessage());
        } else {
            System.out.println("bulk ok!\t" + (i++));
        }
    }


}
