package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;
import org.fxkc.peis.domain.bo.RuleExecuteBo;

import java.io.Serial;

/**
 * 体检项目规则集对象 rule_tj_set
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rule_tj_set")
@AutoMapper(target = RuleExecuteBo.class, reverseConvertGenerate = false)
public class RuleTjSet extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 基础项目id
     */
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
    private String ruleType;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
