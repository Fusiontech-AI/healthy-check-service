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
    @NotBlank(message = "返回值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String returnValues;

    /**
     * 逻辑符号 AND/XOR
     */
    @NotBlank(message = "逻辑符号 AND/XOR不能为空", groups = { AddGroup.class, EditGroup.class })
    private String logicType;

    /**
     * 诊断建议主键id
     */
    @NotBlank(message = "诊断建议主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long zdjyId;

    /**
     * 重要程度（特别重要 重要）
     */
    @NotBlank(message = "重要程度（特别重要 重要）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String importance;

    /**
     * 结果类型（正常 异常）
     */
    @NotBlank(message = "结果类型（正常 异常）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String resultType;

    /**
     * 推荐就诊科室编码
     */
    @NotBlank(message = "推荐就诊科室编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String recommendDeptCode;

    /**
     * 是否危机
     */
    @NotBlank(message = "是否危机不能为空", groups = { AddGroup.class, EditGroup.class })
    private String crisisType;

    /**
     * 危机值措施
     */
    @NotBlank(message = "危机值措施不能为空", groups = { AddGroup.class, EditGroup.class })
    private String crisisDeal;

    /**
     * 是否隐私
     */
    @NotBlank(message = "是否隐私不能为空", groups = { AddGroup.class, EditGroup.class })
    private String privacyType;

    /**
     * 是否进入小结
     */
    @NotBlank(message = "是否进入小结不能为空", groups = { AddGroup.class, EditGroup.class })
    private String summaryType;

    /**
     * 是否进入诊断
     */
    @NotBlank(message = "是否进入诊断不能为空", groups = { AddGroup.class, EditGroup.class })
    private String diagnosisType;

    /**
     * 是否随访
     */
    @NotBlank(message = "是否随访不能为空", groups = { AddGroup.class, EditGroup.class })
    private String followType;

    /**
     * 匹配关键词
     */
    @NotBlank(message = "匹配关键词不能为空", groups = { AddGroup.class, EditGroup.class })
    private String keyWords;

    /**
     * 优先级
     */
    @NotNull(message = "优先级不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer priority;

    /**
     * 疾病级别
     */
    @NotBlank(message = "疾病级别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String icdLevel;

    /**
     * 疾病分类
     */
    @NotBlank(message = "疾病分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String icdClassification;

    /**
     * 统计分类
     */
    @NotBlank(message = "统计分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String statisticsClassification;


}
