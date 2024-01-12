package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.Date;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检单位结账对象 tj_team_settle
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_settle")
public class TjTeamSettle extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 单位ID
     */
    private Long teamId;

    /**
     * 单位任务ID
     */
    private Long teamTaskId;

    /**
     * 收费批次号
     */
    private String chargeNumber;

    /**
     * 结算人
     */
    private String settleOfficer;

    /**
     * 结算时间
     */
    private Date settleTime;

    /**
     * 实收金额
     */
    private BigDecimal receivedAmount;

    /**
     * 支付方式（1微信 2支付宝 3现金 4银行卡）
     */
    private String payType;

    /**
     * 是否打印发票（0否 1是）
     */
    private String printInvoice;

    /**
     * 发票号
     */
    private String invoiceNumber;

    /**
     * 状态（0正常 2废弃）
     */
    private String status;

    /**
     * 审核状态（0待审核 1已审核 2审核不通过）
     */
    private String checkStatus;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
