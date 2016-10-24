package com.gourd.erwa.annotation;

import java.lang.annotation.Documented;

/**
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public final class InterfaceStability {
    /**
     * 主版本是稳定的，不同主版本间可能不兼容 (ie. at m.0).
     */
    @Documented
    public @interface Stable {
    }

    /**
     * 不断变化，不同次版本间可能不兼容  (i.e. m.x)
     */
    @Documented
    public @interface Evolving {
    }

    /**
     * 没有任何可靠性和健壮性保证.
     */
    @Documented
    public @interface Unstable {
    }

}
