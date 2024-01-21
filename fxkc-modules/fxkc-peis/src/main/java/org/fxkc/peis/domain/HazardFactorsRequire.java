package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 危害因素必检项目主对象 hazard_factors_require
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hazard_factors_require")
public class HazardFactorsRequire extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 危害因素名称
     */
    private String hazardFactorsName;

    /**
     * 危害因素编码
     */
    private String hazardFactorsCode;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

    /**
     * 启用状态(0:启用1:不启用)
     */
    private String enableStatus;

    /**
     * 关联类型(1:必检项目2:目标类型3:职业禁忌症4:症状询问重点5:体格检查6:检查周期7:评价标准)
     */
    private String associationType;

    /**
     * 症状询问重点
     */
    private String symptomFocus;

    /**
     * 体格检查
     */
    private String physicalExamination;

    /**
     * 检查周期
     */
    private String inspectionCycle;

    /**
     * 评价标准
     */
    private String evaluationCriterion;

    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类tj_occupational_dict(type=99)
     */
    private String shineType;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
