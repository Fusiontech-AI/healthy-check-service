package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 体检人员综合项目信息对象 tj_reg_combination_project
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tj_reg_combination_project")
public class TjRegCombinationProject extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检人员id
     */
    private Long registerId;

    /**
     * 组合项目id
     */
    private Long combinationProjectId;

    /**
     * 项目类型（1：套餐项目，2：加项项目）见字典bus_combination_project_type
     */
    private String projectType;

    /**
     * 标准金额
     */
    private BigDecimal standardAmount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 应收金额
     */
    private BigDecimal receivableAmount;

    /**
     * 个人费用
     */
    private BigDecimal personAmount;

    /**
     * 单位费用
     */
    private BigDecimal teamAmount;

    /**
     * 缴费状态（0：未缴费，1：已缴费，2：申请退费中，3：已退费，）见字典bus_pay_status
     */
    private String payStatus;

    /**
     * 支付方式（0：个人支付，1：单位支付，2：混合支付）见字典bus_pay_mode
     */
    private String payMode;

    /**
     * 检查状态（0：未检查，1：已检查，2：弃捡，3：未保存，4：延期）见字典bus_check_status
     */
    private String checkStatus;

    /**
     * 项目属性（0：选检项目，1：必检项目）见字典bus_project_required_type
     */
    private String projectRequiredType;

    /**
     * 弃捡时间
     */
    private Date abandonTime;

    /**
     * 延期时间
     */
    private Date delayTime;

    /**
     * 延期原因
     */
    private String delayReason;

    /**
     * 检查医生
     */
    private Long checkDoctor;

    /**
     * 检查时间
     */
    private Date checkTime;

    /**
     * 检查结果
     */
    private String checkResult;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
