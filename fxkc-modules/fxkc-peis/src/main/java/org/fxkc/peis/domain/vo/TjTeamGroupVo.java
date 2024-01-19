package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjTeamGroup;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 团检分组信息视图对象 tj_team_group
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamGroup.class)
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
    private Integer dutyStatus;

    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    @ExcelProperty(value = "分组方式sys_dict_type(bus_group_type)")
    private Integer groupType;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private String gender;

    /**
     * 年龄开始段
     */
    @ExcelProperty(value = "年龄开始段")
    private Integer startAge;

    /**
     * 年龄结束段
     */
    @ExcelProperty(value = "年龄结束段")
    private Integer endAge;

    /**
     * 婚姻状况sys_dict_type(bus_marriage_status)
     */
    @ExcelProperty(value = "婚姻状况sys_dict_type(bus_marriage_status)")
    private String marriage;

    /**
     * 金额
     */
    @ExcelProperty(value = "金额")
    private BigDecimal price;

    /**
     * 分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    @ExcelProperty(value = "分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位")
    private String groupPayType;

    /**
     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    @ExcelProperty(value = "加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位")
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
     * 是否同步项目(1:是0:否)
     */
    @ExcelProperty(value = "是否同步项目(1:是0:否)")
    private String isSyncProject;

    /**
     * 标准价格
     */
    @ExcelProperty(value = "标准价格")
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    @ExcelProperty(value = "实际价格")
    private BigDecimal actualPrice;

    /**
     * 单位ID
     */
    @ExcelProperty(value = "单位ID")
    private Long teamId;

    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位名称")
    private String teamName;

    /**
     * 分组编号
     */
    @ExcelProperty(value = "分组编号")
    private String groupNumber;

    /**
     * 任务id
     */
    @ExcelProperty(value = "任务id")
    private Long taskId;

    /**
     * 任务名称
     */
    @ExcelProperty(value = "任务名称")
    private String taskName;

    /**
     * 套餐id
     */
    @ExcelProperty(value = "套餐id")
    private Long packageId;

    /**
     * 套餐名称
     */
    @ExcelProperty(value = "套餐名称")
    private String packageName;


}
