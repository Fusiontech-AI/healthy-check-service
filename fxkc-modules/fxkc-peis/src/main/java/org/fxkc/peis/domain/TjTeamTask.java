package org.fxkc.peis.domain;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 团检任务管理对象 tj_team_task
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_task")
@Accessors(chain = true)
public class TjTeamTask extends TenantEntity {

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
     * 任务名称
     */
    private String taskName;

    /**
     * 体检类型sys_dict_type(bus_physical_type)
     */
    private String physicalType;

    /**
     * 签订日期
     */
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date signDate;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date beginDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date endDate;

    /**
     * 收费类型sys_dict_type(bus_charge_type)
     */
    private String chargeType;

    /**
     * 是否审核(0:是1:否)
     */
    private String isReview;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 任务折扣实收金额
     */
    private BigDecimal taskReceived;

    /**
     * 任务折扣优惠金额
     */
    private BigDecimal taskDiscount;

    /**
     * 是否封账(1:是0:否)
     */
    private String isSeal;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 体检联系人电话
     */
    private String contactPhone;

    /**
     * 销售负责人
     */
    private String saleHead;

    /**
     * 编制人
     */
    private String preparedName;

    /**
     * 审核结论(0:待审1:通过2:驳回)
     */
    private String reviewResult;

    /**
     * 审核人
     */
    private Long reviewBy;

    /**
     * 单位部门id
     */
    private Long teamDeptId;
}
