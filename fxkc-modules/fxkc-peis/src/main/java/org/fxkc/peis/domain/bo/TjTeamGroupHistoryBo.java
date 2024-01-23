package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamGroupHistory;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * 体检分组人员历史业务对象 tj_team_group_history
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamGroupHistory.class, reverseConvertGenerate = false)
public class TjTeamGroupHistoryBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检人员id
     */
    @NotNull(message = "体检人员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regId;

    /**
     * 分组id
     */
    @NotNull(message = "分组id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long groupId;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupName;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    @NotBlank(message = "在岗状态sys_dict_type(bus_duty_status)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dutyStatus;

    /**
     * 性别sys_dict_type(bus_gender)
     */
    @NotBlank(message = "性别sys_dict_type(bus_gender)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gender;

    /**
     * 年龄开始段
     */
    @NotNull(message = "年龄开始段不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer startAge;

    /**
     * 年龄结束段
     */
    @NotNull(message = "年龄结束段不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer endAge;

    /**
     * 婚姻状况sys_dict_type(bus_marriage_status)
     */
    @NotBlank(message = "婚姻状况sys_dict_type(bus_marriage_status)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String marriage;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal price;

    /**
     * 分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    @NotBlank(message = "分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupPayType;

    /**
     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    @NotBlank(message = "加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String addPayType;

    /**
     * 项目折扣
     */
    @NotNull(message = "项目折扣不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    @NotNull(message = "加项折扣不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal addDiscount;

    /**
     * 标准价格
     */
    @NotNull(message = "标准价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    @NotNull(message = "实际价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal actualPrice;


}
