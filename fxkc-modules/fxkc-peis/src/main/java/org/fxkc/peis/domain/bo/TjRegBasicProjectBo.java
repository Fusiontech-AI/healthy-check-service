package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRegBasicProject;

/**
 * 体检登记基础项目关联业务对象 tj_reg_basic_project
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjRegBasicProject.class, reverseConvertGenerate = false)
public class TjRegBasicProjectBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检登记项目记录主键id
     */
    @NotNull(message = "体检登记项目记录主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regItemId;

    /**
     * 体检登记id
     */
    @NotNull(message = "体检登记id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regId;

    /**
     * 组合项目id
     */
    @NotNull(message = "组合项目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long combinationProjectId;

    /**
     * 基础项目id
     */
    @NotNull(message = "基础项目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long basicProjectId;

    /**
     * 检查部位
     */
    @NotBlank(message = "检查部位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkPart;

    /**
     * 是否阳性 0是 1否
     */
    @NotBlank(message = "是否阳性 0是 1否不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isPositive;

    /**
     * 是否正常（0-正常 1-不正常 2-偏高 3-偏低4-高于极限 5低于极限 ）字典bus_check_result
     */
    @NotBlank(message = "是否正常（0-正常 1-不正常 2-偏高 3-偏低4-高于极限 5低于极限 ）字典bus_check_result不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isAbnormal;

    /**
     * 参考值
     */
    @NotBlank(message = "参考值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reference;

    /**
     * 检查结果
     */
    @NotBlank(message = "检查结果不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkResult;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 数值上限
     */
    @NotBlank(message = "数值上限不能为空", groups = { AddGroup.class, EditGroup.class })
    private String upperLimit;

    /**
     * 数值下限
     */
    @NotBlank(message = "数值下限不能为空", groups = { AddGroup.class, EditGroup.class })
    private String lowLimit;


}
