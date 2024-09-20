package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjTjxmKeywordsType;

/**
 * 体检项目关键字分类业务对象 tj_tjxm_keywords_type
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTjxmKeywordsType.class, reverseConvertGenerate = false)
public class TjTjxmKeywordsTypeBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotBlank(message = "主键id不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 关键词名称
     */
    @NotBlank(message = "关键词名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 状态（0正常 1停用）
     */
    @NotBlank(message = "状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;


}
