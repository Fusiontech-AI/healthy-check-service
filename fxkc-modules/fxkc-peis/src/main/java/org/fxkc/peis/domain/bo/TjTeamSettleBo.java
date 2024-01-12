package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.peis.domain.TjTeamSettle;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 体检单位结账业务对象 tj_team_settle
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamSettle.class, reverseConvertGenerate = false)
public class TjTeamSettleBo extends BaseEntity {

    /**
     * 单位ID
     */
    @NotNull(message = "单位ID不能为空", groups = { AddGroup.class })
    private Long teamId;

    /**
     * 单位任务ID
     */
    @NotNull(message = "单位任务ID不能为空", groups = { AddGroup.class })
    private Long teamTaskId;

    /**
     * 结算时间
     */
    @NotNull(message = "结算时间不能为空", groups = { AddGroup.class })
    private Date settleTime;

    /**
     * 实收金额
     */
    @NotNull(message = "实收金额不能为空", groups = { AddGroup.class })
    private BigDecimal receivedAmount;

    /**
     * 支付方式（1微信 2支付宝 3现金 4银行卡）
     */
    @NotBlank(message = "支付方式不能为空", groups = { AddGroup.class })
    private String payType;


}
