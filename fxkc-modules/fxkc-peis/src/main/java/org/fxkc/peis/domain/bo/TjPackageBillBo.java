package org.fxkc.peis.domain.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 体检套餐算费业务对象 TjPackageBillBo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class TjPackageBillBo {


    /**
     * 变更类型(1单项 2总计项)
     */
    @NotBlank(message = "变更类型不能为空")
    private String changeType;

    /**
     * 输入类型(1折扣 2应收金额)
     */
    @NotBlank(message = "输入类型不能为空")
    private String inputType;

    /**
     * 总计项标准价格
     */
    @NotNull(message = "总计项标准金额不能为空")
    private BigDecimal standardAmount;

    /**
     * 总计项折扣
     */
    @NotNull(message = "总计项折扣不能为空")
    private BigDecimal discount;

    /**
     * 总计项应收金额
     */
    @NotNull(message = "总计项应收金额不能为空")
    private BigDecimal receivableAmount;

    /**
     * 修改序号
     */
    @NotNull(message = "修改序号不能为空")
    private Integer sort;

    /**
     * 单项list信息
     */
    @Valid
    private List<TjPackageBillItemBo> tjPackageBillItemBos;



}
