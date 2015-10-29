package elasticsearch;

/**
 * Created by lw on 14-7-15.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 官方JAVA-API
 * http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_Test {

    public static void main(String[] dfd) {
        Es_Utils.startupClient();
        try {
            //Es_BuildIndex.buildIndexMapping();
            //buildIndex(User.getOneRandomUser());
           /* for (int i = 0; i < 5; i++) {
                Thread.sleep(2000);
                Es_BuildIndex.buildBulkIndex(User.getRandomUsers(1000));
            }*/
            //searchById("5_XDJBA-TBOra4a7oyiYrA");
            //Es_Search.searchByQuery();
            //Es_Search.searchByQuery_Count();
            //updateByQuery();
            // Es_Update_Del.deleteIndexByQuery();
            //Es_Utils.getAllIndices();
            //Es_BuildIndex.createAliases("aliases_test", "index_demo_01", "index_demo_02");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Es_Utils.shutDownClient();
        }
    }
}
