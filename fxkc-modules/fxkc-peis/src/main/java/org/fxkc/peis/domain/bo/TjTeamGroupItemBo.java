package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamGroupItem;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 团检分组对应项目业务对象 tj_team_group_item
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamGroupItem.class, reverseConvertGenerate = false)
public class TjTeamGroupItemBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 分组id
     */
    @NotNull(message = "分组id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long groupId;

    /**
     * 组合项目id
     */
    @NotNull(message = "组合项目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long itemId;

    /**
     * 组合项目名称
     */
    @NotBlank(message = "组合项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String itemName;

    /**
     * 标准价格
     */
    @NotNull(message = "标准价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long standardPrice;

    /**
     * 实际价格
     */
    @NotNull(message = "实际价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long actualPrice;

    /**
     * 折扣
     */
    @NotNull(message = "折扣不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long discount;

    /**
     * 是否套餐包含的项目1是2否
     */
    @NotBlank(message = "是否套餐包含的项目1是2否不能为空", groups = { AddGroup.class, EditGroup.class })
    private String include;

    /**
     * 是否必选(1:是0否)
     */
    @NotNull(message = "是否必选(1:是0否)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Boolean isRequired;


}
