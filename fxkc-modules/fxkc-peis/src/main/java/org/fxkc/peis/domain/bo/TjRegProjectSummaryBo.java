package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRegProjectSummary;

/**
 * 体检登记基础项目小结业务对象 tj_reg_project_summary
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjRegProjectSummary.class, reverseConvertGenerate = false)
public class TjRegProjectSummaryBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检登记基础项目主键id
     */
    @NotNull(message = "体检登记基础项目主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long basicItemId;

    /**
     * 体检登记项目记录主键id
     */
    @NotNull(message = "体检登记项目记录主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regItemId;

    /**
     * 体检登记id
     */
    @NotNull(message = "体检登记id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regId;

    /**
     * 组合项目id
     */
    @NotNull(message = "组合项目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long combinationProjectId;

    /**
     * 基础项目id
     */
    @NotNull(message = "基础项目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long basicProjectId;

    /**
     * 基础项目名称
     */
    @NotBlank(message = "基础项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String basicProjectName;

    /**
     * 小结内容
     */
    @NotBlank(message = "小结内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String summaryContent;


}
