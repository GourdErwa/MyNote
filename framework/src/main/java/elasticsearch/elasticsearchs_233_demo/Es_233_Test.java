package elasticsearch.elasticsearchs_233_demo;

/**
 * Created by lw on 14-7-15.
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 官方JAVA-API
 * http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_233_Test {

    private static final String[] INDEXS = {"analyzier_ezsonar"};

    public static void main(String[] dfd) {
        Es_233_Utils.startupClient();
        try {
            //Es_233_Utils.getAllIndices();
            //  QueryBuilder queryBuilder = Es_233_QueryBuilders_DSL.matchAllQuery();

            //SearchResponse searchResponse = Es_233_Search.builderSearchResponse(Es_233_Search.builderSearchRequestBuilder("message", queryBuilder, "recent_ezsonar_2014-07-23_16-52"));
            // Es_233_Utils.writeSearchResponse(searchResponse);
            Es_233_Search es_233_search = new Es_233_Search(Es_233_Utils.client);


            es_233_search.searchByQuery();

            /*StatsBuilder statsBuilder = AggregationBuilders.stats("_latency_msec_field").field("_latency_msec");

            //AggregationBuilders.terms("by_country").field("country").subAggregation()
            final HistogramBuilder histogramBuilder = AggregationBuilders.histogram("_start_at_field").
                    field("_start_at").
                    interval(60).
                    subAggregation(statsBuilder);


            SearchRequestBuilder builder = es_233_search.builderSearchRequestBuilderByIndex("message", "ezsonar_2015-03-09")
                    .setQuery(QueryBuilders.rangeQuery("_start_at").gt(1425645440).lt(1425649940))
                    .addAggregation(histogramBuilder);

            final String s = builder.internalBuilder().toString();

            System.out.println(s);

            final SearchResponse searchResponse = builder.get();
            final Aggregations aggregations = searchResponse.getAggregations();
            final Map<String, Aggregation> stringAggregationMap = aggregations.asMap();

            final Set<String> strings = stringAggregationMap.keySet();
            for (String string : strings) {

                final Aggregation aggregation = stringAggregationMap.get(string);
                InternalHistogram dateHistogram = (InternalHistogram) aggregation;


                final List<InternalHistogram.Bucket> buckets = dateHistogram.getBuckets();
                for (InternalHistogram.Bucket bucket : buckets) {
                    final Number keyAsNumber = bucket.getKeyAsNumber();

                    System.out.println(keyAsNumber);
                    final Aggregations aggregations1 = bucket.getAggregations();
                    final Aggregation aggregation1 = aggregations1.asMap().get("_latency_msec_field");
                    final String name = aggregation1.getName();
                    System.out.println(name);

                }
            }
            System.out.println(s);
*/

            //Es_233_Utils.writeSearchResponseToMap(es_233_search.builderSearchResponse(builder), "_start_at");
/*
            Facets facets = es_233_search.builderSearchResponse(builder).getFacets();
            Map facets_map = facets.getFacets();

            System.out.println();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("TermsFacet-API");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            TermsFacet termsFacet = (TermsFacet) facets_map.get("TermsFacetBuilder");
            Set<String> stringSet = new TreeSet<>();

            for (TermsFacet.Entry entry : termsFacet) {

                String s = Es_233_Utils.DATETIME_FORMATTER.format(new Date(Long.parseLong(entry.getTerm().toString()) * 1000L));
                stringSet.add(s);
                // Term -》Doc count
                //System.out.println("key :" + s + "\t value:" + entry.getCount());
            }
            String[] strings = stringSet.toArray(new String[0]);
            System.out.println("当天索引时间最小值：" + strings[0]);
            System.out.println("当天索引时间最大值：" + strings[strings.length - 1]);*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Es_233_Utils.shutDownClient();
        }
    }
}
