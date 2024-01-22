package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjHazardFactorsItem;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 危害因素必检项目关联业务对象 hazard_factors_item
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjHazardFactorsItem.class, reverseConvertGenerate = false)
public class TjHazardFactorsItemBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 危害因素必检项目主表id
     */
    @NotNull(message = "危害因素必检项目主表id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long factorsId;

    /**
     * 基础项目id
     */
    @NotNull(message = "基础项目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long itemId;


}
