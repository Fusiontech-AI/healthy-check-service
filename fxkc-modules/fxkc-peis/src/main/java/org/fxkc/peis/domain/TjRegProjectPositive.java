package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检登记基础项目阳性记录对象 tj_reg_project_positive
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_reg_project_positive")
public class TjRegProjectPositive extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
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
    private String diagnoseTitle;

    /**
     * 诊断建议
     */
    private String diagnoseAdvice;

    /**
     * 阳性列表排序
     */
    private Integer positiveSort;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
