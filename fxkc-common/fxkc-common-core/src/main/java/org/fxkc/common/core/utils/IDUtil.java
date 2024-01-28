package org.fxkc.common.core.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * ID工具类(单例)
 */
public class IDUtil {

    /**
     * 终端ID
     */
    private static final Long workerId = 15L;

    /**
     * 数据中心ID
     */
    private static final Long datacenterId = 15L;

    private static Snowflake instance = null;

    private IDUtil() {
    }

    public static Snowflake getInstance() {
        if (instance == null) {
            synchronized (IDUtil.class) {
                if (instance == null) {
                    instance = IdUtil.createSnowflake(workerId, datacenterId);
                }
            }
        }
        return instance;
    }
}
