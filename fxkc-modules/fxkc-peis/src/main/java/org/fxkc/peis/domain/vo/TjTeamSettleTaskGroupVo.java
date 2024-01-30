package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjTeamGroup;

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
public class TjTeamSettleTaskGroupVo implements Serializable {

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
     * 单位应收金额
     */
    @ExcelProperty(value = "单位应收金额")
    private BigDecimal teamReceiveAmount = BigDecimal.ZERO;

    /**
     * 累计人数
     */
    @ExcelProperty(value = "累计人数")
    private Long totalPeople = 0L;

    /**
     * 分组金额
     */
    @ExcelProperty(value = "分组金额")
    private BigDecimal groupAmount = BigDecimal.ZERO;

    /**
     * 分组支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    @ExcelProperty(value = "分组支付方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=个人,1=单位")
    @Translation(type = TransConstant.DICT_TYPE_TO_LABEL, other = "bus_group_pay_type")
    private String groupPayType;

    /**
     * 加项人数
     */
    @ExcelProperty(value = "加项人数")
    private Long addPeople = 0L;

    /**
     * 加项金额
     */
    @ExcelProperty(value = "加项金额")
    private BigDecimal addAmount = BigDecimal.ZERO;

    /**
     * 加项支付方式sys_dict_type(bus_group_pay_type)0:个人1:单位
     */
    @ExcelProperty(value = "加项支付方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=个人,1=单位")
    @Translation(type = TransConstant.DICT_TYPE_TO_LABEL, other = "bus_group_pay_type")
    private String addPayType;



}
