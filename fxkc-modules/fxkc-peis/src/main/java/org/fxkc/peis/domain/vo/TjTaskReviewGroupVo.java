package org.fxkc.peis.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.domain.TjTeamTask;

import java.math.BigDecimal;

@Data
@AutoMapper(target = TjTeamGroup.class)
@Accessors(chain = true)
public class TjTaskReviewGroupVo {

    /**
     * 任务id
     */
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
     * 总人数
     */
    private Long allNum;

    /**
     * 预约人数
     */
    private Long appointNum;

    /**
     * 报到人数
     */
    private Long checkInNum;

}
