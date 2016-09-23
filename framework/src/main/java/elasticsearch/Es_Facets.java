package elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacetBuilder;
import org.elasticsearch.search.facet.filter.FilterFacet;
import org.elasticsearch.search.facet.filter.FilterFacetBuilder;
import org.elasticsearch.search.facet.geodistance.GeoDistanceFacet;
import org.elasticsearch.search.facet.geodistance.GeoDistanceFacetBuilder;
import org.elasticsearch.search.facet.histogram.HistogramFacet;
import org.elasticsearch.search.facet.histogram.HistogramFacetBuilder;
import org.elasticsearch.search.facet.query.QueryFacet;
import org.elasticsearch.search.facet.query.QueryFacetBuilder;
import org.elasticsearch.search.facet.range.RangeFacet;
import org.elasticsearch.search.facet.range.RangeFacetBuilder;
import org.elasticsearch.search.facet.statistical.StatisticalFacet;
import org.elasticsearch.search.facet.statistical.StatisticalFacetBuilder;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.elasticsearch.search.facet.termsstats.TermsStatsFacet;
import org.elasticsearch.search.facet.termsstats.TermsStatsFacetBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-15.
 * <p>
 * 搜索 Facets分组统计
 * <p>
 * <a>http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/java-facets.html</a>
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/search-facets.html
 */
public class Es_Facets {


    /**
     * termsFacet
     * 字段分组-Count 出现次数统计
     */
    private static void termsFacet() {

        TermsFacetBuilder termsFacetBuilder = FacetBuilders.termsFacet("TermsFacetBuilder")
                .field("name")
                .size(Integer.MAX_VALUE);//取得 name分组后COUNT值，显示size值

        TermsFacet termsFacet = (TermsFacet) searchByQuery_Facets(termsFacetBuilder).get("TermsFacetBuilder");
        System.out.println("termsFacet.getTotalCount():" + termsFacet.getTotalCount());// Total terms doc count
        System.out.println("termsFacet.getOtherCount():" + termsFacet.getOtherCount());// Not shown terms doc count
        System.out.println("termsFacet.getMissingCount():" + termsFacet.getMissingCount());// Without term doc count
        for (TermsFacet.Entry entry : termsFacet) {
            // Term -》Doc count
            System.out.println("key :" + entry.getTerm() + "\t value:" + entry.getCount());
        }
    }

    /**
     * termsStatsFacet
     * <p>
     * 统计 key 下面的 value 的值 （Max/Min,Count）
     * 以 key 分组
     * 对 value 求一些函数
     */
    private static void termsStatsFacet() {
        TermsStatsFacetBuilder termsStatsFacetBuilder = FacetBuilders.termsStatsFacet("TermsStatsFacetBuilder")
                .keyField("")
                .valueField("");

        TermsStatsFacet entries = (TermsStatsFacet) searchByQuery_Facets(termsStatsFacetBuilder).get("TermsStatsFacetBuilder");
        System.out.println("Without term doc count -> " + entries.getMissingCount());
        // For each entry
        for (TermsStatsFacet.Entry entry : entries) {
            entry.getTerm();            // Term
            entry.getCount();           // Doc count
            entry.getMin();             // Min value
            entry.getMax();             // Max value
            entry.getMean();            // Mean
            entry.getTotal();           // Sum of values
            System.out.println(entry);
        }

    }

    /**
     * rangeFacet
     * 分组范围性统计
     */
    private static void rangeFacet() {
        /**
         * 20-30
         * 10-∞
         * -∞-20
         * 三个范围做统计
         */
        RangeFacetBuilder rangeFacetBuilder = FacetBuilders.rangeFacet("RangeFacetBuilder")
                .field("age")
                .addRange(20, 30)
                .addUnboundedFrom(10)
                .addUnboundedTo(20);

        RangeFacet rangeFacet = (RangeFacet) searchByQuery_Facets(rangeFacetBuilder).get("RangeFacetBuilder");

        for (RangeFacet.Entry entry : rangeFacet) {
            // sr is here your SearchResponse object
            entry.getFrom();    // Range from requested
            entry.getTo();      // Range to requested
            entry.getCount();   // Doc count
            entry.getMin();     // Min value
            entry.getMax();     // Max value
            entry.getMean();    // Mean
            entry.getTotal();   // Sum of values
            System.out.println(entry.toString());
        }
    }

    /**
     * histogramFacet
     * 直方图统计- 按照时间间隔
     */
    private static void histogramFacet() {
        HistogramFacetBuilder histogramFacetBuilder = FacetBuilders.histogramFacet("HistogramFacetBuilder")
                .field("birthday") //生日分组统计
                .interval(1, TimeUnit.MINUTES); //按分钟数分组
        HistogramFacet histogramFacet = (HistogramFacet) searchByQuery_Facets(histogramFacetBuilder).get("HistogramFacetBuilder");
        // For each entry -Key (X-Axis) -Doc count (Y-Axis)
        for (HistogramFacet.Entry entry : histogramFacet) {
            System.out.println("entry.getKey()->" + entry.getKey() + "\t entry.getCount()->" + entry.getCount());
        }
    }

    /**
     * dateHistogramFacet
     * 数据直方图统计- 按照时间间隔
     */
    private static void dateHistogramFacet() {
        DateHistogramFacetBuilder dateHistogramFacetBuilder = FacetBuilders.dateHistogramFacet("DateHistogramFacetBuilder")
                .field("birthday")// Your com.gourd.erwa.date field
                .interval("minute");// You can also use "quarter", "month", "week", "day",
        // "hour" and "minute" or notation like "1.5h" or "2w"
        DateHistogramFacet histogramFacet
                = (DateHistogramFacet) searchByQuery_Facets(dateHistogramFacetBuilder).get("DateHistogramFacetBuilder");
        for (DateHistogramFacet.Entry entry : histogramFacet) {
            entry.getTime();    // Date in ms since epoch (X-Axis)
            entry.getCount();   // Doc count (Y-Axis)
        }
    }

    /**
     * filterFacet
     * 过滤条件后统计
     */
    private static void filterFacet() {
        FilterFacetBuilder filterFacetBuilder = FacetBuilders.filterFacet("FilterFacetBuilder",
                FilterBuilders.termFilter("name", "葫芦747娃"));    // 返回命中“指定filter”的结果数。
        FilterFacet filterFacet = (FilterFacet) searchByQuery_Facets(filterFacetBuilder).get("FilterFacetBuilder");
        System.out.println("filterFacet.getCount()->" + filterFacet.getCount());// Number of docs that matched
    }

    /**
     * queryFacet
     * 过滤条件后统计
     */
    private static void queryFacet() {
        //Query 条件过滤后统计
        QueryFacetBuilder queryFacetBuilder = FacetBuilders.queryFacet("QueryFacetBuilder",
                QueryBuilders.matchQuery("age", 29));
        QueryFacet queryFacet = (QueryFacet) searchByQuery_Facets(queryFacetBuilder).get("QueryFacetBuilder");
        System.out.println("queryFacet.getCount()->" + queryFacet.getCount());// Number of docs that matched
    }


    /**
     * statisticalFacet
     * 数学统计 - StatisticalFacet需要作用在数值型字段上面，他会统计总数、总和、最值、均值等
     */
    private static void statisticalFacet() {
        StatisticalFacetBuilder statisticalFacetBuilder = FacetBuilders.statisticalFacet("StatisticalFacetBuilder")
                .field("height");
        StatisticalFacet statisticalFacet =
                (StatisticalFacet) searchByQuery_Facets(statisticalFacetBuilder).get("StatisticalFacetBuilder");

        statisticalFacet.getCount();           // Doc count
        statisticalFacet.getMin();             // Min value
        statisticalFacet.getMax();             // Max value
        statisticalFacet.getMean();            // Mean
        statisticalFacet.getTotal();           // Sum of values
        statisticalFacet.getStdDeviation();    // Standard Deviation
        statisticalFacet.getSumOfSquares();    // Sum of Squares
        statisticalFacet.getVariance();        // Variance
    }

    /**
     * geoDistanceFacet
     * 数学统计 - StatisticalFacet需要作用在数值型字段上面，他会统计总数、总和、最值、均值等
     */
    private static void geoDistanceFacet() {
        GeoDistanceFacetBuilder geoDistanceFacetBuilder = FacetBuilders.geoDistanceFacet("GeoDistanceFacetBuilder")
                .field("location")                   // Field containing coordinates we want to compare with
                .point(40, -70)                     // Point from where we start (0)
                .addUnboundedFrom(10)               // 0 to 10 km (excluded)
                .addRange(10, 20)                   // 10 to 20 km (excluded)
                .addRange(20, 100)                  // 20 to 100 km (excluded)
                .addUnboundedTo(100)                // from 100 km to infinity (and beyond ;-) )
                .unit(DistanceUnit.DEFAULT);        // All distances are in kilometers. Can be MILES

        GeoDistanceFacet geoDistanceFacet =
                (GeoDistanceFacet) searchByQuery_Facets(geoDistanceFacetBuilder).get("GeoDistanceFacetBuilder");

        // For each entry
        for (GeoDistanceFacet.Entry entry : geoDistanceFacet) {
            entry.getFrom();            // Distance from requested
            entry.getTo();              // Distance to requested
            entry.getCount();           // Doc count
            entry.getMin();             // Min value
            entry.getMax();             // Max value
            entry.getTotal();           // Sum of values
            entry.getMean();            // Mean
        }
    }

    /**
     * 搜索，Query搜索API
     * Facets 查询-对搜索结果进行计算处理API
     */
    private static Map searchByQuery_Facets(FacetBuilder facetBuilder) {

        SearchResponse response = Es_Utils.client.prepareSearch(Es_Utils.INDEX_DEMO_01)
                .setTypes(Es_Utils.INDEX_DEMO_01_MAPPING)
                .setQuery(QueryBuilders.termQuery("age", 29))
                //.setPostFilter(FilterBuilders.rangeFilter("age").gt(98))
                .addFacet(facetBuilder)
                .setSize(1000)
                .execute()
                .actionGet();
        //Es_Utils.writeSearchResponse(response);
        Facets facets = response.getFacets();
        return facets.getFacets();
    }

    public static void main(String[] args) {
        Es_Utils.startupClient();
        dateHistogramFacet();
    }
}
