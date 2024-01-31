package org.fxkc.peis.domain.bo;

import cn.hutool.core.collection.CollUtil;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.fxkc.peis.domain.TjTeamGroupItem;

import java.math.BigDecimal;
import java.util.List;

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
    @NotBlank(message = "分组名称不能为空")
    private String groupName;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    @NotNull(message = "分组方式不能为空")
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
    @NotBlank(message = "分组支付方式不能为空")
    private String groupPayType;

    /**
     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    private String addPayType;

    /**
     * 项目折扣
     */
    @NotNull(message = "项目折扣不能为空")
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    private BigDecimal addDiscount;

    /**
     * 是否同步项目(0:是1:否)
     */
    @NotBlank(message = "是否同步项目")
    private String isSyncProject;

    /**
     * 任务id
     */
    private Long taskId;

}
