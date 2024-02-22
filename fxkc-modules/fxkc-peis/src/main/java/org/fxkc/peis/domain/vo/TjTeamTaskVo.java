package org.fxkc.peis.domain.vo;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjTeamTask;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 团检任务管理视图对象 tj_team_task
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamTask.class)
public class TjTeamTaskVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 单位id
     */
    @ExcelProperty(value = "单位id")
    private Long teamId;

    /**
     * 名称
     */
    @Translation(type = TransConstant.TEAM_ID_TO_NAME, mapper = "teamId")
    private String teamName;

    /**
     * 任务名称
     */
    @ExcelProperty(value = "任务名称")
    private String taskName;

    /**
     * 体检类型sys_dict_type(bus_physical_type)
     */
    @ExcelProperty(value = "体检类型sys_dict_type(bus_physical_type)")
    private String physicalType;

    /**
     * 签订日期
     */
    @ExcelProperty(value = "签订日期")
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date signDate;

    /**
     * 开始日期
     */
    @ExcelProperty(value = "开始日期")
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date beginDate;

    /**
     * 结束日期
     */
    @ExcelProperty(value = "结束日期")
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date endDate;

    /**
     * 收费类型sys_dict_type(bus_charge_type)
     */
    @ExcelProperty(value = "收费类型sys_dict_type(bus_charge_type)")
    private String chargeType;

    /**
     * 是否审核(0:是1:否)
     */
    @ExcelProperty(value = "是否审核(0:是1:否)")
    private String isReview;

    /**
     * 任务折扣实收金额
     */
    private BigDecimal taskReceived;

    /**
     * 任务折扣优惠金额
     */
    private BigDecimal taskDiscount;

    /**
     * 是否封账(1:是0:否)
     */
    @ExcelProperty(value = "是否封账(1:是0:否)")
    private String isSeal;

    /**
     * 审核结论(0:待审1:通过2:驳回)
     */
    private String reviewResult;

}
