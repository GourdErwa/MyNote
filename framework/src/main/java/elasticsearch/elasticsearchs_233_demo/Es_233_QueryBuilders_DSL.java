package elasticsearch.elasticsearchs_233_demo;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.IndicesQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by lw on 14-7-15.
 * p
 * elasticsearch以提供了一个完整的Java查询dsl其余查询dsl。
 * QueryBuilders工厂构建
 * API:
 * ahp://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/query-dsl-queries.html/a
 */
public class Es_233_QueryBuilders_DSL {

    /**
     * match query 单个匹配
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder matchQuery() {
        return QueryBuilders.matchQuery("name", "葫芦4032娃");
    }


    /**
     * multimatch  query
     * 创建一个匹配查询的布尔型提供字段名称和文本。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder multiMatchQuery() {
        //现住址和家乡在【山西省太原市7429街道】的人
        return QueryBuilders.multiMatchQuery(
                "山西省太原市7429街道",     // Text you are looking for
                "home", "now_home"       // Fields you query on
        );
    }

    /**
     * boolean query and 条件组合查询
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder booleanQuery() {

        return QueryBuilders
                .boolQuery()
                .must(QueryBuilders.termQuery("name", "葫芦3033娃"))
                .must(QueryBuilders.termQuery("home", "山西省太原市7967街道"))
                .mustNot(QueryBuilders.termQuery("isRealMen", false))
                .should(QueryBuilders.termQuery("now_home", "山西省太原市"));
    }

    /**
     * ids query
     * 构造一个只会匹配的特定数据 id 的查询。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder idsQuery() {
        return QueryBuilders.idsQuery().ids("CHszwWRURyK08j01p0Mmug", "ojGrYKMEQCCPvh75lHJm3A");
    }

    /**
     * TODO NotSolved
     * p
     * constant score query
     * 另一个查询和查询,包裹查询只返回一个常数分数等于提高每个文档的查询。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder constantScoreQuery() {
        /*return // Using with Filters
                QueryBuilders.constantScoreQuery(FilterBuilders.termFilter("name", "kimchy"))
                        .boost(2.0f);@return QueryBuilder */

        // With Queries
        return QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("name", "葫芦3033娃"))
                .boost(2.0f);
    }

    /**
     * TODO NotSolved
     * p
     * disjunction max query
     * 一个生成的子查询文件产生的联合查询，
     * 而且每个分数的文件具有最高得分文件的任何子查询产生的，
     * 再加上打破平手的增加任何额外的匹配的子查询。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder disMaxQuery() {
        return QueryBuilders.disMaxQuery()
                .add(QueryBuilders.termQuery("name", "kimchy"))          // Your queries
                .add(QueryBuilders.termQuery("name", "elasticsearch"))   // Your queries
                .boost(1.2f)
                .tieBreaker(0.7f);
    }

    /**
     * fuzzy query
     * 使用模糊查询匹配文档查询。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder fuzzyQuery() {
        return QueryBuilders.fuzzyQuery("name", "葫芦3582");
    }

    /**
     * TODO NotSolved
     * p
     * has child / has parent
     * 父或者子的文档查询
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder hasChildQuery() {
        return // Has Child
                QueryBuilders.hasChildQuery("blog_tag",
                        QueryBuilders.termQuery("tag", "something"));

        // Has Parent
        /*return QueryBuilders.hasParentQuery("blog",
                QueryBuilders.termQuery("tag","something"));@return QueryBuilder */
    }

    /**
     * matchall query
     * 查询匹配所有文件。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder matchAllQuery() {
        return QueryBuilders.matchAllQuery();
    }

    /**
     * TODO NotSolved
     * p
     * more like this (field) query (mlt and mlt_field)
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder moreLikeThisQuery() {
        // mlt Query
        QueryBuilders.moreLikeThisQuery("home", "now_home")     // Fields
                .likeText("山西省太原市7429街道")                  // Text
                .minTermFreq(1)                                 // Ignore Threshold
                .maxQueryTerms(12);                             // Max num of Terms
        // in generated queries

        // mlt_field Query
        return QueryBuilders.moreLikeThisFieldQuery("home")              // Only on single field
                .likeText("山西省太原市7429街道")
                .minTermFreq(1)
                .maxQueryTerms(12);
    }

    /**
     * prefix query
     * 包含与查询相匹配的文档指定的前缀。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder prefixQuery() {
        return QueryBuilders.prefixQuery("name", "葫芦31");
    }

    /**
     * TODO NotSolved
     * p
     * querystring query
     * 　　查询解析查询字符串,并运行它。有两种模式,这种经营。
     * 第一,当没有添加字段(使用{ @link QueryStringQueryBuilder #字段(String)},将运行查询一次,非字段前缀
     * 　　将使用{ @link QueryStringQueryBuilder # defaultField(字符串)}。
     * 第二,当一个或多个字段
     * 　　(使用{ @link QueryStringQueryBuilder #字段(字符串)}),将运行提供的解析查询字段,并结合
     * 　　他们使用DisMax或者一个普通的布尔查询(参见{ @link QueryStringQueryBuilder # useDisMax(布尔)})。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder queryString() {
        return QueryBuilders.queryString("+kimchy -elasticsearch");
    }

    /**
     * range query
     * 查询相匹配的文档在一个范围。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder rangeQuery() {
        return QueryBuilders
                .rangeQuery("name")
                .from("葫芦1000娃")
                .to("葫芦3000娃")
                .includeLower(true)     //包括下界
                .includeUpper(false); //包括上界
    }

    /**
     * span queries (first, near, not, or, term)
     * 跨度查询
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder spanQueries() {
        // Span First
        QueryBuilders.spanFirstQuery(
                QueryBuilders.spanTermQuery("name", "葫芦580娃"),  // Query
                30000                                             // Max查询范围的结束位置
        );

        // Span Near TODO NotSolved
        QueryBuilders.spanNearQuery()
                .clause(QueryBuilders.spanTermQuery("name", "葫芦580娃")) // Span Term Queries
                .clause(QueryBuilders.spanTermQuery("name", "葫芦3812娃"))
                .clause(QueryBuilders.spanTermQuery("name", "葫芦7139娃"))
                .slop(30000)                                               // Slop factor
                .inOrder(false)
                .collectPayloads(false);

        // Span Not TODO NotSolved
        QueryBuilders.spanNotQuery()
                .include(QueryBuilders.spanTermQuery("name", "葫芦580娃"))
                .exclude(QueryBuilders.spanTermQuery("home", "山西省太原市2552街道"));

        // Span Or TODO NotSolved
        return QueryBuilders.spanOrQuery()
                .clause(QueryBuilders.spanTermQuery("name", "葫芦580娃"))
                .clause(QueryBuilders.spanTermQuery("name", "葫芦3812娃"))
                .clause(QueryBuilders.spanTermQuery("name", "葫芦7139娃"));

        // Span Term
        //return QueryBuilders.spanTermQuery("name", "葫芦580娃");
    }


    /**
     * term query
     * 一个查询相匹配的文件包含一个术语。。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder termQuery() {
        return QueryBuilders.termQuery("name", "葫芦580娃");
    }


    /**
     * terms query
     * 一个查询相匹配的多个value
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder termsQuery() {
        return QueryBuilders.termsQuery("name", // field
                "葫芦580娃", "葫芦3812娃")                 // values
                .minimumMatch(1);               // 设置最小数量的匹配提供了条件。默认为1。
    }

    /**
     * TODO NotSolved
     * p
     * top children  query
     * 构建了一种新的评分的子查询，与子类型和运行在子文档查询。这个查询的结果是，那些子父文档文件匹配。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder topChildrenQuery() {
        return QueryBuilders.topChildrenQuery(
                "blog_tag",                                 // field
                QueryBuilders.termQuery("name", "葫芦3812娃") // Query
        )
                .score("max")                               // max, sum or avg
                .factor(5)
                .incrementalFactor(2);
    }

    /**
     * wildcard query
     * 　　实现了通配符搜索查询。支持通配符*
     * 　　匹配任何字符序列(包括空), ? ,
     * 　　匹配任何单个的字符。注意该查询可以缓慢,因为它
     * 　　许多方面需要遍历。为了防止WildcardQueries极其缓慢。
     * 　　一个通配符词不应该从一个通配符*或?。
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder wildcardQuery() {
        return QueryBuilders.wildcardQuery("name", "葫芦*2娃");
    }

    /**
     * TODO NotSolved
     * p
     * nested query
     * 嵌套查询
     *
     * @return QueryBuilder
     */
    protected static QueryBuilder nestedQuery() {
        return QueryBuilders.nestedQuery("location",               // Path
                QueryBuilders.boolQuery()                      // Your query
                        .must(QueryBuilders.matchQuery("location.lat", 0.962590433140581))
                        .must(QueryBuilders.rangeQuery("location.lon").lt(0.00000000000000000003))
        )
                .scoreMode("total");                  // max, total, avg or none
    }

    /**
     * indices query
     * 索引查询
     *
     * @return QueryBuilder
     */
    protected static IndicesQueryBuilder indicesQuery() {
        // Using another query when no match for the main one
        QueryBuilders.indicesQuery(
                QueryBuilders.termQuery("name", "葫芦3812娃"),
                Es_233_Utils.INDEX_DEMO_01, "index2"
        )       //设置查询索引上执行时使用不匹配指数
                .noMatchQuery(QueryBuilders.termQuery("age", "葫芦3812娃"));


        // Using all (match all) or none (match no documents)
        return QueryBuilders.indicesQuery(
                QueryBuilders.termQuery("name", "葫芦3812娃"),
                Es_233_Utils.INDEX_DEMO_01, "index2"
        )      // 设置不匹配查询,可以是 all 或者 none
                .noMatchQuery("none");
    }


    public static void main(String[] args) {
        Es_233_Utils.startupClient();
        try {
            searchTest(indicesQuery());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Es_233_Utils.shutDownClient();
        }
    }

    private static void searchTest(QueryBuilder queryBuilder) {
        //预准备执行搜索
        Es_233_Utils.client.prepareSearch(Es_233_Utils.INDEX_DEMO_01)
                .setTypes(Es_233_Utils.INDEX_DEMO_01_MAPPING)
                .setQuery(queryBuilder)
                .setFrom(0).setSize(20).setExplain(true)
                .execute()
                //注册监听事件
                .addListener(new ActionListener<SearchResponse>() {
                    @Override
                    public void onResponse(SearchResponse response) {

                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }
}
