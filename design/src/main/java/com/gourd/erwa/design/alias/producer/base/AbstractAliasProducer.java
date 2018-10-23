package com.gourd.erwa.design.alias.producer.base;

import com.gourd.erwa.design.alias.AliasCenterKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 别名生产者抽象父类.
 * 实现别名替换相关逻辑
 *
 * @author wei.Li by 2018/10/23
 */
public abstract class AbstractAliasProducer implements AliasProducer {

    private final Map<String, Map<String, String>> data = new HashMap<>();

    @Override
    public final void load() {
        this.data.clear();
        this.data.putAll(this.loadData());
    }

    /**
     * Load data map.
     *
     * @return the map
     */
    public abstract Map<String, Map<String, String>> loadData();

    @Override
    public final void reLoad() {
        this.load();
    }

    @Override
    public String alias(AliasCenterKey aliasCenterKey, String original) {
        final Map<String, String> map = this.data.get(aliasCenterKey.getApp());
        String alias = null;
        if (map != null) {
            alias = map.get(original);
        }
        return (alias == null) ? original : alias;
    }

    @Override
    public List<String> alias(AliasCenterKey aliasCenterKey, List<String> originals) {
        return originals.stream().map(original -> alias(aliasCenterKey, original)).collect(Collectors.toList());
    }

    @Override
    public void close() {
        //nothing
    }

}
