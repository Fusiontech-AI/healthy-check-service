package org.fxkc.peis.domain.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 动态算费相应前端业务对象 AmountCalculationVo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class AmountCalculationVo {

    /**
     * 体检类型(1个检 2团检)
     */
    @NotBlank(message = "个检或团检类型不能为空")
    private String regType;

    /**
     * 变更类型(1单项 2总计项 3新增  4删除)
     */
    @NotBlank(message = "变更类型不能为空")
    private String changeType;

    /**
     * 输入类型(1折扣 2应收金额 3收费方式)
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
     * 个人应收金额总计
     */
    private BigDecimal personAmount;

    /**
     * 单位应收金额总计
     */
    private BigDecimal teamAmount;

    /**
     * 已缴总费用
     */
    private BigDecimal paidTotalAmount;

    /**
     * 未缴总费用
     */
    private BigDecimal unPaidTotalAmount;

    /**
     * 已缴个人费用
     */
    private BigDecimal paidPersonAmount;

    /**
     * 已缴单位费用
     */
    private BigDecimal paidTeamAmount;

    /**
     * 修改序号
     */
    @NotNull(message = "修改序号不能为空")
    private Integer sort;

    /**
     * 响应前端项目list信息
     */
    @Valid
    private List<AmountCalculationItemVo> amountCalculationItemVos;

}
