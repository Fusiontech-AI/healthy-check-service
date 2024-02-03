package org.fxkc.peis.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExtendTypeEnum {
    //1 医院名称
    HOSPITAL_NAME("1", "医院名称"),
    //2.体检地址
    TJ_ADDRESS("2", "体检地址"),
    //3.体检电话
    TJ_PHONE("3", "体检电话"),
    //4.体检注意事项
    WARM_TIPS("4", "体检注意事项"),
    //5.体检中心简介
    BRIEF_INTRODUCTION("5", "体检中心简介"),
    //6.医院徽标
    HOSPITAL_LOGO("6", "医院徽标"),
    //7.页眉图标
    PAGE_HEADER_ICON("7", "页眉图标");

    private final String code;
    private final String name;
}
