package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zry
 * @date 2024/01/22
 */

@Getter
@AllArgsConstructor
public enum HealthyCheckTypeEnum {
    预约("0","预约"),
    登记("1","登记"),
    科室分检("2","科室分检"),
    分检完成("3","分检完成"),
    待总检("4","待总检"),
    已终检("5","已终检")
    ;

    private final String code;
    private final String name;
}
