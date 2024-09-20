package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 规则集记录信息执行参数请求RuleExecuteRequestBo
 */
@Data
public class RuleExecuteRequestBo{

    /**
     * 基础项目id
     */
    @NotBlank(message = "基础项目id不能为空!")
    private Long basicProjectId;

    /**
     * 体检项目值
     */
    @NotBlank(message = "基础项目值不能为空!")
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
