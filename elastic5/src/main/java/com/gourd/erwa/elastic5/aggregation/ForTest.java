package com.gourd.erwa.elastic5.aggregation;

import com.gourd.erwa.elastic5.aggregation.bucket.terms.EZTermsBuilder;
import com.gourd.erwa.elastic5.aggregation.metrics.count.EZCountBuilder;
import com.gourd.erwa.elastic5.impl.ConvertBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 * @author wei.Li by 2017/1/16
 */
public class ForTest {

    public static void main(String[] args) {


        //AggregationBuilders.stats()

        //final TermsBuilder terms = AggregationBuilders.terms("");
        //terms.include()

        /*AggregationBuilders.sum().buildAsBytes();
        SearchRequestBuilder searchRequestBuilder = new SearchRequestBuilder();

        searchRequestBuilder.addAggregation()*/

        final EZCountBuilder ezCountBuilder = EZAggregationBuilders.count("aa").field("");

        final EZTermsBuilder ezTermsBuilder = EZAggregationBuilders.terms("aa").field("").subAggregation(ezCountBuilder);

        System.out.println(ConvertBuilder.convert(ezTermsBuilder));
    }


    private static AggregationBuilder builder(EZTermsBuilder ezTermsBuilder) {


        return null;
    }
}
