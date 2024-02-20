package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjRegProjectSummary;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检登记基础项目小结视图对象 tj_reg_project_summary
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegProjectSummary.class)
public class TjRegProjectSummaryVo implements Serializable {

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
     * 基础项目名称
     */
    @ExcelProperty(value = "基础项目名称")
    private String basicProjectName;

    /**
     * 小结内容
     */
    @ExcelProperty(value = "小结内容")
    private String summaryContent;


}
