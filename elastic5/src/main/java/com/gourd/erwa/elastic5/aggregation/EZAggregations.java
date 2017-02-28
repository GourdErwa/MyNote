package com.gourd.erwa.elastic5.aggregation;

import java.util.*;

/**
 * @author wei.Li by 2017/1/16
 */
public class EZAggregations implements Iterable<EZAggregation> {

    private Map<String, EZAggregation> aggregationMap;

    /**
     * Constructs a new addAggregation.
     */
    public EZAggregations(List<EZAggregation> aggregations) {
        if (this.aggregationMap == null) {
            final Map<String, EZAggregation> map = new HashMap<>(aggregations.size());
            for (EZAggregation aggregation : aggregations) {
                map.put(aggregation.getName(), aggregation);
            }
            this.aggregationMap = Collections.unmodifiableMap(map);
        }
    }

    /**
     */
    public Map<String, EZAggregation> getAsMap() {
        return this.aggregationMap;
    }

    /**
     * Returns the aggregation that is associated with the specified name.
     */
    @SuppressWarnings("unchecked")
    public <A extends EZAggregation> A get(String name) {
        return (A) this.aggregationMap.get(name);
    }

    @Override
    public Iterator<EZAggregation> iterator() {
        return this.aggregationMap.values().iterator();
    }


}
