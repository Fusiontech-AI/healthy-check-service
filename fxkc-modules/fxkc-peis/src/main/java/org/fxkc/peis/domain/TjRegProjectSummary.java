package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检登记基础项目小结对象 tj_reg_project_summary
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_reg_project_summary")
public class TjRegProjectSummary extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检登记基础项目主键id
     */
    private Long basicItemId;

    /**
     * 体检登记项目记录主键id
     */
    private Long regItemId;

    /**
     * 体检登记id
     */
    private Long regId;

    /**
     * 组合项目id
     */
    private Long combinationProjectId;

    /**
     * 基础项目id
     */
    private Long basicProjectId;

    /**
     * 基础项目名称
     */
    private String basicProjectName;

    /**
     * 小结内容
     */
    private String summaryContent;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
