package org.fxkc.peis.domain.vo;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.fxkc.peis.domain.TjTeamGroup;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 团检分组信息视图对象 tj_team_group
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamGroup.class)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TjTeamGroupVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 分组名称
     */
    @ExcelProperty(value = "分组名称")
    private String groupName;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    @ExcelProperty(value = "在岗状态sys_dict_type(bus_duty_status)")
    private String dutyStatus;

    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    @ExcelProperty(value = "分组方式sys_dict_type(bus_group_type)")
    private String groupType;

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
     * 金额
     */
    private BigDecimal price;

    /**
     * 婚姻状况sys_dict_type(bus_marriage_status)
     */
    private String marriage;

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
    @ExcelProperty(value = "项目折扣")
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    @ExcelProperty(value = "加项折扣")
    private BigDecimal addDiscount;

    /**
     * 是否同步项目(0:是1:否)
     */
    @ExcelProperty(value = "是否同步项目(0:是1:否)")
    private String isSyncProject;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 分组项目信息集合
     */
    private List<TjTeamGroupItemVo> groupItemList;

    /**
     * 危害因素集合
     */
    @Builder.Default
    private List<TjTeamGroupHazardsVo> groupHazardsList = CollUtil.newArrayList();

    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类sys_dict_type(bus_job_illumination_source)
     */
    private String shineType;

    /**
     * 标准价格
     */
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

    /**
     * 任务id
     */
    private Long taskId;

}
