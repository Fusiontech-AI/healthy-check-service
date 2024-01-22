package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 体检组合项目对象 tj_combination_project
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_combination_project")
public class TjCombinationProject extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 组合项目编码
     */
    private String combinProjectCode;

    /**
     * 组合项目名称
     */
    private String combinProjectName;

    /**
     * 组合项目简称
     */
    private String combinSimpleName;

    /**
     * 拼音简码
     */
    private String pySimpleCode;

    /**
     * 检查类型0检查项目 1化验项目 2功能项目
     */
    private String checkType;

    /**
     * 科室主键id
     */
    private Long ksId;

    /**
     * 样本类型 0类型1
     */
    private String sampleType;

    /**
     * 是否需要标本
     */
    private String specimenNeedFlag;

    /**
     * 标本类型 0类型1
     */
    private String specimenType;

    /**
     * 标准价格
     */
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
    private String suitSex;

    /**
     * 项目类型 0普通
     */
    private String projectType;

    /**
     * 财务类别 0是
     */
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
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
