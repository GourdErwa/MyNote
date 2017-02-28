package com.gourd.erwa.elastic5.aggregation.bucket.terms;

import com.gourd.erwa.elastic5.aggregation.EZAggregations;
import com.gourd.erwa.elastic5.aggregation.bucket.IMultiBucketsAggregation;

import java.util.List;

/**
 * @author wei.Li by 2017/1/16
 */
public interface EZTerms extends IMultiBucketsAggregation {


    class EZTermsImpl implements EZTerms {

        private final List<StringBucket> r;

        public EZTermsImpl(List<StringBucket> r) {
            this.r = r;
        }

        @Override
        public List<StringBucket> getBuckets() {
            return this.r;
        }

        static class StringBucket implements Bucket {

            private final Object o;
            private final long docCount;
            private final EZAggregations ezAggregations;

            StringBucket(Object o, long docCount, EZAggregations ezAggregations) {
                this.o = o;
                this.docCount = docCount;
                this.ezAggregations = ezAggregations;
            }

            @Override
            public Object getKey() {
                return this.o;
            }

            @Override
            public String getKeyAsString() {
                return this.o.toString();
            }

            @Override
            public long getDocCount() {
                return this.docCount;
            }

            @Override
            public EZAggregations getAggregations() {
                return this.ezAggregations;
            }
        }
    }

}
