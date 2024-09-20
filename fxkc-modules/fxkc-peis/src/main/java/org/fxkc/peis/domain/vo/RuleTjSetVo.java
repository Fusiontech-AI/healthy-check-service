package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.RuleTjSet;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检项目规则集视图对象 rule_tj_set
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = RuleTjSet.class)
public class RuleTjSetVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 基础项目id
     */
    @ExcelProperty(value = "基础项目id")
    private Long basicProjectId;

    /**
     * 是否职业病(0：职业，1：健康  2老年人)
     */
    @ExcelProperty(value = "是否职业病(0：职业，1：健康  2老年人)")
    private String occupationalType;

    /**
     * 适用项目规则类型 1功能检查
     */
    @ExcelProperty(value = "适用项目规则类型 1功能检查")
    private String xmRuleType;

    /**
     * 规则表达式
     */
    @ExcelProperty(value = "规则表达式")
    private String expression;

    /**
     * 分割符号(多个之间采用和字分割)
     */
    @ExcelProperty(value = "分割符号(多个之间采用和字分割)")
    private String splitSymbol;

    /**
     * 规则类型 1诊断建议 2危急值 3高危异常
     */
    @ExcelProperty(value = "规则类型 1诊断建议 2危急值 3高危异常")
    private String ruleType;


}
