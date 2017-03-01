package com.gourd.erwa.elastic5.aggregation;

import com.gourd.erwa.elastic5.aggregation.bucket.terms.EZTerms;
import com.gourd.erwa.elastic5.aggregation.bucket.terms.EZTermsBuilder;
import com.gourd.erwa.elastic5.aggregation.metrics.count.EZCount;
import com.gourd.erwa.elastic5.aggregation.metrics.count.EZCountBuilder;

/**
 * @author wei.Li by 2017/1/16
 */
public interface EZAggregationBuilders {

    /**
     * @return TermsBuilder
     * @see EZTerms
     */
    static EZTermsBuilder terms(String name) {
        return new EZTermsBuilder(name);
    }

    /**
     * @return CountBuilder
     * @see EZCount
     */
    static EZCountBuilder count(String name) {
        return new EZCountBuilder(name);
    }


}
