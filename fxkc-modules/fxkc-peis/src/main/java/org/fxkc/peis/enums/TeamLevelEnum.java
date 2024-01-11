package org.fxkc.peis.enums;

import lombok.Getter;

@Getter
public enum TeamLevelEnum {

    ONE(1),
    TWO(2),
    ;
    private final Integer code;

    TeamLevelEnum(Integer code) {
        this.code = code;
    }
}
