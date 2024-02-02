package org.fxkc.peis.domain.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 动态算费业务对象 AmountCalculationBo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class AmountCalculationBo {

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
     * 输入类型(1折扣 2应收金额 3收费方式 4个人应收额 5单位应收额 6分组或加项折扣)
     */
    private String inputType;

    /**
     * 有无分组标志(1有分组)
     */
    private String groupFlag;

    /**
     * 分组参数对象
     */
    private AmountCalGroupBo amountCalGroupBo;

    /**
     * 总计项标准价格
     */
    private BigDecimal standardAmount;

    /**
     * 总计项折扣
     */
    private BigDecimal discount;

    /**
     * 总计项应收金额
     */
    private BigDecimal receivableAmount;

    /**
     * 修改序号
     */
    private Integer sort;

    /**
     * 存量(已有)项目list信息
     */
    @Valid
    private List<AmountCalculationItemBo> haveAmountCalculationItemBos;


    /**
     * 新增或删除项目list信息
     */
    @Valid
    private List<AmountCalculationItemBo> amountCalculationItemBos;

}
