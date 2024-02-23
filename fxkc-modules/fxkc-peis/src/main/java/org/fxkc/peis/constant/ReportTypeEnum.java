package org.fxkc.peis.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportTypeEnum {
    ZYD("ZYD", "指引单"),
    PERSONAL("PERSONAL", "个人报告"),
    TXM("TXM", "条形码");
    private final String code;
    private final String name;
}
