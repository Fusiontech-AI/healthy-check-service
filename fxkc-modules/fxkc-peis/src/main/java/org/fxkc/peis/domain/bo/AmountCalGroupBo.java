package org.fxkc.peis.domain.bo;

import lombok.Data;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 团检分组信息对象 AmountCalGroupBo
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
public class AmountCalGroupBo {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    private String groupType;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    private String groupPayType;

    /**
     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    private String addPayType;

    /**
     * 项目折扣
     */
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    private BigDecimal addDiscount;

    public AmountCalGroupBo() {
    }

    public AmountCalGroupBo(String groupType, BigDecimal price, String groupPayType, String addPayType, BigDecimal itemDiscount, BigDecimal addDiscount) {
        this.groupType = groupType;
        this.price = price;
        this.groupPayType = groupPayType;
        this.addPayType = addPayType;
        this.itemDiscount = itemDiscount;
        this.addDiscount = addDiscount;
    }
}
