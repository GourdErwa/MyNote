package com.gourd.erwa.elastic5.aggregation;

/**
 * @author wei.Li by 2017/1/16
 */
public interface EZAggregation {

    String getName();

    interface NumericMetrics extends EZAggregation {

        interface SingleValue extends NumericMetrics {

            double getDoubleValue();

            long getLongValue();

        }

        interface MultiValue extends NumericMetrics {
        }
    }

}
