package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.RuleTjCondition;

/**
 * 体检项目规则条件业务对象 rule_tj_condition
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = RuleTjCondition.class, reverseConvertGenerate = false)
public class RuleTjConditionBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 规则项id
     */
    @NotNull(message = "规则项id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ruleId;

    /**
     * 条件名称
     */
    @NotBlank(message = "条件名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 变量名
     */
    @NotBlank(message = "变量名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String variableName;

    /**
     * 变量类型 1数字 2字符
     */
    @NotBlank(message = "变量类型 1数字 2字符不能为空", groups = { AddGroup.class, EditGroup.class })
    private String variableType;

    /**
     * 比较值
     */
    @NotBlank(message = "比较值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String referenceValue;

    /**
     * 比较类型（大于小于）
     */
    @NotBlank(message = "比较类型（大于小于）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String relationType;

    /**
     * 逻辑符号 AND/XOR
     */
    @NotBlank(message = "逻辑符号 AND/XOR不能为空", groups = { AddGroup.class, EditGroup.class })
    private String logicType;

    /**
     * 优先级
     */
    @NotNull(message = "优先级不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer priority;


}
