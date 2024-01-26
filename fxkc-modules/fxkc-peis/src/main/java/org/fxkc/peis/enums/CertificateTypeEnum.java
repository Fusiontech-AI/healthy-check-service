package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum CertificateTypeEnum {

    身份证("0","身份证"),
    驾驶证("1","驾驶证"),
    军官证("2","军官证"),
    市民卡("3","市民卡"),
    学生卡("4","学生卡"),
    香港身份证("5","香港身份证"),
    港澳通行证("6","港澳通行证"),
    台湾通行证("7","台湾通行证"),
    护照("8","护照"),
    澳门通行证("9","澳门通行证"),
    电子健康卡("11","电子健康卡");

    private String code;
    private String name;

    private static Map<String,String> enumMap = new HashMap<>();
    private static Map<String,String> enumNameMap = new HashMap<>();

    static{
        for (CertificateTypeEnum applayStatusEnum : CertificateTypeEnum.values()){
            enumMap.put(applayStatusEnum.getCode(),applayStatusEnum.getName());
            enumNameMap.put(applayStatusEnum.getName(),applayStatusEnum.getCode());
        }

    }


    /**
     * 根据code获取相应的name
     * @param code
     * @return
     */
    public static String getNameByCode(String code){
        if(enumMap.get(code)!=null){
            return enumMap.get(code);
        }
        return "未知状态";
    }

    /**
     * 根据name获取枚举的code
     * @param name
     * @return
     */
    public static String getCodeByName(String name){

        if(enumNameMap.get(name)!=null){
            return enumNameMap.get(name);
        }
        return null;
    }

    /**
     * 是否有此code
     * @param code
     * @return
     */
    public static boolean containsCode(String code){
        if(enumMap.get(code)!=null){
            return true;
        }
        return false;
    }
}
