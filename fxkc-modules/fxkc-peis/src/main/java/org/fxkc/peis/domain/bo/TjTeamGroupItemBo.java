package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamGroupItem;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * 团检分组对应项目业务对象 tj_team_group_item
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamGroupItem.class)
public class TjTeamGroupItemBo extends BaseEntity {

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 组合项目id
     */
    @NotNull(message = "组合项目id不能为空")
    private Long itemId;

    /**
     * 组合项目名称
     */
    @NotBlank(message = "组合项目名称不能为空")
    private String itemName;

    /**
     * 标准价格
     */
    @NotNull(message = "标准价格不能为空")
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    @NotNull(message = "实际价格不能为空")
    private BigDecimal actualPrice;

    /**
     * 折扣
     */
    @NotNull(message = "折扣不能为空")
    private BigDecimal discount;

    /**
     * 是否套餐包含的项目1是2否
     */
    @NotBlank(message = "是否套餐包含的项目1是2否不能为空")
    private String include;

    /**
     * 是否必选(1:是0否)
     */
    @NotNull(message = "是否必选(true:是0:false)不能为空")
    private Boolean isRequired;


}
