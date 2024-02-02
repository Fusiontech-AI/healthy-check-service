package org.fxkc.peis.third.pacs;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.springframework.stereotype.Service;

/**
 * @author zj
 * @description: 聚垒pacs服务具体实现
 * @date 2024-02-01 9:31
 */
@Service
@Slf4j
public class JuleiPacsProvider implements PacsProvider{

    @Override
    public boolean supportsPacs(ServiceProviderEnum providerEnum) {
        return ServiceProviderEnum.JULEI_PACS == providerEnum;
    }

    /**
     * 聚垒pacs服务调用结果
     * @param providerEnum pacs服务提供者类型
     * @param objects      pacs服务调用相关参数
     * @return pacs结果
     */
    @Override
    public Object pacsResult(ServiceProviderEnum providerEnum, Object... objects) {
        log.info("pacs deal request param:{}",objects[0]);
        Integer param = (Integer) objects[0];
        return param+1;
    }
}
