package org.fxkc.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum SexEnum {

    男("0","男"),
    女("1","女"),
    不限("2","不限");

    private String code;
    private String name;

    private static Map<String,String> enumMap = new HashMap<>();
    private static Map<String,String> enumNameMap = new HashMap<>();

    static{
        for (SexEnum sexEnum:SexEnum.values()){
            enumMap.put(sexEnum.getCode(),sexEnum.getName());
            enumNameMap.put(sexEnum.getName(),sexEnum.getCode());
        }
    }


    public static String getNameByCode(String code){
        if(enumMap.get(code)!=null){
            return enumMap.get(code);
        }
        return "未知";
    }

    public static String getCodeByName(String name){

        if(enumNameMap.get(name)!=null){
            return enumNameMap.get(name);
        }
        return null;
    }
}
