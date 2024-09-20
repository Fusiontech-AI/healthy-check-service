package org.fxkc.peis.domain.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 体检人员综合项目信息业务对象 TjRegCombinAddBo
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
public class TjRegCombinAddBo {

    /**
     * 体检人员id
     */
    @NotNull(message = "体检人员id不能为空")
    private Long registerId;

    /**
     * 操作类型（1:登记，2:报道 3:变更项目 4:暂存）
     */
    @NotBlank(message = "操作类型（1:登记，2:报道 3:变更项目 4:暂存）不能为空")
    private String operationType;

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
    @NotNull(message = "个人应收金额总计不能为空")
    private BigDecimal personAmount;

    /**
     * 单位应收金额总计
     */
    @NotNull(message = "单位应收金额总计不能为空")
    private BigDecimal teamAmount;

    /**
     * 已缴总费用
     */
    @NotNull(message = "已缴总费用不能为空")
    private BigDecimal paidTotalAmount;

    /**
     * 已缴个人费用
     */
    @NotNull(message = "已缴个人费用不能为空")
    private BigDecimal paidPersonAmount;

    /**
     * 已缴单位费用
     */
    @NotNull(message = "已缴单位费用不能为空")
    private BigDecimal paidTeamAmount;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 批量标志（0:是）
     */
    private String batchFlag;

    @Valid
    private List<TjRegCombinItemBo> tjRegCombinItemBos;

}
