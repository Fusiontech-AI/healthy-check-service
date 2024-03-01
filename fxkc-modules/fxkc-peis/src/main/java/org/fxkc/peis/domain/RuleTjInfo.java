package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检项目规则项对象 rule_tj_info
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rule_tj_info")
public class RuleTjInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 规则集id
     */
    private Long rulesetId;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 返回值
     */
    private String returnValues;

    /**
     * 逻辑符号 AND/XOR
     */
    private String logicType;

    /**
     * 诊断建议主键id
     */
    private Long zdjyId;

    /**
     * 重要程度（特别重要 重要）
     */
    private String importance;

    /**
     * 结果类型（正常 异常）
     */
    private String resultType;

    /**
     * 推荐就诊科室编码
     */
    private String recommendDeptCode;

    /**
     * 是否危机
     */
    private String crisisType;

    /**
     * 危机值措施
     */
    private String crisisDeal;

    /**
     * 是否隐私
     */
    private String privacyType;

    /**
     * 是否进入小结
     */
    private String summaryType;

    /**
     * 是否进入诊断
     */
    private String diagnosisType;

    /**
     * 是否随访
     */
    private String followType;

    /**
     * 匹配关键词
     */
    private String keyWords;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 疾病级别
     */
    private String icdLevel;

    /**
     * 疾病分类
     */
    private String icdClassification;

    /**
     * 统计分类
     */
    private String statisticsClassification;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
