package org.fxkc.peis.rule.domain;

import lombok.Data;

/**
 * 规则执行请求dto
 */
@Data
public class RuleExCuteDto {

    /**
     * 个人登记ID
     */
    String regId;

    /**
     * 适用类型（1健康体检 2职业病  3老年人）
     */
    private String tjType;

    /**
     * 体检项目id
     */
    private String tjxmId;

    /**
     * 组合项目ID
     */
    private String zhxmId;

    /**
     * 参数信息
     */
    private RuleExCuteParamDto paramDto;

}
