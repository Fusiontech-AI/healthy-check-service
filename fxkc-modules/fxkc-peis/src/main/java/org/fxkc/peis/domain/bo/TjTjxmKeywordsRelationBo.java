package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjTjxmKeywordsRelation;

/**
 * 体检项目关键字和诊断建议关系业务对象 tj_tjxm_keywords_relation
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTjxmKeywordsRelation.class, reverseConvertGenerate = false)
public class TjTjxmKeywordsRelationBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotBlank(message = "主键id不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 关键词id
     */
    @NotBlank(message = "关键词id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String keywordId;

    /**
     * 诊断建议id
     */
    @NotNull(message = "诊断建议id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long zdjyId;


}
