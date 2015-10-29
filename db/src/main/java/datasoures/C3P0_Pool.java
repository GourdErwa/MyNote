package datasoures;


import java.lang.reflect.Field;
import java.sql.SQLException;

/**
 * Created by lw on 14-5-5.
 * C3P0_Pool 数据库连接池
 */
public class C3P0_Pool {


    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       /* //连接Mysql数据库
        DataSource dataSource = DataSources.unpooledDataSource("jdbc:mysql://localhost:3306/test", "root", "root");
        //创建数据库连接池
        DataSource pooled = DataSources.pooledDataSource(dataSource);
        Connection connection;
        connection = pooled.getConnection();

        System.out.println("connection Class Type is ->" + connection.getClass().getName());
        System.out.println("Inner connection Class Type is ->" + getInnter(connection).getClass().getName());*/
    }

    private static Object getInnter(Object o) {
        Object o1 = null;
        Field field;
        try {
            field = o.getClass().getDeclaredField("inner");
            field.setAccessible(true);
            o1 = field.get(o);//取得内部包装类
            field.setAccessible(false);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return o1;
    }

}
