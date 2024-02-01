package org.fxkc.peis.third.pacs;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.fxkc.peis.third.lis.LisProvider;
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

    @Override
    public Object pacsResult(ServiceProviderEnum providerEnum, Object... objects) {
        PacsProvider pacsProvider = getPacsProvider(providerEnum.name(), providerEnum);
        if (Objects.isNull(pacsProvider)) {
            throw new IllegalArgumentException("不支持" + providerEnum.name() + "类型 服务厂商！");
        }
        return pacsProvider.pacsResult(providerEnum, objects);
    }
}
