/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package elasticsearch.elasticsearchs_233_demo;

import com.google.common.io.Files;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.replication.ReplicationType;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * @author wei.Li by 14/9/29.
 */
public class Copy {

    private static void copy() {


        File file = new File("/lw/elasticsearch.txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //预准备
        SearchResponse searchResponse = Es_233_Utils.client.prepareSearch("analyzier_ezsonar")
                .setTypes("message")
                .setSize(10000)
                .execute()
                .actionGet();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            try {
                Files.append(searchHit.getSourceAsString() + "\r\n", file, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       /* SearchResponse response = Es_233_Utils.client.prepareSearch("analyzier_ezsonar")
                //加上这个据说可以提高性能，但第一次却不返回结果
                .setSearchType(SearchType.SCAN)
                        //实际返回的数量为5*index的主分片格式
                .setSize(5)
                        //这个游标维持多长时间
                .setScroll(TimeValue.timeValueMillis(6000))
                .execute().actionGet();
        //第一次查询，只返回数量和一个scrollId
        System.out.println(response.getHits().getTotalHits());
        System.out.println(response.getHits().hits().length);
        //第一次运行没有结果
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("------------------------------");
        while (true) {
            //使用上次的scrollId继续访问
            response = Es_233_Utils.client.prepareSearchScroll(response.getScrollId())
                    .setScroll(TimeValue.timeValueMillis(6000))
                    .execute()
                    .actionGet();
            System.out.println(response.getHits().getTotalHits());
            System.out.println(response.getHits().hits().length);
            if (response.getHits().hits().length == 0) {
                break;
            }
            for (SearchHit hit : response.getHits()) {
                System.out.println(hit.getSourceAsString());
            }
        }*/
    }

    public static void main(String[] args) {
        Es_233_Utils.startupClient();
        copy();
    }

    /**
     * 把数据从index复制到target
     *
     * @param source
     * @param target
     */
    public void move(String source, String target) {
        QueryBuilder qb = matchAllQuery();
        int count = 0;

        SearchResponse scrollResp = Es_233_Utils.client.prepareSearch(source)
                .setSearchType(SearchType.SCAN)
                .setScroll(new TimeValue(10000))
                .setQuery(qb)
                .setSize(100).execute().actionGet();

        while (true) {
            scrollResp = Es_233_Utils.client.prepareSearchScroll(scrollResp.getScrollId())
                    .setScroll(new TimeValue(60000)).execute().actionGet();

            // No more hits.
            if (scrollResp.getHits().hits().length == 0) {
                break;
            }
            Date startDate = new Date();
            final BulkRequestBuilder request = Es_233_Utils.client.prepareBulk();
            for (SearchHit hit : scrollResp.getHits()) {
//                Map<String, Object> doc = hit.getSource();
//                String oldId = hit.getId();

                BytesReference doc = hit.getSourceRef();
                count++;
            }
            Date endDate = new Date();

            request.setConsistencyLevel(WriteConsistencyLevel.ONE);
            request.setReplicationType(ReplicationType.ASYNC);
            request.setRefresh(false);

        }
    }
}
