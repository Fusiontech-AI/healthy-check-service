package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 体检组合项目详细信息对象 tj_package_info
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_package_info")
public class TjPackageInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 套餐主键id
     */
    private Long packageId;

    /**
     * 组合项目主键id
     */
    private Long combinProjectId;

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
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 是否必选(1:是0否)
     */
    private Boolean required;
}
