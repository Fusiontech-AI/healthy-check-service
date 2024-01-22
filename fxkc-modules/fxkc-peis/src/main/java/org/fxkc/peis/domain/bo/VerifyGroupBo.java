package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VerifyGroupBo {

    @NotBlank(message = "分组id不能为空")
//    @ApiModelProperty(value = "分组id")
    private String groupId;

    @NotBlank(message = "是否同步项目不能为空")
//    @ApiModelProperty(value = "是否同步项目(1:是0:否)")
    private String isSyncProject;

    @NotBlank(message = "分组类型不能为空")
//    @ApiModelProperty(value = "分组类型")
    private String groupType;

    @NotNull(message = "项目折扣不能为空")
//    @ApiModelProperty(value = "项目折扣")
    private BigDecimal projectDiscount;

    @NotNull(message = "加项折扣不能为空")
//    @ApiModelProperty(value = "加项折扣")
    private BigDecimal addItemDiscount;

    @NotBlank(message = "分组支付方式不能为空")
//    @ApiModelProperty(value = "分组支付方式:0个人 1单位")
    private String groupPayType;

    @NotBlank(message = "加项支付方式不能为空")
//    @ApiModelProperty(value = "加项支付方式:0个人 1单位")
    private String addItemPayType;

//    @ApiModelProperty(value = "实际价格(项目分组传)")
    private BigDecimal actualPrice;

//    @ApiModelProperty(value = "金额(金额、折扣分组传)")
    private BigDecimal amount;

//    @ApiModelProperty(value = "项目id(项目分组传)")
    private List<String> itemList;


//    /**
//     * 主键id
//     */
//    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
//    private Long id;
//
//    /**
//     * 分组方式sys_dict_type(bus_group_type)
//     */
//    @NotNull(message = "分组方式不能为空")
//    private Integer groupType;
//
//
//    /**
//     * 金额
//     */
//    private BigDecimal price;
//
//    /**
//     * 分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
//     */
//    private String groupPayType;
//
//    /**
//     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
//     */
//    private String addPayType;
//
//    /**
//     * 项目折扣
//     */
//
//    private BigDecimal itemDiscount;
//
//    /**
//     * 加项折扣
//     */
//    private BigDecimal addDiscount;
//
//    /**
//     * 是否同步项目(1:是0:否)
//     */
//    private String isSyncProject;

}
