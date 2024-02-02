package org.fxkc.peis.third.his;

import com.alibaba.fastjson2.JSONObject;
import org.fxkc.peis.third.enums.ServiceProviderEnum;
import org.springframework.stereotype.Component;

/**
 * @author zj
 * @description: 福鑫科创his实现（交互通过HTTP接口）
 * @date 2024-02-01 18:10
 */

@Component
public class FusiontechHisHttpProvider implements HisProvider{
    @Override
    public boolean supportsHis(ServiceProviderEnum providerEnum) {
        return ServiceProviderEnum.FUSIONTECH_HIS_HTTP == providerEnum;
    }

    /**
     * 体检患者往his建档
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return his建档结果
     */
    @Override
    public JSONObject examination(ServiceProviderEnum providerEnum, Object... objects) {
        String object = (String) objects[0];
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", object+"his examination http");
        return jsonObject;
    }

    /**
     * 体检患者往his登记
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return his登记结果
     */
    @Override
    public JSONObject checkIn(ServiceProviderEnum providerEnum, Object... objects) {
        String object = (String) objects[0];
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", object+"his checkIn http");
        return jsonObject;
    }

    /**
     * 体检患者往his推送费用以供his收费
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return his费用推送结果
     */
    @Override
    public JSONObject pushFees(ServiceProviderEnum providerEnum, Object... objects) {
        String object = (String) objects[0];
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", object+"his pushFees http");
        return jsonObject;
    }

    /**
     * his收费成功回调体检
     * @param providerEnum his服务提供者类型
     * @param objects      his服务调用相关参数
     * @return 体检改变患者收费状态结果
     */
    @Override
    public JSONObject charge(ServiceProviderEnum providerEnum, Object... objects) {
        String object = (String) objects[0];
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", object+"his charge http");
        return jsonObject;
    }
}
