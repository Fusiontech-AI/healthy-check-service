package org.fxkc.peis.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 套餐和项目混合分页查询响应对象 PackageAndProjectVo
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
public class PackageAndProjectVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 拼音简码
     */
    private String pySimpleCode;

    /**
     * 1套餐 2项目
     */
    private String type;

    /**
     * 标准价格
     */
    private BigDecimal standardAmount;

    /**
     * 折扣率
     */
    private BigDecimal discount;

    /**
     * 应收金额(实际价格)
     */
    private BigDecimal receivableAmount;

    /**
     * 检查类型0检查项目 1化验项目 2功能项目
     */
    private String checkType;

    /**
     * 是否必选(1:是0否)
     */
    private Boolean required;
}
