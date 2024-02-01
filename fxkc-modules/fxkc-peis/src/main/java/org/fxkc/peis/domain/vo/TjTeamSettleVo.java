package org.fxkc.peis.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjTeamSettle;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检单位结账视图对象 tj_team_settle
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamSettle.class)
public class TjTeamSettleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 单位ID
     */
    private Long teamId;

    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位名称")
    @Translation(type = TransConstant.TEAM_ID_TO_NAME, mapper = "teamId")
    private String teamName;

    /**
     * 单位任务ID
     */
    private Long teamTaskId;

    /**
     * 单位任务名称
     */
    @ExcelProperty(value = "单位任务名称")
    @Translation(type = TransConstant.TEAM_TASK_ID_TO_NAME, mapper = "teamTaskId")
    private String teamTaskName;

    /**
     * 收费批次号
     */
    @ExcelProperty(value = "收费批次号")
    private String chargeNumber;

    /**
     * 结算人
     */
    private Long settleOfficer;

    /**
     * 结算人名称
     */
    @ExcelProperty(value = "结算人")
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME,mapper = "settleOfficer")
    private String settleOfficerName;

    /**
     * 结算时间
     */
    @ExcelProperty(value = "结算时间")
    private Date settleTime;

    /**
     * 实收金额
     */
    @ExcelProperty(value = "实收金额")
    private BigDecimal receivedAmount;

    /**
     * 支付方式（1微信 2支付宝 3现金 4银行卡）
     */
    private String payType;

    /**
     * 支付方式名称
     */
    @ExcelProperty(value = "支付方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_team_pay_type")
    @Translation(type = TransConstant.DICT_TYPE_TO_LABEL,other = "bus_team_pay_type",mapper = "payType")
    private String payTypeName;

    /**
     * 是否打印发票（0否 1是）
     */
    private String printInvoice;

    /**
     * 是否打印发票名称
     */
    @ExcelProperty(value = "是否打印发票", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_print_invoice")
    @Translation(type = TransConstant.DICT_TYPE_TO_LABEL,other = "bus_print_invoice",mapper = "printInvoice")
    private String printInvoiceName;

    /**
     * 发票号
     */
    @ExcelProperty(value = "发票号")
    private String invoiceNumber;

    /**
     * 状态（0正常 2废弃）
     */
    private String status;

    /**
     * 状态（0正常 2废弃）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_team_settle_status")
    @Translation(type = TransConstant.DICT_TYPE_TO_LABEL,other = "bus_team_settle_status",mapper = "status")
    private String statusName;

    /**
     * 审核人
     */
    private Long auditor;

    /**
     * 审核人名称
     */
    @ExcelProperty(value = "审核人")
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME,mapper = "auditor")
    private String auditorName;

    /**
     * 审核状态（0待审核 1已审核 2审核不通过）
     */
    private String checkStatus;

    /**
     * 审核状态名称
     */
    @ExcelProperty(value = "审核状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_team_check_status")
    @Translation(type = TransConstant.DICT_TYPE_TO_LABEL,other = "bus_team_check_status",mapper = "checkStatus")
    private String checkStatusName;

    /**
     * 审核时间
     */
    @ExcelProperty(value = "审核时间")
    private Date checkTime;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

}
