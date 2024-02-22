package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
