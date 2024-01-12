package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjPackageInfo;

import java.math.BigDecimal;

/**
 * 体检组合项目详细信息业务对象 tj_package_info
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjPackageInfo.class, reverseConvertGenerate = false)
public class TjPackageInfoBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 套餐主键id
     */
    @NotNull(message = "套餐主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long packageId;

    /**
     * 组合项目主键id
     */
    @NotNull(message = "组合项目主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long combinProjectId;

    /**
     * 标准价格
     */
    @NotNull(message = "标准价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal standardAmount;

    /**
     * 折扣
     */
    @NotNull(message = "折扣不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal discount;

    /**
     * 应收金额
     */
    @NotNull(message = "应收金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal receivableAmount;


}
