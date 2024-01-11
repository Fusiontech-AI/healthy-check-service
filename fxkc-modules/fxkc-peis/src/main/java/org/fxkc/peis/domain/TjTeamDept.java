package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 单位部门信息对象 tj_team_dept
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_dept")
public class TjTeamDept extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 单位id
     */
    private Long teamId;

    /**
     * 部门编号
     */
    private String deptNo;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门负责人
     */
    private String deptManager;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
