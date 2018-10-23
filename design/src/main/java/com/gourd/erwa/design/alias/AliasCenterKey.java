package com.gourd.erwa.design.alias;

import com.gourd.erwa.design.alias.producer.instance.AliasProducerKeyEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Alias center key.
 *
 * @author wei.Li by 2018/10/23
 */
@Data
@Builder
@EqualsAndHashCode
public class AliasCenterKey {

    /**
     * 应用名称
     */
    private String app;
    /**
     * 别名生产者 KEY
     */
    private AliasProducerKeyEnum aliasProducerKeyEnum;

}
