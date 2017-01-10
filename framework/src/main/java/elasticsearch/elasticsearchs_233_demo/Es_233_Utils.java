package elasticsearch.elasticsearchs_233_demo;

import com.gourd.erwa.util.conver.ConvertUtils0;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsRequest;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lw on 14-7-15.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * startup and shutDownClient ----》Client
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_233_Utils {


    //直接传入 index_demo_*  即按前缀* 查询
    protected static final String INDEX_DEMO_ALL = "index_demo_*";
    protected static final String INDEX_DEMO_01 = "index_demo_01";
    protected static final String INDEX_DEMO_01_MAPPING = "message";
    protected static Client client;
    protected static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA),
            DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.CHINA);

    /**
     * startup Transport Client
     * 启动es
     */
    protected static void startupClient() {
        /**
         * 可以设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
         * 这样做的好 处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器。
         */
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.sniff", true).build();
        client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("192.168.1.233", 9300));
        //.addTransportAddress(new InetSocketTransportAddress("10.211.55.4", 9300));
    }

    /**
     * on shutDownClient
     * 停止es
     */
    protected static void shutDownClient() {
        client.close();
    }

    /**
     * 获取所有index
     */
    protected static void getAllIndices() {
        ActionFuture<IndicesStatsResponse> isr = client.admin().indices().stats(new IndicesStatsRequest().all());
        Set<String> set = isr.actionGet().getIndices().keySet();
        String[] strings = set.toArray(new String[set.size()]);
        Arrays.sort(strings);
        for (String string : strings) {
            if (!string.startsWith(".")) {
                System.out.println(string);
            }
        }
    }

    /**
     * 查询indices 是否存在
     *
     * @param indices 索引名称
     * @return boolean
     * @see #aliasTarget(String)
     */
    protected static boolean isExistsIndices(String... indices) {
        return client.admin().indices().exists(new IndicesExistsRequest(indices)).actionGet().isExists();
    }

    /**
     * 打印SearchResponse结果集
     *
     * @param response SearchResponse
     */
    protected static void writeSearchResponse(SearchResponse response) {
        SearchHit[] searchHitsByPrepareSearch = response.getHits().hits();
        //获取结果集打印
        for (SearchHit searchHit : searchHitsByPrepareSearch) {
            System.out.println(searchHit.getSourceAsString());
        }
    }

    /**
     * SearchResponse结果集转Map
     * 获取某指标的值set去重打印
     *
     * @param response response
     * @param filed    获取指标的字段名称
     */
    protected static void writeSearchResponseToMap(SearchResponse response, String filed) {
        if (null == response) {
            System.out.println("~~~~~~~~~ response is null ~~~~~~~~~");
            return;
        }
        SearchHit[] searchHitsByPrepareSearch = response.getHits().hits();
        Set<String> stringSet = new TreeSet<>();
        //获取结果集打印
        for (SearchHit searchHit : searchHitsByPrepareSearch) {
            String s = searchHit.getSourceAsString();
            Map map = ConvertUtils0.parseJsonToMap(s);
            Set set = map.keySet();
/*
            set.stream().filter(s1 -> s1.equals(filed)).forEach(s1 -> {
                long aLong = Long.parseLong((map.get(s1) + ""));
                stringSet.add(DATETIME_FORMATTER.format(new Date(aLong * 1000L)));
            });
*/
        }
        //stringSet.forEach(System.out::println);
    }

    /**
     * 获取某个alias指向的index
     *
     * @param alias alias
     * @return index
     */
    public String aliasTarget(String alias) {
        // The ES return value of this has an awkward format: The first key of the hash is the target index. Thanks.
        // return client.admin().indices().getAliases(new IndicesGetAliasesRequest(alias)).actionGet().getAliases().keysIt().next();
        Iterator<String> itr = client.admin()
                .indices()
                .getAliases(new GetAliasesRequest(alias))
                .actionGet()
                .getAliases()
                .keysIt();
        if (itr.hasNext()) {
            // 有alias
            String currentIndexName = itr.next();
            System.out.println(currentIndexName);
            return currentIndexName;
        } else
            return null;
    }


}
