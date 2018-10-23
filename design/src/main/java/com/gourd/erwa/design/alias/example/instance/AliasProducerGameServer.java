package com.gourd.erwa.design.alias.example.instance;

import com.gourd.erwa.design.alias.producer.base.AbstractAliasProducerJdbc;
import com.gourd.erwa.design.alias.producer.base.AliasProducerKey;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏服列表操作别名替换.
 *
 * @author wei.Li by 2018/10/23
 */
public class AliasProducerGameServer extends AbstractAliasProducerJdbc {

    @Override
    public AliasProducerKey aliasProducerKey() {
        return AliasProducerKeyEnum.AliasProducerGameServer;
    }

    @Override
    public Map<String, Map<String, String>> loadData() {
        final Map<String, Map<String, String>> data = new HashMap<>(10);

        final Map<String, String> game01Data = new HashMap<>(10);
        game01Data.put("101", "game01-101服");
        game01Data.put("102", "game01-102服");
        game01Data.put("103", "game01-103服");
        game01Data.put("104", "game01-104服");
        data.put("game01", game01Data);

        final Map<String, String> game02Data = new HashMap<>(10);
        game02Data.put("101", "game02-101服");
        game02Data.put("102", "game02-102服");
        game02Data.put("103", "game02-103服");
        game02Data.put("104", "game02-104服");
        data.put("game02", game02Data);

        return data;
    }

}
