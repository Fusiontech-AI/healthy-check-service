package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRegCombinationProject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 体检人员综合项目信息业务对象 tj_reg_combination_project
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjRegCombinationProject.class, reverseConvertGenerate = false)
public class TjRegCombinationProjectBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检人员id
     */
    @NotNull(message = "体检人员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long registerId;

    /**
     * 组合项目id
     */
    @NotNull(message = "组合项目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long combinationProjectId;

    /**
     * 项目类型（1：套餐项目，2：加项项目）见字典bus_combination_project_type
     */
    @NotBlank(message = "项目类型（1：套餐项目，2：加项项目）见字典bus_combination_project_type不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectType;

    /**
     * 标准金额
     */
    @NotNull(message = "标准金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal standardAmount;

    /**
     * 折扣
     */
    @NotNull(message = "折扣不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal discount;

    /**
     * 应收金额
     */
    @NotNull(message = "应收金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal receivableAmount;

    /**
     * 个人费用
     */
    @NotNull(message = "个人费用不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal personAmount;

    /**
     * 单位费用
     */
    @NotNull(message = "单位费用不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal teamAmount;

    /**
     * 缴费状态（0：未缴费，1：已缴费，2：申请退费中，3：已退费，）见字典bus_pay_status
     */
    @NotBlank(message = "缴费状态（0：未缴费，1：已缴费，2：申请退费中，3：已退费，）见字典bus_pay_status不能为空", groups = { AddGroup.class, EditGroup.class })
    private String payStatus;

    /**
     * 支付方式（0：个人支付，1：单位支付，2：混合支付）见字典bus_pay_mode
     */
    @NotBlank(message = "支付方式（0：个人支付，1：单位支付，2：混合支付）见字典bus_pay_mode不能为空", groups = { AddGroup.class, EditGroup.class })
    private String payMode;

    /**
     * 检查状态（0：未检查，1：已检查，2：弃捡，3：未保存，4：延期）见字典bus_check_status
     */
    @NotBlank(message = "检查状态（0：未检查，1：已检查，2：弃捡，3：未保存，4：延期）见字典bus_check_status不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkStatus;

    /**
     * 项目属性（0：选检项目，1：必检项目）见字典bus_project_required_type
     */
    @NotBlank(message = "项目属性（0：选检项目，1：必检项目）见字典bus_project_required_type不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectRequiredType;

    /**
     * 弃捡时间
     */
    @NotNull(message = "弃捡时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date abandonTime;

    /**
     * 延期时间
     */
    @NotNull(message = "延期时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date delayTime;

    /**
     * 延期原因
     */
    @NotBlank(message = "延期原因不能为空", groups = { AddGroup.class, EditGroup.class })
    private String delayReason;

    /**
     * 检查医生
     */
    @NotNull(message = "检查医生不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long checkDoctor;

    /**
     * 检查医生姓名
     */
    @NotNull(message = "检查医生姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkDoctorName;

    /**
     * 检查时间
     */
    @NotNull(message = "检查时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date checkTime;

    /**
     * 检查结果
     */
    @NotBlank(message = "检查结果不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkResult;

    /**
     * 加项标识:1个人加项 2团队加项
     */
    private String addFlag;

}
