package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 团检分组对应项目对象 tj_team_group_item
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_group_item")
@Accessors(chain = true)
public class TjTeamGroupItem extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 组合项目id
     */
    private Long itemId;

    /**
     * 组合项目名称
     */
    private String itemName;

    /**
     * 标准价格
     */
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 是否套餐包含的项目1是2否
     */
    private String include;

    /**
     * 是否必选(1:是0否)
     */
    private Boolean isRequired;


}
