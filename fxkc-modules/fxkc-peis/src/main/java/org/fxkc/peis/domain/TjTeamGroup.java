package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 团检分组信息对象 tj_team_group
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_group")
@Accessors(chain = true)
public class TjTeamGroup extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    private Integer groupType;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄开始段
     */
    private Integer startAge;

    /**
     * 年龄结束段
     */
    private Integer endAge;

    /**
     * 婚姻状况sys_dict_type(bus_marriage_status)
     */
    private String marriage;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    private String groupPayType;

    /**
     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    private String addPayType;

    /**
     * 项目折扣
     */
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    private BigDecimal addDiscount;

    /**
     * 是否同步项目(1:是0:否)
     */
    private String isSyncProject;

    /**
     * 标准价格
     */
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

    /**
     * 单位ID
     */
    private Long teamId;

    /**
     * 单位名称
     */
    private String teamName;

    /**
     * 分组编号
     */
    private String groupNumber;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
