package org.fxkc.peis.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 体检套餐算费单项业务响应前端对象 AmountCalculationItemVo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class AmountCalculationItemVo {

    /**
     * 排列序号
     */
    private Integer sort;

    /**
     * 标准价格
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
     * 个人应收金额
     */
    private BigDecimal personAmount;

    /**
     * 单位应收金额
     */
    private BigDecimal teamAmount;

    /**
     * 变更类型(0个人 1单位 2混合支付)
     */
    private String payType;

    /**
     * 缴费状态(1未缴费 2已缴费 3无需缴费)
     */
    private String payStatus;

    /**
     * 组合项目编码
     */
    private String combinProjectCode;

    /**
     * 组合项目名称
     */
    private String combinProjectName;


    /**
     * 是否套餐项目标志
     */
    private String tcFlag;
}
