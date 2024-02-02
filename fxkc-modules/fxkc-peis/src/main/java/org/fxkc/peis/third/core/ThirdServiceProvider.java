package org.fxkc.peis.third.core;


import com.alibaba.fastjson2.JSONObject;

/**
 * @author zj
 * @description:  第三方服务提供者
 * @date 2024-01-31 18:07
 */
public interface ThirdServiceProvider {

    /**
     * 获取lis结果
     * @param objects 获取lis结果参数
     * @return lis结果数据
     */
    default Object lisResult(Object... objects) {
        return null;
    }

    /**
     * 获取pacs结果
     * @param objects 获取pacs结果参数
     * @return pacs结果数据
     */
    default Object pacsResult(Object... objects) {
        return null;
    }

    /**
     * 获取C14结果
     * @param objects 获取C14结果参数
     * @return C14结果数据
     */
    default Object c14Result(Object... objects) {
        return null;
    }

    /**
     * his建档
     * @param objects 建档参数
     * @return 建档结果
     */
    default JSONObject examination(Object... objects){return null;}

    /**
     * his登记
     * @param objects 登记参数
     * @return 登记结果
     */
    default JSONObject checkIn(Object... objects){return null;}

    /**
     * his推送费用
     * @param objects 推送费用参数
     * @return 推送费用结果
     */
    default JSONObject pushFees(Object... objects){return null;}

    /**
     * his收费成功回调
     * @param objects his收费回调参数
     * @return 收费结果
     */
    default JSONObject charge(Object... objects){return null;}
}
