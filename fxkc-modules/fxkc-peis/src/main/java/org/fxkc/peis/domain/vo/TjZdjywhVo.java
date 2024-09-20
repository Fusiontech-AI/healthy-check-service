package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjZdjywh;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 诊断建议主视图对象 tj_zdjywh
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjZdjywh.class)
public class TjZdjywhVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 主要诊断
     */
    @ExcelProperty(value = "主要诊断")
    private String zyzd;

    /**
     * 建议名称
     */
    @ExcelProperty(value = "建议名称")
    private String jymc;

    /**
     * 诊断描述
     */
    @ExcelProperty(value = "诊断描述")
    private String zdms;

    /**
     * 是否疾病（0-是，1-否）
     */
    @ExcelProperty(value = "是否疾病", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-是，1-否")
    private String sfjb;

    /**
     * 常见疾病（0-是，1-否）
     */
    @ExcelProperty(value = "常见疾病", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-是，1-否")
    private String cjjb;

    /**
     * 拼音简码
     */
    @ExcelProperty(value = "拼音简码")
    private String pyjm;

    /**
     * 科普说明
     */
    @ExcelProperty(value = "科普说明")
    private String kpsm;

    /**
     * 职业病建议
     */
    @ExcelProperty(value = "职业病建议")
    private String zybjy;

    /**
     * ICD-10疾病编码
     */
    @ExcelProperty(value = "ICD-10疾病编码")
    private String icdCode;

    /**
     * 看诊科室推荐编码
     */
    @ExcelProperty(value = "看诊科室推荐编码")
    private String kzkstjCode;

    /**
     * 看诊科室推荐名称
     */
    @ExcelProperty(value = "看诊科室推荐名称")
    private String kzkstjName;

    /**
     * 重要程度(1正常2一般3重要4非常重要)
     */
    @ExcelProperty(value = "重要程度(1正常2一般3重要4非常重要)")
    private String importance;

    /**
     * 程度排序
     */
    @ExcelProperty(value = "程度排序")
    private Long degreeSort;


}
