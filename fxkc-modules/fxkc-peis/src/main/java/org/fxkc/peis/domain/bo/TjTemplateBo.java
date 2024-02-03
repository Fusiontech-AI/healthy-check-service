package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTemplate;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 体检报告维护业务对象 tj_template
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTemplate.class, reverseConvertGenerate = false)
public class TjTemplateBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotBlank(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 模板ID
     */
    @NotBlank(message = "模板ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateInfoId;

    /**
     * 体检类型
     */
    @NotBlank(message = "体检类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String physicalType;

}
