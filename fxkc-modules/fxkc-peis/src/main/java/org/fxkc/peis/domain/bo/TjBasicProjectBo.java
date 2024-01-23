package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjBasicProject;

/**
 * 体检基础项目业务对象 tj_basic_project
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjBasicProject.class, reverseConvertGenerate = false)
public class TjBasicProjectBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 基础项目编码
     */
    @NotBlank(message = "基础项目编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String basicProjectCode;

    /**
     * 基础项目名称
     */
    @NotBlank(message = "基础项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String basicProjectName;

    /**
     * 基础项目简称
     */
    private String basicSimpleName;

    /**
     * 所属科室id
     */
    @NotNull(message = "所属科室id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ksId;

    /**
     * 计量单位
     */
    @NotBlank(message = "计量单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 结果类型 0数字 1文本
     */
    @NotBlank(message = "结果类型 0数字 1文本不能为空", groups = { AddGroup.class, EditGroup.class })
    private String resultType;

    /**
     * 结果获取方式 0手工 1导入
     */
    @NotBlank(message = "结果获取方式 0手工 1导入不能为空", groups = { AddGroup.class, EditGroup.class })
    private String resultGetWay;

    /**
     * 适用性别 0男 1女 2不限
     */
    private String suitSex;

    /**
     * 项目默认值
     */
    private String defaultValue;

    /**
     * 基础项目显示序号
     */
    @NotNull(message = "基础项目显示序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long projectSort;

    /**
     * 进入小结 0是 1否
     */
    private String enterSummary;

    /**
     * 进入报告 0是 1否
     */
    private String enterReport;

    /**
     * 对照lis编码
     */
    private String lisCode;

    /**
     * 对照pacs编码
     */
    private String pacsCode;

    /**
     * 对照his编码
     */
    private String hisCode;

    /**
     * 对照职业病编码
     */
    private String zybCode;

    /**
     * 基础项目状态（0正常 1停用）
     */
    private String status;
    /**
     * 是否职业病
     */
    private Boolean isOccupational;

}
