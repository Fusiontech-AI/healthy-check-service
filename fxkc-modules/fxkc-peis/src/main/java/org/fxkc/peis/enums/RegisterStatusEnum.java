package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-22 17:43
 **/
@Getter
@AllArgsConstructor
public enum RegisterStatusEnum {
    正常("0","正常"),
    取消登记("1","取消登记")
    ;
    private final String code;
    private final String name;
}
