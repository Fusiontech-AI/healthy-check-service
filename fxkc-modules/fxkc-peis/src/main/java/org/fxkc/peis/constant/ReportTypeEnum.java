package org.fxkc.peis.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportTypeEnum {
    ZYD("ZYD", "指引单");

    private final String code;
    private final String name;
}
