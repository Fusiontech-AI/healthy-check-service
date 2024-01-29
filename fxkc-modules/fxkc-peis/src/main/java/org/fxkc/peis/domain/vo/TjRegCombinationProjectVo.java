package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.fxkc.peis.domain.TjRegCombinationProject;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 体检人员综合项目信息视图对象 tj_reg_combination_project
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegCombinationProject.class)
public class TjRegCombinationProjectVo implements Serializable {

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
    private Long registerId;

    /**
     * 组合项目id
     */
    @ExcelProperty(value = "组合项目id")
    private Long combinationProjectId;

    /**
     * 项目类型（1：套餐项目，2：加项项目）见字典bus_combination_project_type
     */
    @ExcelProperty(value = "项目类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_combination_project_type")
    private String projectType;

    /**
     * 标准金额
     */
    @ExcelProperty(value = "标准金额")
    private BigDecimal standardAmount;

    /**
     * 折扣
     */
    @ExcelProperty(value = "折扣")
    private BigDecimal discount;

    /**
     * 应收金额
     */
    @ExcelProperty(value = "应收金额")
    private BigDecimal receivableAmount;

    /**
     * 个人费用
     */
    private BigDecimal personAmount;

    /**
     * 单位费用
     */
    private BigDecimal teamAmount;

    /**
     * 缴费状态（0：未缴费，1：已缴费，2：申请退费中，3：已退费，）见字典bus_pay_status
     */
    @ExcelProperty(value = "缴费状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_pay_status")
    private String payStatus;

    /**
     * 支付方式（0：个人支付，1：单位支付，2：混合支付）见字典bus_pay_mode
     */
    @ExcelProperty(value = "支付方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_pay_mode")
    private String payMode;

    /**
     * 检查状态（0：未检查，1：已检查，2：弃捡，3：未保存，4：延期）见字典bus_check_status
     */
    @ExcelProperty(value = "检查状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_check_status")
    private String checkStatus;

    /**
     * 项目属性（0：选检项目，1：必检项目）见字典bus_project_required_type
     */
    @ExcelProperty(value = "项目属性", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_project_required_type")
    private String projectRequiredType;

    /**
     * 弃捡时间
     */
    @ExcelProperty(value = "弃捡时间")
    private Date abandonTime;

    /**
     * 延期时间
     */
    @ExcelProperty(value = "延期时间")
    private Date delayTime;

    /**
     * 延期原因
     */
    @ExcelProperty(value = "延期原因")
    private String delayReason;

    /**
     * 检查医生
     */
    @ExcelProperty(value = "检查医生")
    private Long checkDoctor;

    /**
     * 检查时间
     */
    @ExcelProperty(value = "检查时间")
    private Date checkTime;

    /**
     * 检查结果
     */
    @ExcelProperty(value = "检查结果")
    private String checkResult;


}
