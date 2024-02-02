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
