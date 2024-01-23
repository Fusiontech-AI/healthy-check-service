package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjTeamGroupHistory;
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
 * 体检分组人员历史视图对象 tj_team_group_history
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamGroupHistory.class)
public class TjTeamGroupHistoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检人员id
     */
    @ExcelProperty(value = "体检人员id")
    private Long regId;

    /**
     * 分组id
     */
    @ExcelProperty(value = "分组id")
    private Long groupId;

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
     * 性别sys_dict_type(bus_gender)
     */
    @ExcelProperty(value = "性别sys_dict_type(bus_gender)")
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
     * 标准价格
     */
    @ExcelProperty(value = "标准价格")
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    @ExcelProperty(value = "实际价格")
    private BigDecimal actualPrice;


}
