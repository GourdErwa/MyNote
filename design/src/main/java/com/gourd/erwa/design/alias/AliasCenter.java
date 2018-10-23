package com.gourd.erwa.design.alias;

import com.gourd.erwa.design.alias.producer.base.AliasProducer;
import com.gourd.erwa.design.alias.producer.base.AliasProducerKey;

import java.util.*;
import java.util.function.BiFunction;

/**
 * 别名替换中心.
 * 使用示例参考 {@linkplain com.gourd.erwa.design.alias.example.Examples}
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
    private final Map<String, AliasProducer> register = new HashMap<>();
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

        final AliasProducerKey aliasProducerKey = aliasProducer.aliasProducerKey();
        final String key = aliasProducerKey.key();
        final AliasProducer producer = this.register.get(key);
        if (producer != null) {
            throw new AliasException("aliasProducerKey:" + key + ", class " + aliasProducer.getClass().getSimpleName() + " already exists");
        }
        this.register.put(key, aliasProducer);
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
        System.out.println("AliasCenter start() start...");
        System.out.println("AliasCenter reLoadAliasProducer ...");
        this.reLoadAliasProducer();
        this.register.forEach(
                (s, aliasProducer) ->
                        System.out.println("AliasCenter registered key:" + s + " aliasProducer:" + aliasProducer.getClass().getSimpleName())
        );
        System.out.println("AliasCenter start() end...");
        return this;
    }

    /**
     * 校验是否启动，启动状态下抛出异常
     */
    private void checkStart() {
        if (this.start) {
            throw new AliasException("AliasCenter already start");
        }
    }

    /**
     * 校验是否启动，未启动状态下抛出异常
     */
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
     * @param function       转换函数 , 给予 [原始,别名] 2个字符，自由组合为结果字符
     * @return the string [ ]
     */
    public String[] aliasReplace(AliasCenterKey aliasCenterKey, String[] originals, BiFunction<String, String, String> function) {

        return this.aliasReplace(aliasCenterKey, Arrays.asList(originals), function).toArray(new String[0]);
    }

    /**
     * 别名替换.
     *
     * @param aliasCenterKey 别名替换 key
     * @param originals      待替换原始数据
     * @param function       转换函数 , 给予 [原始,别名] 2个字符，自由组合为结果字符
     * @return the list
     */
    public List<String> aliasReplace(AliasCenterKey aliasCenterKey, List<String> originals, BiFunction<String, String, String> function) {

        this.checkNotStart();

        final AliasProducer aliasProducer = register.get(aliasCenterKey.getAliasProducerKey().key());
        if (aliasProducer == null) {
            throw new RuntimeException("aliasCenterKey:" + aliasCenterKey.toString() + " doesn't exist");
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
