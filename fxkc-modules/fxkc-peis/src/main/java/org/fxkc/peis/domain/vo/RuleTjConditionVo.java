package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.RuleTjCondition;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检项目规则条件视图对象 rule_tj_condition
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = RuleTjCondition.class)
public class RuleTjConditionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 规则项id
     */
    @ExcelProperty(value = "规则项id")
    private Long ruleId;

    /**
     * 条件名称
     */
    @ExcelProperty(value = "条件名称")
    private String name;

    /**
     * 变量名
     */
    @ExcelProperty(value = "变量名")
    private String variableName;

    /**
     * 变量类型 1数字 2字符
     */
    @ExcelProperty(value = "变量类型 1数字 2字符")
    private String variableType;

    /**
     * 比较值
     */
    @ExcelProperty(value = "比较值")
    private String referenceValue;

    /**
     * 比较类型（大于小于）
     */
    @ExcelProperty(value = "比较类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "大=于小于")
    private String relationType;

    /**
     * 逻辑符号 AND/XOR
     */
    @ExcelProperty(value = "逻辑符号 AND/XOR")
    private String logicType;

    /**
     * 优先级
     */
    @ExcelProperty(value = "优先级")
    private Integer priority;


}
