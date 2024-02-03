package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTemplateInfo;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 体检报告模板业务对象 tj_template_info
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTemplateInfo.class, reverseConvertGenerate = false)
public class TjTemplateInfoBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotBlank(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateName;

    /**
     * 体检类型
     */
    @NotBlank(message = "体检类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String physicalType;

    /**
     * 报告类型
     */
    @NotBlank(message = "报告类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reportType;

    /**
     * 描述
     */
    private String describe;

    /**
     * 模板路径
     */
    @NotBlank(message = "模板路径不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templatePath;

    /**
     * 模板数据
     */
    @NotBlank(message = "模板数据不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateData;

    /**
     * 扩展类型
     */
    @NotBlank(message = "扩展类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String extendType;


}
