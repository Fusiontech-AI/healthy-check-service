package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjCombinationProject;

import java.math.BigDecimal;
import java.util.List;

/**
 * 体检组合项目业务对象 TjCombinationProjectAddBo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@AutoMapper(target = TjCombinationProject.class, reverseConvertGenerate = false)
public class TjCombinationProjectAddBo {

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
    @NotBlank(message = "标本类型 0类型1不能为空", groups = { AddGroup.class, EditGroup.class })
    private String specimenType;

    /**
     * 标准价格
     */
    @NotNull(message = "标准价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal standardAmount;

    /**
     * 折扣率
     */
    private BigDecimal discount;

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
     * 组合项目排序
     */
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
    private String financialType;

    /**
     * 是否外送 0是
     */
    private String outFlag;

    /**
     * 外送回调地址
     */
    private String outAddress;

    /**
     * 当前预约上限
     */
    private Long useLimit;

    /**
     * 指引单提示信息
     */
    private String guideNotice;

    /**
     * 项目临床意义
     */
    private String projectClinicalMean;

    /**
     * 项目描述
     */
    private String projectDescribe;

    /**
     * 组合项目状态（0正常 1停用）
     */
    @NotBlank(message = "组合项目状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    @Valid
    private List<TjCombinationProjectInfoItemBo> infoItemBos;
}
