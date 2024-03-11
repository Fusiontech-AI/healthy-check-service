package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.peis.domain.vo.AmountCalculationItemVo;

import java.math.BigDecimal;

/**
 * 体检套餐算费单项业务对象 AmountCalculationItemBo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@AutoMapper(target = AmountCalculationItemVo.class, reverseConvertGenerate = false)
public class AmountCalculationItemBo {

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

    /**
     * 个人应收金额
     */
    @NotNull(message = "个人应收金额不能为空")
    private BigDecimal personAmount;

    /**
     * 单位应收金额
     */
    @NotNull(message = "单位应收金额不能为空")
    private BigDecimal teamAmount;

    /**
     * 变更类型(0个人 1单位 2混合支付)
     */
    @NotBlank(message = "支付方式不能为空")
    private String payType;

    /**
     * 缴费状态（0：未缴费，1：已缴费，2：申请退费中，3：已退费，）见字典bus_pay_status
     */
    @NotBlank(message = "缴费状态不能为空")
    private String payStatus;

    /**
     * 组合项目名称
     */
    private String combinProjectName;

    /**
     * 组合项目编码
     */
    private String combinProjectCode;

    /**
     * 是否套餐项目标志
     */
    @NotBlank(message = "是否套餐项目标志不能为空")
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
