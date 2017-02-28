package com.gourd.erwa.elastic5.aggregation.metrics.count;

import com.gourd.erwa.elastic5.aggregation.EZAggregation;

/**
 * @author wei.Li by 2017/1/16
 */
public interface EZCount extends EZAggregation.NumericMetrics.SingleValue {


    class InternalCount implements EZCount {

        private final double value;

        public InternalCount(double value) {
            this.value = value;
        }

        @Override

        public double getDoubleValue() {
            return this.value;
        }

        @Override
        public long getLongValue() {
            return (long) this.value;
        }

        @Override
        public String getName() {
            return null;
        }
    }

}
