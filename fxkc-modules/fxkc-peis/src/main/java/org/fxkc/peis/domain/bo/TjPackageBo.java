package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjPackage;

import java.math.BigDecimal;

/**
 * 体检套餐业务对象 tj_package
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjPackage.class, reverseConvertGenerate = false)
public class TjPackageBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检类型
     */
    @NotBlank(message = "体检类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tjType;

    /**
     * 适用性别 0男 1女 2不限
     */
    @NotBlank(message = "适用性别 0男 1女 2不限不能为空", groups = { AddGroup.class, EditGroup.class })
    private String suitSex;

    /**
     * 套餐名称
     */
    @NotBlank(message = "套餐名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageName;

    /**
     * 套餐简称
     */
    @NotBlank(message = "套餐简称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageSimpleName;

    /**
     * 拼音简码
     */
    @NotBlank(message = "拼音简码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pySimpleCode;

    /**
     * 组合项目排序
     */
    @NotNull(message = "组合项目排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long packageSort;

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

    /**
     * 组合项目状态（0正常 1停用）
     */
    @NotBlank(message = "组合项目状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;


}
