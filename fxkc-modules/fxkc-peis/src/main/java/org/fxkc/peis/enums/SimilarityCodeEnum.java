package org.fxkc.peis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum SimilarityCodeEnum {

    精度85(0.85,"STRING_ACCURACY"),
    精度65(0.65,"STRING_SECOND_ACCURACY");

    private double code;
    private String name;

    private static Map<Double,String> enumMap = new HashMap<>();
    private static Map<String,Double> enumNameMap = new HashMap<>();

    static{
        for (SimilarityCodeEnum applayStatusEnum : SimilarityCodeEnum.values()){
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
    public static Double getCodeByName(String name){

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

    public static List<String> getNameList() {
        List<String> codeList = new ArrayList<>();
        for (SimilarityCodeEnum myEnum : SimilarityCodeEnum.values()) {
            codeList.add(myEnum.getName());
        }
        return codeList;
    }
}
