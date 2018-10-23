package com.gourd.erwa.design.alias.example.instance;

import com.gourd.erwa.design.alias.producer.base.AliasProducer;
import com.gourd.erwa.design.alias.producer.base.AliasProducerKey;

/**
 * 支持别名生产者类型定义.
 * {@linkplain AliasProducer#aliasProducerKey()}
 *
 * @author wei.Li by 2018/10/23
 */
public enum AliasProducerKeyEnum implements AliasProducerKey {

    /**
     * 游戏道具别名替换.
     */
    AliasProducerGameItem,

    /**
     * 游戏道具操作别名替换.
     */
    AliasProducerGameItemOperate,

    /**
     * 游戏服列表操作别名替换.
     */
    AliasProducerGameServer;

    @Override
    public String key() {
        return this.name();
    }
}
