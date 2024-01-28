package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检登记基础项目关联对象 tj_reg_basic_project
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("tj_reg_basic_project")
public class TjRegBasicProject extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

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
     * 检查部位
     */
    private String checkPart;

    /**
     * 是否阳性 0是 1否
     */
    private String isPositive;

    /**
     * 是否正常（0-正常 1-不正常 2-偏高 3-偏低4-高于极限 5低于极限 ）字典bus_check_result
     */
    private String isAbnormal;

    /**
     * 参考值
     */
    private String reference;

    /**
     * $column.columnComment
     */
    private String checkResult;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数值上限
     */
    private String upperLimit;

    /**
     * 数值下限
     */
    private String lowLimit;

    /**
     * $column.columnComment
     */
    @TableLogic
    private String delFlag;


}
