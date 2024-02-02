package org.fxkc.peis.third.his;

import com.alibaba.fastjson2.JSONObject;
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
 * @description: 获取his服务，由哪家his服务提供
 * @date 2024-02-01 18:01
 */
@Service
@Primary
@Slf4j
public class HisProviderComposite implements HisProvider{

    @Autowired
    List<HisProvider> hisProviderList;

    private final Map<String, HisProvider> hisProviderMap = new ConcurrentHashMap<>(4);

    /**
     * his服务路由，决定哪个his服务提供
     * @param key 服务提供方key
     * @param providerEnum 服务提供方枚举
     * @return
     */
    private HisProvider getHisProvider(String key, ServiceProviderEnum providerEnum) {
        HisProvider currentLisProvider = this.hisProviderMap.get(key);
        if (Objects.isNull(currentLisProvider)) {
            for (HisProvider hisProvider : this.hisProviderList) {
                if (hisProvider.supportsHis(providerEnum)) {
                    currentLisProvider = hisProvider;
                    this.hisProviderMap.put(key, currentLisProvider);
                    break;
                }
            }
        }
        return currentLisProvider;
    }

    @Override
    public boolean supportsHis(ServiceProviderEnum providerEnum) {
        return Objects.nonNull(getHisProvider(providerEnum.name(), providerEnum));
    }

    /**
     * 体检患者往his建档
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return his建档结果
     */
    @Override
    public JSONObject examination(ServiceProviderEnum providerEnum, Object... objects) {
        HisProvider hisProvider = getHisProvider(providerEnum.name(), providerEnum);
        if (Objects.isNull(hisProvider)) {
            throw new IllegalArgumentException(providerEnum.name() +"service provider type is not defined, contact the administrator！");
        }
        return hisProvider.examination(providerEnum, objects);
    }

    /**
     * 体检患者往his登记
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return his登记结果
     */
    @Override
    public JSONObject checkIn(ServiceProviderEnum providerEnum, Object... objects) {
        HisProvider hisProvider = getHisProvider(providerEnum.name(), providerEnum);
        if (Objects.isNull(hisProvider)) {
            throw new IllegalArgumentException(providerEnum.name() +"service provider type is not defined, contact the administrator！");
        }
        return hisProvider.checkIn(providerEnum, objects);
    }

    /**
     * 体检患者往his推送费用以供his收费
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return his费用推送结果
     */
    @Override
    public JSONObject pushFees(ServiceProviderEnum providerEnum, Object... objects) {
        HisProvider hisProvider = getHisProvider(providerEnum.name(), providerEnum);
        if (Objects.isNull(hisProvider)) {
            throw new IllegalArgumentException(providerEnum.name() +"service provider type is not defined, contact the administrator！");
        }
        return hisProvider.pushFees(providerEnum, objects);
    }

    /**
     * his收费成功回调体检
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return 体检改变患者收费状态结果
     */
    @Override
    public JSONObject charge(ServiceProviderEnum providerEnum, Object... objects) {
        HisProvider hisProvider = getHisProvider(providerEnum.name(), providerEnum);
        if (Objects.isNull(hisProvider)) {
            throw new IllegalArgumentException(providerEnum.name() +"service provider type is not defined, contact the administrator！");
        }
        return hisProvider.charge(providerEnum, objects);
    }
}
