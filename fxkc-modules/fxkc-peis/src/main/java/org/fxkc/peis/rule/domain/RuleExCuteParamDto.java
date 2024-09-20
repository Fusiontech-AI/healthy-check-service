package org.fxkc.peis.rule.domain;

import lombok.Data;

/**
 * 规则执行参数请求dto
 */
@Data
public class RuleExCuteParamDto {

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

}
