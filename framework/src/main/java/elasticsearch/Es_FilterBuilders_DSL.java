package elasticsearch;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;


/**
 * Created by lw on 14-7-16.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * elasticsearch以提供了一个完整的Java查询dsl其余查询dsl。
 * FilterBuilders工厂构建
 * API:
 * <a>http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/query-dsl-filters.html</a>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_FilterBuilders_DSL {

    /**
     * and Filter
     * <p>
     * 一个过滤器匹配文档匹配的布尔组合其他过滤器。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder andFilter() {
        return FilterBuilders.andFilter(
                FilterBuilders.rangeFilter("age").from(1).to(1000),
                FilterBuilders.prefixFilter("name", "葫芦1493")
        );
    }

    /**
     * bool Filter
     * 一个过滤器匹配文档匹配的布尔组合其他过滤器。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder boolFilter() {
        return FilterBuilders.boolFilter()
                .must(FilterBuilders.termFilter("name", "葫芦1493娃"))
                .mustNot(FilterBuilders.rangeFilter("age").from(1000).to(3000))
                .should(FilterBuilders.termFilter("home", "山西省太原市7077街道"));
    }

    /**
     * exists filter
     * 一个过滤器来过滤字段唯一字段存在。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder existsFilter() {
        return FilterBuilders.existsFilter("home");
    }

    /**
     * ids filter
     * 创建一个新的id筛选提供 doc/映射类型。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder idsFilter() {
        return FilterBuilders.idsFilter(Es_Utils.INDEX_DEMO_01_MAPPING, "type2")
                .addIds("SNt0KdNbRdKmXJVaXfNxEA", "UDKtO4o9TgmDHIT4bk_OWw", "jkAZoHe9RWyjxyOnBCTdrw");

        // Type is optional
        //FilterBuilders.idsFilter().addIds("1", "4", "100");
    }

    /**
     * limit filter
     * 一个过滤器,用于限制结果提供的极限值(每个shard * 2 )。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder limitFilter() {
        return FilterBuilders.limitFilter(2);//返回碎片shard*2 个结果
    }

    /**
     * type filter
     * 过滤type
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder typeFilter() {
        return FilterBuilders.typeFilter(Es_Utils.INDEX_DEMO_01_MAPPING);
    }

    /**
     * TODO NotSolved
     * <p>
     * geo bounding box filter
     * 定义一个过滤器来过滤基于边界框左上角和右下角的位置/分
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder geoBoundingBoxFilter() {
        return FilterBuilders.geoBoundingBoxFilter("pin.location")
                .topLeft(40.73, -74.1)
                .bottomRight(40.717, -73.99);
    }


    /**
     * TODO NotSolved
     * <p>
     * geodistance filter
     * 一个过滤器来过滤基于一个特定的距离从一个特定的地理位置/点。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder geoDistanceFilter() {
        return FilterBuilders.geoDistanceFilter("pin.location")
                .point(40, -70)
                .distance(200, DistanceUnit.KILOMETERS)
                .optimizeBbox("memory")                    // Can be also "indexed" or "none"
                .geoDistance(GeoDistance.ARC);            // Or GeoDistance.PLANE
    }

    /**
     * TODO NotSolved
     * <p>
     * geo distance range filter
     * 一个过滤器来过滤基于一个特定的范围从一个特定的地理位置/点。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder geoDistanceRangeFilter() {
        return FilterBuilders.geoDistanceRangeFilter("pin.location")
                .point(40, -70)
                .from("200km")
                .to("400km")
                .includeLower(true)
                .includeUpper(false)
                .optimizeBbox("memory")                    // Can be also "indexed" or "none"
                .geoDistance(GeoDistance.ARC);            // Or GeoDistance.PLANE
    }

    /**
     * TODO NotSolved
     * <p>
     * geo polygon filter
     * 一个过滤器来过滤基于多边形定义为一组位置/分。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder geoPolygonFilter() {
        return FilterBuilders.geoPolygonFilter("pin.location")
                .addPoint(40, -70)
                .addPoint(30, -80)
                .addPoint(20, -90);
    }


    /**
     * TODO NotSolved
     * <p>
     * has child / has parent filters
     * 构造一个子过滤器,子类型和查询与文档、过滤的结果是父* *文档。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder hasChildFilter() {
        // Has Child
        FilterBuilders.hasChildFilter("blog_tag",
                QueryBuilders.termQuery("tag", "something"));

        // Has Parent
        return FilterBuilders.hasParentFilter("blog",
                QueryBuilders.termQuery("tag", "something"));
    }

    /**
     * match all filter
     * 一个过滤器匹配所有文件。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder matchAllFilter() {
        return FilterBuilders.matchAllFilter();
    }

    /**
     * missing filter
     * 一个过滤器来过滤字段唯一文件不存在。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder missingFilter() {
        return FilterBuilders.missingFilter("name")
                .existence(true)
                .nullValue(true);
    }

    /**
     * not filter
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder notFilter() {
        return FilterBuilders.notFilter(
                FilterBuilders.rangeFilter("age").from(1000).to(2000));
    }

    /**
     * or filter
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder orFilter() {
        return FilterBuilders.orFilter(
                FilterBuilders.termFilter("name", "葫芦1493娃"),
                FilterBuilders.termFilter("name", "葫芦5083娃")
        );
    }

    /**
     * prefix filter
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder prefixFilter() {
        return FilterBuilders.prefixFilter("name", "葫芦5083");
    }

    /**
     * query filter
     * 一个过滤器,仅包装一个查询。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder queryFilter() {
        return FilterBuilders.queryFilter(
                QueryBuilders.queryString("name")
        );
    }

    /**
     * range filter
     * 过滤器,限制搜索结果值在给定的范围内。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder rangeFilter() {
        FilterBuilders.rangeFilter("age")
                .from(1000)
                .to(2000)
                .includeLower(true)
                .includeUpper(false);

        // A simplified form using gte, gt, lt or lte
        return FilterBuilders.rangeFilter("age")
                .gte(1000)
                .lt(2000);
    }

    /**
     * script filter
     * 过滤器基于脚本的构建。
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder scriptFilter() {
        return FilterBuilders.scriptFilter(
                "doc['age'].value > param1"
        ).addParam("param1", 1000);
    }

    /**
     * term filter
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder termFilter() {
        return FilterBuilders.termFilter("name", "葫芦5083娃");
    }

    /**
     * terms filter
     * <p>
     * The execution option now has the following options :
     * <p>
     * plain
     * The default. Works as today. Iterates over all the terms, building a bit set matching it, and filtering. The total filter is cached.
     * <p>
     * fielddata
     * Generates a terms filters that uses the fielddata cache to compare terms.
     * This execution mode is great to use when filtering on a field that is already loaded into the fielddata cache from faceting, sorting, or index warmers.
     * When filtering on a large number of terms, this execution can be considerably faster than the other modes.
     * The total filter is not cached unless explicitly configured to do so.
     * <p>
     * bool
     * Generates a term filter (which is cached) for each term, and wraps those in a bool filter.
     * The bool filter itself is not cached as it can operate very quickly on the cached term filters.
     * <p>
     * and
     * Generates a term filter (which is cached) for each term, and wraps those in an and filter.
     * The and filter itself is not cached.
     * <p>
     * or
     * Generates a term filter (which is cached) for each term, and wraps those in an or filter.
     * The or filter itself is not cached. Generally, the bool execution mode should be preferred.
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder termsFilter() {
        return FilterBuilders.termsFilter("name", "葫芦5083娃", "葫芦3582娃")
                .execution("plain");     // 执行模式 Cane be either "plain", "bool" "and". Defaults to "plain".
    }

    /**
     * nested filter
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder nestedFilter() {
        return FilterBuilders.nestedFilter("obj1",
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("obj1.name", "blue"))
                        .must(QueryBuilders.rangeQuery("obj1.count").gt(5))
        );
    }

    /**
     * caching
     * 缓存 过滤器
     *
     * @return FilterBuilder
     */
    protected static FilterBuilder cache() {
        return FilterBuilders.andFilter(
                FilterBuilders.rangeFilter("age").from(1000).to(9000),
                FilterBuilders.prefixFilter("name", "葫芦3582")
        )
                .cache(true);//默认false
    }


    public static void main(String[] args) {
        Es_Utils.startupClient();
        try {
            searchTest(cache());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Es_Utils.shutDownClient();
        }
    }

    /**
     * @param filterBuilder filterBuilder
     */
    private static void searchTest(FilterBuilder filterBuilder) {
        //预准备执行搜索
        Es_Utils.client.prepareSearch(Es_Utils.INDEX_DEMO_01)
                .setTypes(Es_Utils.INDEX_DEMO_01_MAPPING)
                .setPostFilter(filterBuilder)
                .setFrom(0).setSize(20).setExplain(true)
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
}
