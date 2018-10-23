package com.gourd.erwa.design.alias.example;

import com.gourd.erwa.design.alias.AliasCenter;
import com.gourd.erwa.design.alias.AliasCenterKey;
import com.gourd.erwa.design.alias.producer.instance.AliasProducerKeyEnum;
import com.gourd.erwa.design.alias.producer.instance.AliasProducerGameItem;
import com.gourd.erwa.design.alias.producer.instance.AliasProducerGameItemOperate;
import com.gourd.erwa.design.alias.producer.instance.AliasProducerGameServer;

import java.util.Arrays;


/**
 * The type Examples.
 *
 * @author wei.Li by 2018/10/23
 */
public class Examples {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        /*
         * 注册别名中心
         */
        final AliasCenter aliasCenter = AliasCenter.create()
                .register(new AliasProducerGameItem())
                .register(new AliasProducerGameItemOperate())
                .register(new AliasProducerGameServer())
                .start();

        /*
         * 示例
         * 替换 game01 应用 101/102 道具别名
         */
        final String[] game01Originals = {"101", "102"};
        final String[] game01s = aliasCenter.aliasReplace(
                AliasCenterKey.builder()
                        .app("game01")
                        .aliasProducerKeyEnum(AliasProducerKeyEnum.AliasProducerGameItem)
                        .build(),
                game01Originals,
                AliasCenter.BI_FUNCTION_PARENTHESES
        );

        /*
         * 重载所有别名生产方别名数据
         * 应用于缓存更新等操作
         */
        aliasCenter.reLoadAliasProducer();

        /*
         * 系统停止时
         * 关闭资源
         */
        aliasCenter.close();
        System.out.println("原始数据: " + Arrays.toString(game01Originals) + " , 替换别名后 " + Arrays.toString(game01s));
    }
}
