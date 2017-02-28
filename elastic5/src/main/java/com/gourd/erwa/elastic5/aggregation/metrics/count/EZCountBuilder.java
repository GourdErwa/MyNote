package com.gourd.erwa.elastic5.aggregation.metrics.count;

import com.gourd.erwa.elastic5.aggregation.AggregationTypeEnum;
import com.gourd.erwa.elastic5.aggregation.MetricsAggregationBuilder;

/**
 * @author wei.Li by 2017/1/16
 */
public class EZCountBuilder extends MetricsAggregationBuilder<EZCountBuilder> {

    public EZCountBuilder(String name) {
        super(name, AggregationTypeEnum.COUNT);
    }

}
