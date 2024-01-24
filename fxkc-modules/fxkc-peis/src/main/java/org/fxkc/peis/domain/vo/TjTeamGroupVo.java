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
    private String dutyStatus;

    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    @ExcelProperty(value = "分组方式sys_dict_type(bus_group_type)")
    private Integer groupType;


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
