package org.fxkc.peis.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 获取体检单位结账金额统计
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
public class TjTeamSettleAmountStatisticsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 实收金额
     */
    private BigDecimal receivedAmount;

    /**
     * 已结金额
     */
    private BigDecimal settledAmount;

    /**
     * 余额
     */
    private BigDecimal balance;

}
