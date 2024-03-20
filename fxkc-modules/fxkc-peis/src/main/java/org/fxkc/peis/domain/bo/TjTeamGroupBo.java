package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjTeamGroup;

import java.math.BigDecimal;

/**
 * 团检分组信息业务对象 tj_team_group
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamGroup.class, reverseConvertGenerate = false)
public class TjTeamGroupBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 单位id
     */
    private Long teamId;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupName;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    @NotBlank(message = "分组方式不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupType;

    /**
     * 性别sys_dict_type(bus_gender)
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
    @NotBlank(message = "分组支付方式不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupPayType;

    /**
     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    private String addPayType;

    /**
     * 项目折扣
     */
    @NotNull(message = "项目折扣不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    private BigDecimal addDiscount;

    /**
     * 实际折扣
     */
    private BigDecimal discount;

    /**
     * 是否同步项目(0:是1:否)
     */
    @NotBlank(message = "是否同步项目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isSyncProject;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 是否过滤无项目分组(0:是1:否)
     */
    private String filterProject;
}
