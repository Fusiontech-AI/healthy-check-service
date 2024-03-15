package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.RuleTjInfo;

/**
 * 体检项目规则项业务对象 rule_tj_info
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = RuleTjInfo.class, reverseConvertGenerate = false)
public class RuleTjInfoBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 规则集id
     */
    @NotNull(message = "规则集id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long rulesetId;

    /**
     * 规则名称
     */
    @NotBlank(message = "规则名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 返回值
     */
    private String returnValues;

    /**
     * 逻辑符号 AND/XOR
     */
    @NotBlank(message = "逻辑符号 AND/XOR不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotNull(message = "优先级不能为空", groups = { AddGroup.class, EditGroup.class })
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


}
