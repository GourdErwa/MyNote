package com.gourd.erwa.annotation;

import java.lang.annotation.Documented;

/**
 * @author wei.Li
 */
public final class InterfaceAudience {

    private InterfaceAudience() {
    } // Audience can't exist on its own

    /**
     * 对所有工程和应用可用.
     */
    @Documented
    public @interface Public {
    }

    /**
     * 仅限于某些特定工程.
     * For example, "Common", "MapReduce", "ZooKeeper", "HBase".
     */
    @Documented
    public @interface LimitedPrivate {
        String[] value();
    }

    /**
     * 仅限于 com.gourd.erwa
     */
    @Documented
    public @interface Private {
    }

}
