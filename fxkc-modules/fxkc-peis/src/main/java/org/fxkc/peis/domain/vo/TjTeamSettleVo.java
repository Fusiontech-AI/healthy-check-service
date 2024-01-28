package org.fxkc.peis.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

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
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 单位ID
     */
    @ExcelProperty(value = "单位ID")
    private Long teamId;

    /**
     * 单位任务ID
     */
    @ExcelProperty(value = "单位任务ID")
    private Long teamTaskId;

    /**
     * 收费批次号
     */
    @ExcelProperty(value = "收费批次号")
    private String chargeNumber;

    /**
     * 结算人
     */
    @ExcelProperty(value = "结算人")
    private String settleOfficer;

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
    @ExcelProperty(value = "支付方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=微信,2=支付宝,3=现金,4=银行卡")
    private String payType;

    /**
     * 是否打印发票（0否 1是）
     */
    @ExcelProperty(value = "是否打印发票", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=否,1=是")
    private String printInvoice;

    /**
     * 发票号
     */
    @ExcelProperty(value = "发票号")
    private String invoiceNumber;

    /**
     * 状态（0正常 2废弃）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,2=废弃")
    private String status;

    /**
     * 审核人
     */
    @ExcelProperty(value = "审核人")
    private String auditor;

    /**
     * 审核状态（0待审核 1已审核 2审核不通过）
     */
    @ExcelProperty(value = "审核状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=待审核,1=已审核,2=审核不通过")
    private String checkStatus;

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
