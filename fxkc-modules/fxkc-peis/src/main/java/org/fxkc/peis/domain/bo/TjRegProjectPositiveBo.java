package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRegProjectPositive;

/**
 * 体检登记基础项目阳性记录业务对象 tj_reg_project_positive
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjRegProjectPositive.class, reverseConvertGenerate = false)
public class TjRegProjectPositiveBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检登记诊断明细主键id
     */
    private Long zdmxId;

    /**
     * 体检登记项目记录主键id
     */
    private Long regItemId;

    /**
     * 体检登记id
     */
    @NotNull(message = "体检登记id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regId;

    /**
     * 组合项目id
     */
    private Long combinationProjectId;

    /**
     * 组合项目名称
     */
    private String combinProjectName;

    /**
     * 基础项目id
     */
    private Long basicProjectId;

    /**
     * 检查类型0检查项目 1化验项目 2功能项目
     */
    private String checkType;

    /**
     * 科室主键id
     */
    private Long ksId;

    /**
     * 是否显示 0是 1否
     */
    private String showFlag;

    /**
     * 诊断建议主键id
     */
    private Long diagnoseAdviceId;

    /**
     * 诊断建议名称
     */
    @NotBlank(message = "诊断建议名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String diagnoseTitle;

    /**
     * 诊断建议
     */
    @NotBlank(message = "诊断建议不能为空", groups = { AddGroup.class, EditGroup.class })
    private String diagnoseAdvice;

    /**
     * 阳性列表排序
     */
    private Integer positiveSort;


}
