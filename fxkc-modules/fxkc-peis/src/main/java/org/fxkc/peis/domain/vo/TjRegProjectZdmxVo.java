package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjRegProjectZdmx;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检登记基础项目诊断明细视图对象 tj_reg_project_zdmx
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegProjectZdmx.class)
public class TjRegProjectZdmxVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检登记基础项目主键id
     */
    @ExcelProperty(value = "体检登记基础项目主键id")
    private Long basicItemId;

    /**
     * 体检登记项目记录主键id
     */
    @ExcelProperty(value = "体检登记项目记录主键id")
    private Long regItemId;

    /**
     * 体检登记id
     */
    @ExcelProperty(value = "体检登记id")
    private Long regId;

    /**
     * 组合项目id
     */
    @ExcelProperty(value = "组合项目id")
    private Long combinationProjectId;

    /**
     * 基础项目id
     */
    @ExcelProperty(value = "基础项目id")
    private Long basicProjectId;

    /**
     * 诊断建议主键id
     */
    @ExcelProperty(value = "诊断建议主键id")
    private Long diagnoseAdviceId;

    /**
     * 诊断建议名称
     */
    @ExcelProperty(value = "诊断建议名称")
    private String diagnoseTitle;

    /**
     * 诊断建议
     */
    @ExcelProperty(value = "诊断建议")
    private String diagnoseAdvice;

    /**
     * 规则分类 字典bus_rule_type
     */
    @ExcelProperty(value = "规则分类 字典bus_rule_type")
    private String ruleType;

    /**
     * 规则命中info主键id
     */
    @ExcelProperty(value = "规则命中info主键id")
    private Long ruleInfoId;


}
