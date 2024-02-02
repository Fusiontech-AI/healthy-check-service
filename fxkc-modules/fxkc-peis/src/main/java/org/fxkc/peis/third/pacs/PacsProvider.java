package org.fxkc.peis.third.pacs;

import org.fxkc.peis.third.core.ThirdServiceProvider;
import org.fxkc.peis.third.enums.ServiceProviderEnum;

/**
 * @author zj
 * @description: pacs服务抽象
 * @date 2024-01-31 18:07
 */

public interface PacsProvider extends ThirdServiceProvider {

    /**
     * 第三方服务提供者类型
     *
     * @param providerEnum
     * @return 是否支持
     */
    boolean supportsPacs(ServiceProviderEnum providerEnum);

    /**
     * 获取pacs结果
     *
     * @param providerEnum pacs服务提供者类型
     * @param objects      pacs服务调用相关参数
     * @return pacs结果
     */
    default Object pacsResult(ServiceProviderEnum providerEnum, Object... objects) {
        return pacsResult(objects);
    }
}
