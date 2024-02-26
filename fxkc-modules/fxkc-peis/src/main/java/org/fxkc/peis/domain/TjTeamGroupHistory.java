package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 体检分组人员历史对象 tj_team_group_history
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_group_history")
@Accessors(chain = true)
@AutoMapper(target = TjTeamGroup.class)
public class TjTeamGroupHistory extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检人员id
     */
    private Long regId;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

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
     * 标准价格
     */
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类sys_dict_type(bus_job_illumination_source)
     */
    private String shineType;
}
