package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum AssociationTypeEnum {

    REQUIRED_ITEM("1", "必检项目"),
    TARGET_OCCUPATIONAL("2", "目标职业病"),
    OCCUPATIONAL_CONTRAINDICATIONS("3", "职业禁忌症"),
    SYMPTOM_FOCUS("4", "症状询问重点"),
    PHYSICAL_EXAMINATION("5", "体格检查"),
    INSPECTION_CYCLE("6", "检查周期"),
    EVALUATION_CRITERION("7", "评价标准"),
    ;

    private final String code;

    private final String desc;

    public static List<String> getCheckList() {
        return Arrays.asList(REQUIRED_ITEM.getCode(), TARGET_OCCUPATIONAL.getCode(),
                OCCUPATIONAL_CONTRAINDICATIONS.getCode());
    }

}
