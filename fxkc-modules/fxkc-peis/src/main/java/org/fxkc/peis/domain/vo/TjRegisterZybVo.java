package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjRegisterZyb;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 体检登记职业病关联视图对象 tj_register_zyb
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegisterZyb.class)
public class TjRegisterZybVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检登记id
     */
    @ExcelProperty(value = "体检登记id")
    private Long regId;

    /**
     * 在岗状态,字典bus_duty_status
     */
    @ExcelProperty(value = "在岗状态,字典bus_duty_status")
    private String dutyStatus;

    /**
     * 照射源,字典bus_shine_source
     */
    @ExcelProperty(value = "照射源,字典bus_shine_source")
    private String illuminationSource;

    /**
     * 职业照射种类,字典bus_job_illumination_source
     */
    @ExcelProperty(value = "职业照射种类,字典bus_job_illumination_source")
    private String jobIlluminationType;

    /**
     * 个案卡类别,字典bus_case_card_type
     */
    @ExcelProperty(value = "个案卡类别,字典bus_case_card_type")
    private String caseCardType;

    /**
     * 工种编码,字典bus_job_code
     */
    @ExcelProperty(value = "工种编码,字典bus_job_code")
    private String jobCode;

    /**
     * 总工龄(年)
     */
    @ExcelProperty(value = "总工龄(年)")
    private Long seniorityYear;

    /**
     * 总工龄(月)
     */
    @ExcelProperty(value = "总工龄(月)")
    private Long seniorityMonth;

    /**
     * 接害工龄(年)
     */
    @ExcelProperty(value = "接害工龄(年)")
    private Long contactSeniorityYear;

    /**
     * 接害工龄(月)
     */
    @ExcelProperty(value = "接害工龄(月)")
    private Long contactSeniorityMonth;

    /**
     * 检查医生
     */
    @ExcelProperty(value = "检查医生")
    private Long checkDoctor;

    /**
     * 检查日期
     */
    @ExcelProperty(value = "检查日期")
    private Date checkDate;

    /**
     * 职业病检查结果
     */
    @ExcelProperty(value = "职业病检查结果")
    private String zybResult;

    /**
     * 职业病处理意见
     */
    @ExcelProperty(value = "职业病处理意见")
    private String zybAdvice;

    /**
     * 定性结论
     */
    @ExcelProperty(value = "定性结论")
    private String dxResult;


}
