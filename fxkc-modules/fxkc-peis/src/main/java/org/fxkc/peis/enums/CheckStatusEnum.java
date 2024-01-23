package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-23 13:41
 **/
@Getter
@AllArgsConstructor
public enum CheckStatusEnum {

    未检查("0", "未检查"),
    已检查("1", "已检查"),
    弃捡("2", "弃捡"),
    未保存("3", "未保存"),
    延期("4", "延期")
    ;

    private final String code;
    private final String name;
}
