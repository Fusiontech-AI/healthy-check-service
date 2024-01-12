package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjCombinationProjectInfo;

/**
 * 体检组合项目详细信息业务对象 tj_combination_project_info
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjCombinationProjectInfo.class, reverseConvertGenerate = false)
public class TjCombinationProjectInfoBo extends BaseEntity {

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
     * 组合项目主键id
     */
    @NotNull(message = "组合项目主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long combinProjectId;


}
