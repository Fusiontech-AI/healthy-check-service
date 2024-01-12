package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 体检基础项目参考信息对象 tj_basic_project_ref
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_basic_project_ref")
public class TjBasicProjectRef extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 基础项目主键id
     */
    private Long basicProjectId;

    /**
     * 1男 2女
     */
    private String sex;

    /**
     * 年龄开始值
     */
    private Long ageStart;

    /**
     * 年龄结束值
     */
    private Long ageEnd;

    /**
     * 健康参考开始值
     */
    private BigDecimal healthReferStart;

    /**
     * 健康参考结束值
     */
    private BigDecimal healthReferEnd;

    /**
     * 职业参考开始值
     */
    private BigDecimal careerReferStart;

    /**
     * 职业参考结束值
     */
    private BigDecimal careerReferEnd;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
