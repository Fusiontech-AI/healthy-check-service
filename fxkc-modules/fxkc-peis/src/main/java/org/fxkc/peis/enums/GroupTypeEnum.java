package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupTypeEnum {

    ITEM(1, "项目分组"),
    PRICE(2, "金额分组"),
    DISCOUNT(3, "折扣分组"),
    ;

    private final Integer code;

    private final String desc;

}
