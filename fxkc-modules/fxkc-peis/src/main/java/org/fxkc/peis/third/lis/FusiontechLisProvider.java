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

    @Override
    public Object lisResult(ServiceProviderEnum providerEnum, Object... objects) {
        String param = (String) objects[0];
        return param.toUpperCase();
    }
}
