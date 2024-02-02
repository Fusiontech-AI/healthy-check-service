package org.fxkc.peis.third.his;

import com.alibaba.fastjson2.JSONObject;
import org.fxkc.peis.third.core.ThirdServiceProvider;
import org.fxkc.peis.third.enums.ServiceProviderEnum;

/**
 * his服务提供
 */
public interface HisProvider extends ThirdServiceProvider {

    /**
     * 第三方服务提供者类型
     *
     * @param providerEnum
     * @return 是否支持
     */
    boolean supportsHis(ServiceProviderEnum providerEnum);

    /**
     * his建档
     *
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return 建档结果
     */
    default JSONObject examination(ServiceProviderEnum providerEnum,Object... objects) {
        return examination(objects);
    }

    /**
     * his登记
     *
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return 登记结果
     */
    default JSONObject checkIn(ServiceProviderEnum providerEnum,Object... objects) {
        return checkIn(objects);
    }

    /**
     * his费用推送
     *
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return 费用推送结果
     */
    default JSONObject pushFees(ServiceProviderEnum providerEnum,Object... objects) {
        return pushFees(objects);
    }

    /**
     * his收费回调
     *
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return 收费结果
     */
    default JSONObject charge(ServiceProviderEnum providerEnum,Object... objects) {
        return charge(objects);
    }
}
