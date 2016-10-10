package com.gourd.erwa.design.enjoy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lw on 14-5-1.
 * 享元模式
 * 由不同的名字获取对应的数据库连接
 * 与对象池的区别是每个name有自己共享对应的对象。
 * <p>
 * javax.sql.DataSource 以数据库连接池为实际class
 */
class EnjoyFactory {

    private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();

    public static DataSource getgetConnectionByName(String dataSourcesName) {
        DataSource dataSource = dataSourceMap.get(dataSourcesName);
        if (dataSource == null) {
            DataSourcesImpl sources = new DataSourcesImpl(dataSourcesName);
            dataSource = sources.getDataSourceByName();
            dataSourceMap.put(dataSourcesName, dataSource);
        }
        return dataSource;
    }
}
