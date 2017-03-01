package com.gourd.erwa.elastic5.impl;

import com.gourd.erwa.elastic5.aggregation.BaseAggregationBuilder;
import com.gourd.erwa.elastic5.aggregation.EZAggregationBuilder;
import com.gourd.erwa.elastic5.aggregation.bucket.terms.EZTermsBuilder;
import com.gourd.erwa.elastic5.aggregation.metrics.count.EZCountBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;

import java.util.List;

/**
 * @author wei.Li by 2017/1/16
 */
public class ConvertBuilder {

    public static AggregationBuilder convert(BaseAggregationBuilder builder) {

        AggregationBuilder r;

        switch (builder.getType()) {

            case TERMS: {

                final EZTermsBuilder ezTermsBuilder = (EZTermsBuilder) builder;

                r = AggregationBuilders.terms(ezTermsBuilder.getName())
                        .field(ezTermsBuilder.getField())
                        .shardSize(ezTermsBuilder.getShardSize())
                        .size(ezTermsBuilder.getSize());
                break;
            }

            case COUNT: {

                final EZCountBuilder ezCountBuilder = (EZCountBuilder) builder;
                r = AggregationBuilders.count(ezCountBuilder.getName())
                        .field(ezCountBuilder.getField());
                break;
            }

            default: {
                r = null;
                break;
            }
        }

        @SuppressWarnings("unchecked") final List<BaseAggregationBuilder> aggregations = ((EZAggregationBuilder) builder).getAggregations();
        if (!aggregations.isEmpty()) {
            for (BaseAggregationBuilder aggregation : aggregations) {
                final AggregationBuilder convert = convert(aggregation);
                if (convert != null) {
                    r.subAggregation(convert);
                }
            }
        }
        return r;

    }
}
