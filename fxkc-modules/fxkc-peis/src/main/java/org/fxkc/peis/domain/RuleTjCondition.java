package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检项目规则条件对象 rule_tj_condition
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rule_tj_condition")
public class RuleTjCondition extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 规则项id
     */
    private Long ruleId;

    /**
     * 条件名称
     */
    private String name;

    /**
     * 变量名
     */
    private String variableName;

    /**
     * 变量类型 1数字 2字符
     */
    private String variableType;

    /**
     * 比较值
     */
    private String referenceValue;

    /**
     * 比较类型（大于小于）
     */
    private String relationType;

    /**
     * 逻辑符号 AND/XOR
     */
    private String logicType;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
