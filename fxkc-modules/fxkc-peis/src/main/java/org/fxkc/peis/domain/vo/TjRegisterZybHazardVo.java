package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjRegisterZybHazard;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 体检登记职业病危害因素视图对象 tj_register_zyb_hazard
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegisterZybHazard.class)
public class TjRegisterZybHazardVo implements Serializable {

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
     * 危害因素编码
     */
    @ExcelProperty(value = "危害因素编码")
    private String hazardFactor;

    /**
     * 危害因素名称
     */
    @ExcelProperty(value = "危害因素名称")
    private String hazardFactorName;

    /**
     * 危害因素其他名称
     */
    @ExcelProperty(value = "危害因素其他名称")
    private String hazardFactorOther;

    /**
     * 开始接害日期,条件必填，体检类型为岗前体检该项非必填
     */
    @ExcelProperty(value = "开始接害日期,条件必填，体检类型为岗前体检该项非必填")
    private Date hazardStartDate;

    /**
     * 接触所监测危害因素工龄年,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月
     */
    @ExcelProperty(value = "接触所监测危害因素工龄年,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月")
    private Long hazardYear;

    /**
     * 接触所监测危害因素工龄月,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月
     */
    @ExcelProperty(value = "接触所监测危害因素工龄月,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月")
    private Long hazardMonth;


}
