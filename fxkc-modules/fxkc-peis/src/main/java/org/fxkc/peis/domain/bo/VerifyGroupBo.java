package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerifyGroupBo {

    /**
     * 主键id
     */
    @NotBlank(message = "主键id不能为空")
    private Long id;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空")
    private String groupName;

    /**
     * 分组方式
     */
    @NotNull(message = "分组方式不能为空")
    private Integer groupType;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    @NotBlank(message = "分组支付方式不能为空")
    private String groupPayType;

    /**
     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    private String addPayType;

    /**
     * 项目折扣
     */
    @NotNull(message = "项目折扣不能为空")
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    private BigDecimal addDiscount;

    /**
     * 是否同步项目(0:是1:否)
     */
    @NotBlank(message = "是否同步项目不能为空")
    private String isSyncProject;


}
