package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PhysicalTypeEnum {

    JKTJ("健康体检"),
    ZYJKTJ("职业健康体检"),
    FSTJ("放射体检"),
    LNRTJ("老年人体检"),
    RZTJ("入职体检"),
    XSTJ("学生体检"),
    ;

    private final String desc;

    public static Boolean isOccupational(String name) {
        return Objects.equals(ZYJKTJ.name(), name) || Objects.equals(FSTJ.name(), name);
    }

}
