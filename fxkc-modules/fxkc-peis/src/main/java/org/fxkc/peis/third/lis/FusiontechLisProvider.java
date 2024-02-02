package org.fxkc.peis.third.lis;

import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.springframework.stereotype.Component;

/**
 * @author zj
 * @description: 福鑫科创LIS服务实现
 * @date 2024-01-31 18:07
 */
@Component
public class FusiontechLisProvider implements LisProvider {

    @Override
    public boolean supportsLis(ServiceProviderEnum providerEnum) {
        return ServiceProviderEnum.FUSIONTECH_LIS == providerEnum;
    }

    /**
     * 获取lis结果
     * @param providerEnum lis服务提供者类型
     * @param objects      lis服务调用相关参数
     * @return lis结果
     */
    @Override
    public Object lisResult(ServiceProviderEnum providerEnum, Object... objects) {
        String param = (String) objects[0];
        return param.toUpperCase();
    }
}
