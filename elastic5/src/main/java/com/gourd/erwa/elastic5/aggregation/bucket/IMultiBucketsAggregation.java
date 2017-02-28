package com.gourd.erwa.elastic5.aggregation.bucket;

import com.gourd.erwa.elastic5.aggregation.EZAggregations;

import java.util.List;

/**
 * @author wei.Li by 2017/1/16
 */
public interface IMultiBucketsAggregation {

    /**
     * @return The buckets of this aggregation.
     */
    List<? extends Bucket> getBuckets();

    /**
     * A bucket represents a criteria to which all documents that fall in it adhere to. It is also uniquely identified
     * by a key, and can potentially hold sub-aggregations computed over all documents in it.
     */
    interface Bucket {
        /**
         * @return The key associated with the bucket
         */
        Object getKey();

        /**
         * @return The key associated with the bucket as a string
         */
        String getKeyAsString();

        /**
         * @return The number of documents that fall within this bucket
         */
        long getDocCount();

        /**
         * @return The sub-aggregations of this bucket
         */
        EZAggregations getAggregations();
    }
}
