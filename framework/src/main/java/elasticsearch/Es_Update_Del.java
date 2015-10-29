package elasticsearch;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import java.io.IOException;

/**
 * Created by lw on 14-7-15.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 修改 and 删除索引记录
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_Update_Del {


    /**
     * 删除 创建的索引index
     *
     * @param indexs 要删除的索引数组
     * @return 是否删除成功
     */
    protected static boolean deleteIndexByName(String... indexs) {
        DeleteIndexResponse deleteIndexResponse = Es_Utils.client
                .admin().indices().delete(new DeleteIndexRequest(indexs)).actionGet();

        return deleteIndexResponse.isAcknowledged();
    }

    /**
     * 通过Id删除索引记录
     *
     * @param id id
     */
    protected static void deleteIndexById(String id) {

        DeleteResponse responsedd = Es_Utils.client.prepareDelete(Es_Utils.INDEX_DEMO_01, Es_Utils.INDEX_DEMO_01_MAPPING, id)
                .setOperationThreaded(false)
                .execute()
                .actionGet();
    }

    /**
     * 通过Query条件查询后删除索引记录
     */
    protected static void deleteIndexByQuery() {

        QueryBuilder query = new QueryStringQueryBuilder("葫芦3582娃").field("name");
        Es_Utils.client.prepareDeleteByQuery(Es_Utils.INDEX_DEMO_01)
                .setTypes(Es_Utils.INDEX_DEMO_01_MAPPING)
                .setQuery(query)
                .execute()

                .addListener(new ActionListener<DeleteByQueryResponse>() {
                    @Override
                    public void onResponse(DeleteByQueryResponse indexDeleteByQueryResponses) {
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });

    }


    /**
     * 修改
     *
     * @throws IOException IOException
     */
    protected static void updateByQuery() throws IOException {

        boolean isCreatedByUpdate = Es_Utils.client.update(new UpdateRequest(Es_Utils.INDEX_DEMO_01, Es_Utils.INDEX_DEMO_01_MAPPING, "TKLkVot6SJu429zpJbFN3g")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "liw")
                        .field("age", "25")
                        .endObject()
                )
        )
                .actionGet()
                .isCreated();
        //预准备
        Es_Utils.client.prepareUpdate(Es_Utils.INDEX_DEMO_01, Es_Utils.INDEX_DEMO_01_MAPPING, "TKLkVot6SJu429zpJbFN3g")
                .setDoc("age", "18")
                .execute()
                .actionGet();
    }

}
