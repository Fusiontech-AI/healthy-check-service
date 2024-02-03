package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTemplateExtend;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 体检报告扩展业务对象 tj_template_extend
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTemplateExtend.class, reverseConvertGenerate = false)
public class TjTemplateExtendBo extends BaseEntity {

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 扩展类型
     */
    @NotBlank(message = "扩展类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String extendType;

    /**
     * 显示类型("text":文本,"file":"文件")
     */
    @NotBlank(message = "显示类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String showType;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;


    /**
     * 模板ID
     */
    @NotBlank(message = "模板ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateId;


}
