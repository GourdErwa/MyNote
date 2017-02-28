package com.gourd.erwa.elastic5.aggregation;

/**
 * @author wei.Li by 2017/1/16
 */
public abstract class BaseAggregationBuilder {

    private final String name;
    private final AggregationTypeEnum type;

    protected BaseAggregationBuilder(String name, AggregationTypeEnum type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public AggregationTypeEnum getType() {
        return type;
    }
}
