package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjSample;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检样本视图对象 tj_sample
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjSample.class)
public class TjSampleVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 样本编码
     */
    @ExcelProperty(value = "样本编码")
    private String sampleCode;

    /**
     * 样本名称
     */
    @ExcelProperty(value = "样本名称")
    private String sampleName;

    /**
     * 样本类别
     */
    @ExcelProperty(value = "样本类别")
    private String sampleCategory;

    /**
     * 样本类型
     */
    @ExcelProperty(value = "样本类型")
    private String sampleType;

    /**
     * 条码类型
     */
    @ExcelProperty(value = "条码类型")
    private String barCodeType;

    /**
     * 打印顺序
     */
    @ExcelProperty(value = "打印顺序")
    private Long printSort;

    /**
     * 打印份数
     */
    @ExcelProperty(value = "打印份数")
    private Long printNumber;

    /**
     * 是否打印 0是 1否
     */
    @ExcelProperty(value = "是否打印 0是 1否")
    private String printFlag;

    /**
     * 打印申请单 0打印 1不打印
     */
    @ExcelProperty(value = "打印申请单 0打印 1不打印")
    private String printApplyFlag;

    /**
     * 申请单份数
     */
    @ExcelProperty(value = "申请单份数")
    private Long printApplyNumber;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


    /**
     * 样本状态（0正常 1停用）
     */
    @ExcelProperty(value = "样本状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;
}
