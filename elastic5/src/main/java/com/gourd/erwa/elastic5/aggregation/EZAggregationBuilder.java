package com.gourd.erwa.elastic5.aggregation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.Li by 2017/1/16
 */
public abstract class EZAggregationBuilder<B extends EZAggregationBuilder<B>> extends BaseAggregationBuilder {

    private final List<BaseAggregationBuilder> aggregations = new ArrayList<>();

    protected EZAggregationBuilder(String name, AggregationTypeEnum type) {
        super(name, type);
    }

    @SuppressWarnings("unchecked")
    public B subAggregation(BaseAggregationBuilder aggregation) {
        this.aggregations.add(aggregation);
        return (B) this;
    }

    public List<BaseAggregationBuilder> getAggregations() {
        return aggregations;
    }

}
