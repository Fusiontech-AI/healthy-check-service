package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjCombinationProject;

import java.math.BigDecimal;

/**
 * 体检组合项目业务对象 tj_combination_project
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjCombinationProject.class, reverseConvertGenerate = false)
public class TjCombinationProjectBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 组合项目编码
     */
    @NotBlank(message = "组合项目编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String combinProjectCode;

    /**
     * 组合项目名称
     */
    @NotBlank(message = "组合项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String combinProjectName;

    /**
     * 组合项目简称
     */
    @NotBlank(message = "组合项目简称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String combinSimpleName;

    /**
     * 拼音简码
     */
    @NotBlank(message = "拼音简码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pySimpleCode;

    /**
     * 检查类型0检查项目 1化验项目 2功能项目
     */
    @NotBlank(message = "检查类型0检查项目 1化验项目 2功能项目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkType;

    /**
     * 科室主键id
     */
    @NotNull(message = "科室主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ksId;

    /**
     * 样本类型 0类型1
     */
    @NotBlank(message = "样本类型 0类型1不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sampleType;

    /**
     * 是否需要标本
     */
    @NotBlank(message = "是否需要标本不能为空", groups = { AddGroup.class, EditGroup.class })
    private String specimenNeedFlag;

    /**
     * 标本类型 0类型1
     */
    private String specimenType;

    /**
     * 标准价格
     */
    @NotNull(message = "标准价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal standardAmount;

    /**
     * 折扣率
     */
    @NotNull(message = "折扣率不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal discount;

    /**
     * 对照lis编码
     */
    @NotBlank(message = "对照lis编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String lisCode;

    /**
     * 对照pacs编码
     */
    @NotBlank(message = "对照pacs编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pacsCode;

    /**
     * 对照his编码
     */
    @NotBlank(message = "对照his编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hisCode;

    /**
     * 对照职业病编码
     */
    @NotBlank(message = "对照职业病编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zybCode;

    /**
     * 组合项目排序
     */
    @NotNull(message = "组合项目排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long projectSort;

    /**
     * 适用性别 0男 1女 2不限
     */
    @NotBlank(message = "适用性别 0男 1女 2不限不能为空", groups = { AddGroup.class, EditGroup.class })
    private String suitSex;

    /**
     * 项目类型 0普通
     */
    @NotBlank(message = "项目类型 0普通不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectType;

    /**
     * 财务类别 0是
     */
    @NotBlank(message = "财务类别 0是不能为空", groups = { AddGroup.class, EditGroup.class })
    private String financialType;

    /**
     * 检查地址
     */
    private String checkAddress;

    /**
     * 是否进入隐私报告 0是
     */
    private String privacyFlag;


    /**
     * 是否进入指引单 0是
     */
    private String guideFlag;


    /**
     * 是否进入工作站 0是
     */
    private String workerFlag;

    /**
     * 是否外送 0是
     */
    @NotBlank(message = "是否外送 0是不能为空", groups = { AddGroup.class, EditGroup.class })
    private String outFlag;

    /**
     * 外送回调地址
     */
    @NotBlank(message = "外送回调地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String outAddress;

    /**
     * 当前预约上限
     */
    @NotNull(message = "当前预约上限不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long useLimit;

    /**
     * 指引单提示信息
     */
    @NotBlank(message = "指引单提示信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String guideNotice;

    /**
     * 项目临床意义
     */
    @NotBlank(message = "项目临床意义不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectClinicalMean;

    /**
     * 项目描述
     */
    @NotBlank(message = "项目描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectDescribe;

    /**
     * 组合项目状态（0正常 1停用）
     */
    @NotBlank(message = "组合项目状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;


}
