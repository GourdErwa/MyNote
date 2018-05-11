package elasticsearch;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * Created by lw on 14-7-15.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 搜索
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_Search {


    /**
     * 搜索，通过Id搜索API
     *
     * @param id id
     */
    protected static void searchById(String id) {
        GetResponse responsere = Es_Utils.client.prepareGet(Es_Utils.INDEX_DEMO_01, Es_Utils.INDEX_DEMO_01_MAPPING, id)
                /*
                设置线程
     当删除api在同一个节点上执行时（在一个分片中执行一个api会分配到同一个服务器上），
     删除api允许执行前设置线程模式 （operationThreaded选项），operationThreaded这个选项是使这个操作在另外一个线程中执行，
     或在一个正在请求的线程 （假设这个api仍是异步的）中执行。
     默认的话operationThreaded会设置成true，这意味着这个操作将在一个不同的线程中执行。
     下面是 设置成false的方法：
                 */
                .setOperationThreaded(false)
                .execute()
                .actionGet();
        if (responsere.isExists()) {
            System.out.println("通过Id=[" + id + "]搜索结果:\n" + responsere.getSourceAsString());
        } else {
            System.out.println("通过Id=[" + id + "]搜索结果:不存在");
        }

    }

    /**
     * 搜索，Query搜索API
     * 条件组合查询
     */
    protected static void searchByQuery() {

        //qb1构造了一个TermQuery，对name这个字段进行项搜索，项是最小的索引片段，这个查询对应lucene本身的TermQuery
        QueryBuilder queryBuilder1 = QueryBuilders.termQuery("name", "葫芦2娃");

        //qb2构造了一个组合查询（BoolQuery），其对应lucene本身的BooleanQuery，可以通过must、should、mustNot方法对QueryBuilder进行组合，形成多条件查询
        QueryBuilder queryBuilder2 = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("name", "test1"))
                .must(QueryBuilders.termQuery("age", 29))
                .mustNot(QueryBuilders.termQuery("height", 100))
                .should(QueryBuilders.termQuery("name", "2"));

        //直接执行搜索
        SearchHit[] searchHitsBySearch = Es_Utils.client.search(new SearchRequest(Es_Utils.INDEX_DEMO_01)
                .types(Es_Utils.INDEX_DEMO_01_MAPPING)
                .source(
                        SearchSourceBuilder.searchSource()
                                .sort("age")
                )
        )
                .actionGet()
                .getHits()
                .hits();


        //预准备执行搜索
        Es_Utils.client.prepareSearch(Es_Utils.INDEX_DEMO_01)
                .setTypes(Es_Utils.INDEX_DEMO_01_MAPPING)
                // .setSearchType(SearchType.SCAN)
                //.setQuery(queryBuilder1)
                //.setQuery(QueryBuilders.termQuery("multi", "test"))       // Query
                //.setPostFilter(FilterBuilders.rangeFilter("age").lt(10).gt(50))   // Filter过滤
                //.setPostFilter(FilterBuilders.inFilter("age", 45))   // Filter过滤
                //.setPostFilter(FilterBuilders.boolFilter().mustNot(FilterBuilders.inFilter("age", 20, 21, 22)))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                //注册监听事件
                .addListener(new ActionListener<SearchResponse>() {
                    @Override
                    public void onResponse(SearchResponse searchResponse) {
                        Es_Utils.writeSearchResponse(searchResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }

    /**
     * 搜索，Query搜索API
     * count查询
     */
    protected static void searchByQuery_Count() {

        /*long countByCount = Es_Utils.client.count(
                new CountRequest(Es_Utils.INDEX_DEMO_01).types(Es_Utils.INDEX_DEMO_01_MAPPING)

        )
                .actionGet()
                .getCount();*/
        /*CountResponse countResponse =Es_Utils.client.count(
                new CountRequest(Es_Utils.INDEX_DEMO_01).types(Es_Utils.INDEX_DEMO_01_MAPPING)
        )
                .actionGet();
                */
        //预准备
        for (int i = 0; i < 10; i++) {
            FilterBuilder filterBuilder;
            CountResponse countResponse;
            String index = "";
            long start = System.currentTimeMillis();
            if (i >= 0) {
                filterBuilder = Es_FilterBuilders_DSL.cache();
                if (i % 2 == 0) {
                    index = "index_demo_02";
                } else {
                    index = "index_demo_01";
                }
                countResponse = Es_Utils.client.prepareCount(index)
                        .setTypes(Es_Utils.INDEX_DEMO_01_MAPPING)
                        .setQuery(new FilteredQueryBuilder(Es_QueryBuilders_DSL.matchAllQuery(), filterBuilder))
                        .execute()
                        .actionGet();
            } else {
                filterBuilder = Es_FilterBuilders_DSL.cache();
                countResponse = Es_Utils.client.prepareCount(Es_Utils.INDEX_DEMO_ALL)
                        .setTypes(Es_Utils.INDEX_DEMO_01_MAPPING)
                        .setQuery(new FilteredQueryBuilder(Es_QueryBuilders_DSL.matchAllQuery(), filterBuilder))
                        .execute()
                        .actionGet();
            }


            System.out.println("cache search . result :" + (System.currentTimeMillis() - start) + " \t, count : " + countResponse
                    .getCount());

        }

        System.out.println("searchByQuery_Count<{}>:" + "");
    }
}
