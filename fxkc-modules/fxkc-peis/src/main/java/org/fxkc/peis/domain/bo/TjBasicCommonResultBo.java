package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjBasicCommonResult;

/**
 * 体检基础项目常见结果业务对象 tj_basic_common_result
 *
 * @author JunBaiChen
 * @date 2024-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjBasicCommonResult.class, reverseConvertGenerate = false)
public class TjBasicCommonResultBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 基础项目主键id
     */
    @NotNull(message = "基础项目主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long basicProjectId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer sort;


}
