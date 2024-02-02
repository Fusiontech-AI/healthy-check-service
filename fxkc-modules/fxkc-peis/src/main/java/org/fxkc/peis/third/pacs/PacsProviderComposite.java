package org.fxkc.peis.third.pacs;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zj
 * @description: pacs服务提供实现，哪家pacs厂商提供
 * @date 2024-02-01 9:27
 */

@Service
@Primary
@Slf4j
public class PacsProviderComposite implements PacsProvider{

    @Autowired
    List<PacsProvider>  pacsProviders;

    private final Map<String, PacsProvider> lisProviderMap = new ConcurrentHashMap<>(4);

    /**
     * 获取具体pacs服务厂商
     * @param key 服务提供厂商key
     * @param providerEnum 服务提供厂商枚举
     * @return pacs服务实现类
     */
    private PacsProvider getPacsProvider(String key, ServiceProviderEnum providerEnum) {
        PacsProvider currentPacsProvider = this.lisProviderMap.get(key);
        if (Objects.isNull(currentPacsProvider)) {
            for (PacsProvider lisProvider : this.pacsProviders) {
                if (lisProvider.supportsPacs(providerEnum)) {
                    currentPacsProvider = lisProvider;
                    this.lisProviderMap.put(key, currentPacsProvider);
                    break;
                }
            }
        }
        return currentPacsProvider;
    }

    @Override
    public boolean supportsPacs(ServiceProviderEnum providerEnum) {
        return Objects.nonNull(getPacsProvider(providerEnum.name(), providerEnum));
    }

    /**
     * 获取pacs服务调用结果
     * @param providerEnum pacs服务提供者类型
     * @param objects      pacs服务调用相关参数
     * @return pacs服务调用结果
     */
    @Override
    public Object pacsResult(ServiceProviderEnum providerEnum, Object... objects) {
        PacsProvider pacsProvider = getPacsProvider(providerEnum.name(), providerEnum);
        if (Objects.isNull(pacsProvider)) {
            throw new IllegalArgumentException(providerEnum.name() +"service provider type is not defined, contact the administrator！");
        }
        return pacsProvider.pacsResult(providerEnum, objects);
    }
}
