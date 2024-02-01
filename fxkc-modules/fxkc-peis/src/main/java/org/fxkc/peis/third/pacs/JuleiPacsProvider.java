package org.fxkc.peis.third.pacs;

import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.springframework.stereotype.Service;

/**
 * @author zj
 * @description: 聚垒pacs服务具体实现
 * @date 2024-02-01 9:31
 */
@Service
public class JuleiPacsProvider implements PacsProvider{

    @Override
    public boolean supportsPacs(ServiceProviderEnum providerEnum) {
        return ServiceProviderEnum.JULEI_PACS == providerEnum;
    }

    @Override
    public Object pacsResult(ServiceProviderEnum providerEnum, Object... objects) {
        Integer param = (Integer) objects[0];
        return param+1;
    }
}
