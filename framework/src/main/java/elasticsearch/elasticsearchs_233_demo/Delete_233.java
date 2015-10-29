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

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsRequest;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;

import java.util.Map;

/**
 * 删除指定 index
 *
 * @author wei.Li by 14/10/8.
 */
public class Delete_233 {


    /**
     * 查询 index
     *
     * @param indices 查询的条件，模糊匹配
     * @return
     */
    public static Map<String, IndexStats> getAllIndices(String... indices) {

        IndicesStatsRequest indicesStatsRequest
                = new IndicesStatsRequest();
        if (indices.length != 0)
            indicesStatsRequest = indicesStatsRequest.indices(indices);

        ActionFuture<IndicesStatsResponse> isr = Es_233_Utils.client.admin()
                .indices()
                .stats(indicesStatsRequest);

        return isr.actionGet().getIndices();
    }

    /**
     * 删除指定索引
     *
     * @param indices 要删除的索引
     */
    private static void deleteIndexResponse(String... indices) {
        DeleteIndexResponse deleteIndexResponse = Es_233_Utils.client
                .admin().indices().delete(new DeleteIndexRequest().indices(indices)).actionGet();

        System.out.println("isAcknowledged:" + deleteIndexResponse.isAcknowledged());
    }

    public static void main(String[] args) {
        Es_233_Utils.startupClient();

        /*//要删除的 index
        Set<String> strings = new HashSet<>();

        //查询所有 index
        Map<String, IndexStats> stringIndexStatsMap
                = getAllIndices();
        Set<String> stringSet = stringIndexStatsMap.keySet();
        for (String s : stringSet) {
            if (s.contains("ezsonar_2014-08-*")) {
                strings.add(s);
                System.out.println(s);
            }
        }

        String[] strings1 = new String[strings.size()];*/
        //deleteIndexResponse(strings.toArray(strings1));
        deleteIndexResponse("recent_ezsonar_*");

    }
}
