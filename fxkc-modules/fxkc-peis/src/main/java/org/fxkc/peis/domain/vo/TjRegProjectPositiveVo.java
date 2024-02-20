package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjRegProjectPositive;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检登记基础项目阳性记录视图对象 tj_reg_project_positive
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegProjectPositive.class)
public class TjRegProjectPositiveVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检登记诊断明细主键id
     */
    @ExcelProperty(value = "体检登记诊断明细主键id")
    private Long zdmxId;

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
     * 组合项目名称
     */
    @ExcelProperty(value = "组合项目名称")
    private String combinProjectName;

    /**
     * 基础项目id
     */
    @ExcelProperty(value = "基础项目id")
    private Long basicProjectId;

    /**
     * 检查类型0检查项目 1化验项目 2功能项目
     */
    @ExcelProperty(value = "检查类型0检查项目 1化验项目 2功能项目")
    private String checkType;

    /**
     * 科室主键id
     */
    @ExcelProperty(value = "科室主键id")
    private Long ksId;

    /**
     * 是否显示 0是 1否
     */
    @ExcelProperty(value = "是否显示 0是 1否")
    private String showFlag;

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
     * 阳性列表排序
     */
    @ExcelProperty(value = "阳性列表排序")
    private Integer positiveSort;


}
