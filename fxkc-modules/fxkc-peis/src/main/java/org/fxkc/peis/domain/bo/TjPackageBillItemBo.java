package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 体检套餐算费单项业务对象 TjPackageBillItemBo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class TjPackageBillItemBo {

    /**
     * 排列序号
     */
    @NotNull(message = "排列序号不能为空")
    private Integer sort;

    /**
     * 标准价格
     */
    @NotNull(message = "标准金额不能为空")
    private BigDecimal standardAmount;

    /**
     * 折扣
     */
    @NotNull(message = "折扣不能为空")
    private BigDecimal discount;

    /**
     * 应收金额
     */
    @NotNull(message = "应收金额不能为空")
    private BigDecimal receivableAmount;



}
