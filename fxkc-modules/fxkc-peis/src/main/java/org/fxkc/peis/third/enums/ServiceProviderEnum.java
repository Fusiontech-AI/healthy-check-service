package org.fxkc.peis.third.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zj
 * @description: 第三方厂商枚举
 * @date 2024-01-31 18:07
 */

@Getter
@AllArgsConstructor
public enum ServiceProviderEnum {

    // his
    FUSIONTECH_HIS_HTTP("福鑫科创 HIS，直接走接口"),
    FUSIONTECH_HIS_JCPT("福鑫科创 HIS，走集成平台"),

    // lis
    FUSIONTECH_LIS("福鑫科创 LIS"),
    CHUANGZHI_LIS("创智 LIS"),

    // pacs
    JULEI_PACS("聚垒 PACS"),
    TIANJIAN_PACS("天健 PACS")

    // c14

    // 动脉硬化

    // 心电

    // 骨密度
    ;

    private final String serviceProvider;
}
