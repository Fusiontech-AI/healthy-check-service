package org.fxkc.peis.liteflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum InputTypeEnum {

    折扣("1","折扣"),
    应收金额("2","应收金额"),
    收费方式("3","收费方式"),
    个人应收额("4","个人应收额"),
    单位应收额("5","单位应收额"),
    分组或加项折扣("6","分组或加项折扣");

    private String code;
    private String name;

    private static Map<String,String> enumMap = new HashMap<>();
    private static Map<String,String> enumNameMap = new HashMap<>();

    static{
        for (InputTypeEnum applayStatusEnum : InputTypeEnum.values()){
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
