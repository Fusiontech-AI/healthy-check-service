package org.fxkc.peis.liteflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ChangeTypeEnum {

    单项("1","单项"),
    总计项("2","总计项"),
    新增("3","新增"),
    删除("4","删除");

    private String code;
    private String name;

    private static Map<String,String> enumMap = new HashMap<>();
    private static Map<String,String> enumNameMap = new HashMap<>();

    static{
        for (ChangeTypeEnum applayStatusEnum : ChangeTypeEnum.values()){
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
