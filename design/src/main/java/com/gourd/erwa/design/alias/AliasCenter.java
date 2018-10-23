package com.gourd.erwa.design.alias;

import com.google.common.collect.Lists;
import com.gourd.erwa.design.alias.producer.base.AliasProducer;
import com.gourd.erwa.design.alias.producer.instance.AliasProducerKeyEnum;

import java.util.*;
import java.util.function.BiFunction;

/**
 * 别名替换中心.
 *
 * @author wei.Li by 2018/10/23
 */
public class AliasCenter {

    /**
     * 转换函数
     * (original, alias) -> original(alias)
     */
    public static final BiFunction<String, String, String> BI_FUNCTION_PARENTHESES = (original, alias) -> original + "(" + alias + ")";
    /**
     * 注册别名生产者
     */
    private final Map<AliasProducerKeyEnum, AliasProducer> register = new HashMap<>();
    private boolean start = false;

    private AliasCenter() {
    }

    /**
     * Create alias center.
     *
     * @return the alias center
     */
    public static AliasCenter create() {
        return new AliasCenter();
    }

    /**
     * 注册生产者.
     *
     * @param aliasProducer the alias producer
     * @return the alias center
     */
    public AliasCenter register(AliasProducer aliasProducer) {

        this.checkStart();

        final AliasProducerKeyEnum aliasProducerKeyEnum = aliasProducer.producerEnum();
        final AliasProducer producer = this.register.get(aliasProducerKeyEnum);
        if (producer != null) {
            throw new AliasException("aliasProducerEnum:" + aliasProducerKeyEnum + " already exists");
        }
        this.register.put(aliasProducerKeyEnum, aliasProducer);
        return this;
    }

    /**
     * 启动生产者中心.
     *
     * @return the alias center
     */
    public synchronized AliasCenter start() {
        this.checkStart();
        start = true;
        this.reLoadAliasProducer();
        return this;
    }

    private void checkStart() {
        if (this.start) {
            throw new AliasException("AliasCenter already start");
        }
    }

    private void checkNotStart() {
        if (!this.start) {
            throw new AliasException("AliasCenter failure , please invoke start()");
        }
    }

    /**
     * 刷新所有生产者别名数据.
     */
    public void reLoadAliasProducer() {
        this.checkNotStart();
        this.register.values().forEach(AliasProducer::reLoad);
    }

    /**
     * 别名替换.
     *
     * @param aliasCenterKey 别名替换 key
     * @param originals      待替换原始数据
     * @param function       转换函数
     * @return the string [ ]
     */
    public String[] aliasReplace(AliasCenterKey aliasCenterKey, String[] originals, BiFunction<String, String, String> function) {

        return this.aliasReplace(aliasCenterKey, Lists.newArrayList(originals), function).toArray(new String[0]);
    }

    /**
     * 别名替换.
     *
     * @param aliasCenterKey 别名替换 key
     * @param originals      待替换原始数据
     * @param function       转换函数
     * @return the list
     */
    public List<String> aliasReplace(AliasCenterKey aliasCenterKey, List<String> originals, BiFunction<String, String, String> function) {

        this.checkNotStart();

        final AliasProducer aliasProducer = register.get(aliasCenterKey.getAliasProducerKeyEnum());
        if (aliasProducer == null) {
            throw new RuntimeException("aliasCenterKey:" + aliasCenterKey.toString() + " c exists");
        }
        final List<String> r = new ArrayList<>();
        final List<String> alias = aliasProducer.alias(aliasCenterKey, originals);

        for (int i = 0; i < originals.size(); i++) {
            final String original = originals.get(i);
            final String alia = alias.get(i);
            r.add(function.apply(original, alia));
        }
        return r;
    }

    /**
     * 关闭资源.
     */
    public void close() {
        for (AliasProducer aliasProducer : this.register.values()) {
            try {
                aliasProducer.close();
            } catch (Exception ignored) {
            }
        }
    }
}
