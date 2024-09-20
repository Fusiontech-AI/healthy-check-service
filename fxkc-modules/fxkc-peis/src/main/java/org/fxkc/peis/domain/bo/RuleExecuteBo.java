package org.fxkc.peis.domain.bo;

import lombok.Data;
import org.fxkc.peis.domain.RuleTjSet;

/**
 * 规则集记录信息执行参数请求Bo
 */
@Data
public class RuleExecuteBo extends RuleTjSet {

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


}
