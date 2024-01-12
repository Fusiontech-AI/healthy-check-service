package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjBasicProjectRef;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 体检基础项目参考信息视图对象 tj_basic_project_ref
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjBasicProjectRef.class)
public class TjBasicProjectRefVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 基础项目主键id
     */
    @ExcelProperty(value = "基础项目主键id")
    private Long basicProjectId;

    /**
     * 1男 2女
     */
    @ExcelProperty(value = "1男 2女")
    private String sex;

    /**
     * 年龄开始值
     */
    @ExcelProperty(value = "年龄开始值")
    private Long ageStart;

    /**
     * 年龄结束值
     */
    @ExcelProperty(value = "年龄结束值")
    private Long ageEnd;

    /**
     * 健康参考开始值
     */
    @ExcelProperty(value = "健康参考开始值")
    private BigDecimal healthReferStart;

    /**
     * 健康参考结束值
     */
    @ExcelProperty(value = "健康参考结束值")
    private BigDecimal healthReferEnd;

    /**
     * 职业参考开始值
     */
    @ExcelProperty(value = "职业参考开始值")
    private BigDecimal careerReferStart;

    /**
     * 职业参考结束值
     */
    @ExcelProperty(value = "职业参考结束值")
    private BigDecimal careerReferEnd;




}
