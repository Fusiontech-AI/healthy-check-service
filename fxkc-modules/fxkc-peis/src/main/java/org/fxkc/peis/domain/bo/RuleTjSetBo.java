package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.RuleTjSet;

/**
 * 体检项目规则集业务对象 rule_tj_set
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = RuleTjSet.class, reverseConvertGenerate = false)
public class RuleTjSetBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 基础项目id
     */
    @NotNull(message = "基础项目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long basicProjectId;

    /**
     * 是否职业病(0：职业，1：健康  2老年人)
     */
    private String occupationalType;

    /**
     * 适用项目规则类型 1功能检查
     */
    private String xmRuleType;

    /**
     * 规则表达式
     */
    private String expression;

    /**
     * 分割符号(多个之间采用和字分割)
     */
    private String splitSymbol;

    /**
     * 规则类型 1诊断建议 2危急值 3高危异常
     */
    @NotBlank(message = "规则类型 1诊断建议 2危急值 3高危异常不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ruleType;


}
