package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 套餐危害因素关联对象 tj_package_hazards
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_package_hazards")
public class TjPackageHazards extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 危害因素编码
     */
    private String hazardFactorsCode;

    /**
     * 危害因素名称
     */
    private String hazardFactorsName;

    /**
     * 其他危害因素名称
     */
    private String hazardFactorsOther;


}
