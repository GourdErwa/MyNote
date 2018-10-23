package com.gourd.erwa.design.alias;

import com.gourd.erwa.design.alias.producer.base.AliasProducerKey;

import java.util.Objects;

/**
 * The type Alias center key.
 *
 * @author wei.Li by 2018/10/23
 */
public class AliasCenterKey {

    /**
     * 应用名称
     */
    private String app;
    /**
     * 别名生产者 KEY
     */
    private AliasProducerKey aliasProducerKey;

    private AliasCenterKey(String app, AliasProducerKey aliasProducerKey) {
        this.app = app;
        this.aliasProducerKey = aliasProducerKey;
    }

    public static AliasCenterKey create(String app, AliasProducerKey aliasProducerKey) {
        return new AliasCenterKey(app, aliasProducerKey);
    }

    public String getApp() {
        return app;
    }

    public AliasProducerKey getAliasProducerKey() {
        return aliasProducerKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AliasCenterKey)) return false;
        AliasCenterKey that = (AliasCenterKey) o;
        return Objects.equals(getApp(), that.getApp()) &&
                Objects.equals(getAliasProducerKey(), that.getAliasProducerKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApp(), getAliasProducerKey());
    }
}
