package com.gourd.erwa.design.alias.producer.base;

import com.gourd.erwa.design.alias.AliasCenterKey;

import java.util.List;

/**
 * 支持别名替换生产者接口.
 *
 * @author wei.Li by 2018/10/23
 */
public interface AliasProducer {

    /**
     * 生产者指定 KEY.
     *
     * @return the alias producer enum
     */
    AliasProducerKey aliasProducerKey();

    /**
     * 载入别名替换数据.
     */
    void load();

    /**
     * 重新载入别名替换数据.
     */
    void reLoad();

    /**
     * 别名替换.
     *
     * @param aliasCenterKey 别名替换指定替换内容
     * @param original       待替换原始内容
     * @return 替换结果 string
     */
    String alias(AliasCenterKey aliasCenterKey, String original);

    /**
     * 别名替换.
     *
     * @param aliasCenterKey 别名替换指定替换内容
     * @param originals      待替换原始内容
     * @return 替换结果 list
     */
    List<String> alias(AliasCenterKey aliasCenterKey, List<String> originals);

    /**
     * 关闭资源.
     */
    void close();

}
