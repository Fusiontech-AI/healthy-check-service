package org.fxkc.peis.domain.vo;

import lombok.Data;
import org.fxkc.peis.domain.RuleTjInfo;

/**
 * 规则执行响应对象RuleTjInfoExcuteResultVO
 */
@Data
public class RuleTjInfoExcuteResultVo extends RuleTjInfo {

    /**
     *主要诊断
     */
    private String zyzd;

    /**
     *科普说明
     */
    private String kpsm;

    /**
     *职业病建议
     */
    private String zybjy;

    /**
     *比较原始值
     */
    private String oldValue;

    /**
     *项目值
     */
    private String xmValue;

    /**
     * 基础项目主键id
     */
    private Long basicProjectId;

    /**
     * 基础项目名称
     */
    private String basicProjectName;

    /**
     * 组合项目主键id
     */
    private Long combinProjectId;


    /**
     * 疾病类型
     */
    private String icdType;


    /**
     * 体检人员id
     */
    private Long registerId;

    /**
     * 规则类型 1诊断建议 2危急值 3高危异常
     */
    private String ruleType;
}
