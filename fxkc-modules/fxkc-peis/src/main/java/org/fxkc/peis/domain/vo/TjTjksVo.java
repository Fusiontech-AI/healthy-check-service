package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjTjks;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检科室视图对象 tj_tjks
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTjks.class)
public class TjTjksVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 科室编码
     */
    @ExcelProperty(value = "科室编码")
    private String ksCode;

    /**
     * 科室名称
     */
    @ExcelProperty(value = "科室名称")
    private String ksName;

    /**
     * 科室简拼
     */
    @ExcelProperty(value = "科室简拼")
    private String ksSimplePy;

    /**
     * 是否打印条码 0是 1否
     */
    @ExcelProperty(value = "是否打印条码 0是 1否")
    private String printFlag;

    /**
     * 科室显示序号
     */
    @ExcelProperty(value = "科室显示序号")
    private Long ksSort;

    /**
     * 角色状态（0正常 1停用）
     */
    @ExcelProperty(value = "科室状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;


}
