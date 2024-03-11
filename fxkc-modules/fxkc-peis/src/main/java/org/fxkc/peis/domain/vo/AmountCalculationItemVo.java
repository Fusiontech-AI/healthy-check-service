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
     * 主键id
     */
    private Long id;

    /**
     * 组合项目主键id
     */
    private Long combinProjectId;

    /**
     * 原始主键id 用于暂存原始列表主键记录
     */
    private Long originId;

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
     * 缴费状态（0：未缴费，1：已缴费，2：申请退费中，3：已退费，）见字典bus_pay_status
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

    /**
     * 加项标识:1个人加项 2团队加项
     */
    private String addFlag;

    /**
     * 是否必选(1:是0否)
     */
    private Boolean required;
}
