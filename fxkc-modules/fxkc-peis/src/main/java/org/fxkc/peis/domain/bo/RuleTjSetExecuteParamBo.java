package org.fxkc.peis.domain.bo;

import lombok.Data;

/**
 * 规则集记录信息执行参数请求Bo
 */
@Data
public class RuleTjSetExecuteParamBo {

    /**
     * 体检项目值
     */
    private String xmValue;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private String age;

    /**
     * 阴阳
     */
    private String isPositive;

    /**
     * 范围
     */
    private String isAbnormal;

    /**
     * 分割符号(多个之间采用和字分割)
     */
    private String splitSymbol;

}
