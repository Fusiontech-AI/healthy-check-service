package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OccupationalDictEnum {


    TJJL("6","体检结论"),
    GAKLB("8","个案卡类别"),
    JCLX("9","检查类型"),
    WHYS("13","危害因素"),
    ZYJJZ("14","职业禁忌证"),
    YSZYB("15","疑似职业病"),
    HYFL("16","行业分类"),
    JJLX("17","经济类型"),
    QYGM("18","企业规模"),
    GZ("19","工种");

    private final String code;
    private final String name;
}
