package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class TjTeamSettleTaskGroupStatisticsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 个人加项金额
     */
    @ExcelProperty(value = "个人加项金额")
    private BigDecimal personAddAmount = BigDecimal.ZERO;

    /**
     * 单位加项金额
     */
    @ExcelProperty(value = "单位加项金额")
    private BigDecimal teamAddAmount = BigDecimal.ZERO;

}
