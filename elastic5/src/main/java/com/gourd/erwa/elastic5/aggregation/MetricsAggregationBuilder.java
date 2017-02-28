package com.gourd.erwa.elastic5.aggregation;

/**
 * @author wei.Li by 2017/1/16
 */
public abstract class MetricsAggregationBuilder<B extends MetricsAggregationBuilder<B>> extends EZAggregationBuilder<B> {

    private String field;

    protected MetricsAggregationBuilder(String name, AggregationTypeEnum type) {
        super(name, type);
    }

    @SuppressWarnings("unchecked")
    protected B field(String field) {
        this.field = field;
        return (B) this;
    }


    public String getField() {
        return field;
    }
}
