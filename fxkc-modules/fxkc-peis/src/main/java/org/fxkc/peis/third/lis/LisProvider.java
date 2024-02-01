package org.fxkc.peis.third.lis;

import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.fxkc.peis.third.core.ThirdServiceProvider;

/**
 * @author zj
 * @description: 第三方服务之LIS服务
 * @date 2024-01-28 18:14
 */

public interface LisProvider extends ThirdServiceProvider {

    /**
     * 第三方服务提供者类型
     *
     * @param providerEnum
     * @return 是否支持
     */
    boolean supportsLis(ServiceProviderEnum providerEnum);

    /**
     * 获取lis结果
     *
     * @param providerEnum lis服务提供者类型
     * @param objects      lis服务调用相关参数
     * @return lis结果
     */
    default Object lisResult(ServiceProviderEnum providerEnum, Object... objects) {
        return lisResult(objects);
    }
}
