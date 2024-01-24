package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OccupationalDictEnum {


    TJJL("bus_tj_conclusion","体检结论"),
    GAKLB("bus_case_card_type","个案卡类别"),
    JCLX("bus_tj_check_type","检查类型"),
    WHYS("bus_hazardous_factors","危害因素"),
    ZYJJZ("bus_zy_contraindications","职业禁忌证"),
    YSZYB("bus_suspected_zyb","疑似职业病"),
    HYFL("bus_industry_category","行业分类"),
    JJLX("bus_economic_type","经济类型"),
    QYGM("bus_company_size","企业规模"),
    GZ("bus_job_code","工种");

    private final String code;
    private final String name;
}
