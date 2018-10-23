package com.gourd.erwa.design.alias.producer.instance;

import com.gourd.erwa.design.alias.producer.base.AbstractAliasProducerJdbc;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Alias producer game item.
 *
 * @author wei.Li by 2018/10/23
 */
public class AliasProducerGameItem extends AbstractAliasProducerJdbc {

    @Override
    public AliasProducerKeyEnum producerEnum() {
        return AliasProducerKeyEnum.AliasProducerGameItem;
    }


    @Override
    public Map<String, Map<String, String>> loadData() {
        final Map<String, Map<String, String>> data = new HashMap<>(10);

        final Map<String, String> game01Data = new HashMap<>(10);
        game01Data.put("101", "101道具");
        game01Data.put("102", "102道具");
        game01Data.put("103", "103道具");
        game01Data.put("104", "104道具");
        data.put("game01", game01Data);

        final Map<String, String> game02Data = new HashMap<>(10);
        game02Data.put("101", "101道具");
        game02Data.put("102", "102道具");
        game02Data.put("103", "103道具");
        game02Data.put("104", "104道具");
        data.put("game02", game02Data);

        return data;
    }
}
