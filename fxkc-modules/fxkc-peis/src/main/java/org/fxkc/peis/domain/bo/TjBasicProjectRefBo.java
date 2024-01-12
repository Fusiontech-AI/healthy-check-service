package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjBasicProjectRef;

import java.math.BigDecimal;

/**
 * 体检基础项目参考信息业务对象 tj_basic_project_ref
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjBasicProjectRef.class, reverseConvertGenerate = false)
public class TjBasicProjectRefBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 基础项目主键id
     */
    @NotNull(message = "基础项目主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long basicProjectId;

    /**
     * 1男 2女
     */
    @NotBlank(message = "1男 2女不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sex;

    /**
     * 年龄开始值
     */
    @NotNull(message = "年龄开始值不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ageStart;

    /**
     * 年龄结束值
     */
    @NotNull(message = "年龄结束值不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ageEnd;

    /**
     * 健康参考开始值
     */
    @NotNull(message = "健康参考开始值不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal healthReferStart;

    /**
     * 健康参考结束值
     */
    @NotNull(message = "健康参考结束值不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal healthReferEnd;

    /**
     * 职业参考开始值
     */
    @NotNull(message = "职业参考开始值不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal careerReferStart;

    /**
     * 职业参考结束值
     */
    @NotNull(message = "职业参考结束值不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal careerReferEnd;


}
