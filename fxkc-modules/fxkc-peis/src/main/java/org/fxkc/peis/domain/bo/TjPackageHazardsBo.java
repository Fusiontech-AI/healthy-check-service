package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjPackageHazards;

/**
 * 套餐危害因素关联业务对象 tj_package_hazards
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjPackageHazards.class, reverseConvertGenerate = false)
public class TjPackageHazardsBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 套餐id
     */
    @NotNull(message = "套餐id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long packageId;

    /**
     * 危害因素编码
     */
    @NotBlank(message = "危害因素编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactorsCode;

    /**
     * 危害因素名称
     */
    @NotBlank(message = "危害因素名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactorsName;

    /**
     * 其他危害因素名称
     */
    @NotBlank(message = "其他危害因素名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactorsOther;


}
