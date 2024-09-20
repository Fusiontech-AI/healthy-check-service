package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjYxType;

/**
 * 体检阳性分类业务对象 tj_yx_type
 *
 * @author JunBaiChen
 * @date 2024-03-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjYxType.class, reverseConvertGenerate = false)
public class TjYxTypeBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 类别名称
     */
    @NotBlank(message = "类别名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 类别序号
     */
    @NotNull(message = "类别序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer sort;

    /**
     * 状态（0正常 1停用）
     */
    private String status;


}
