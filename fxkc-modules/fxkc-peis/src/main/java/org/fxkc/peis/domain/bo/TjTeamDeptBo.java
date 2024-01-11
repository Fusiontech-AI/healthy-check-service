package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamDept;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 单位部门信息业务对象 tj_team_dept
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamDept.class, reverseConvertGenerate = false)
public class TjTeamDeptBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 单位id
     */
    @NotNull(message = "单位id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teamId;

    /**
     * 部门编号
     */
    @NotBlank(message = "部门编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deptNo;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deptName;

    /**
     * 部门负责人
     */
    private String deptManager;


}
