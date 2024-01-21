package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 危害因素必检项目关联对象 hazard_factors_item
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hazard_factors_item")
public class HazardFactorsItem extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 危害因素必检项目主表id
     */
    private Long factorsId;

    /**
     * 基础项目id
     */
    private Long itemId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
