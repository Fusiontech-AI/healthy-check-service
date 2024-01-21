package org.fxkc.peis.domain.bo;

import lombok.experimental.Accessors;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjOccupationalDict;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 职业病字典业务对象 tj_occupational_dict
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjOccupationalDict.class, reverseConvertGenerate = false)
@Accessors(chain = true)
public class TjOccupationalDictBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String typeName;

    /**
     * 编号
     */
    @NotBlank(message = "编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;

    /**
     * 值
     */
    @NotBlank(message = "值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String value;

    /**
     * 分类
     */
    private String sort;

    /**
     * 分类编号
     */
    private String sortCode;


}
