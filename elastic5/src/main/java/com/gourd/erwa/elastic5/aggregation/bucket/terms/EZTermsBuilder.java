package com.gourd.erwa.elastic5.aggregation.bucket.terms;

import com.gourd.erwa.elastic5.aggregation.AggregationTypeEnum;
import com.gourd.erwa.elastic5.aggregation.MetricsAggregationBuilder;

/**
 * @author wei.Li by 2017/1/16
 */
public class EZTermsBuilder extends MetricsAggregationBuilder<EZTermsBuilder> {

    private int size = 0;
    private int shardSize = 0;

    public EZTermsBuilder(String name) {
        super(name, AggregationTypeEnum.TERMS);
    }

    public EZTermsBuilder size(int size) {
        this.size = size;
        return this;
    }

    public EZTermsBuilder shardSize(int shardSize) {
        this.shardSize = shardSize;
        return this;
    }

    public int getSize() {
        return size;
    }

    public int getShardSize() {
        return shardSize;
    }
}
