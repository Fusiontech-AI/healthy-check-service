package org.fxkc.peis.third.lis;

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
 * @description: 获取lis服务，由哪家lis服务提供
 * @date 2024-01-31 17:59
 */

@Service
@Primary
@Slf4j
public class LisProviderComposite implements LisProvider{

    @Autowired
    List<LisProvider> lisProviders;

    private final Map<String, LisProvider> lisProviderMap = new ConcurrentHashMap<>(4);

    private LisProvider getLisProvider(String key, ServiceProviderEnum providerEnum) {
        LisProvider currentLisProvider = this.lisProviderMap.get(key);
        if (Objects.isNull(currentLisProvider)) {
            for (LisProvider lisProvider : this.lisProviders) {
                if (lisProvider.supportsLis(providerEnum)) {
                    currentLisProvider = lisProvider;
                    this.lisProviderMap.put(key, currentLisProvider);
                    break;
                }
            }
        }
        return currentLisProvider;
    }


    @Override
    public boolean supportsLis(ServiceProviderEnum providerEnum) {
        return Objects.nonNull(getLisProvider(providerEnum.name(), providerEnum));
    }


    @Override
    public Object lisResult(ServiceProviderEnum providerEnum, Object... objects) {
        LisProvider lisProvider = getLisProvider(providerEnum.name(), providerEnum);
        if (Objects.isNull(lisProvider)) {
            throw new IllegalArgumentException("不支持" + providerEnum.name() + "类型 服务厂商！");
        }
        return lisProvider.lisResult(providerEnum, objects);
    }
}
