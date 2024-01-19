package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjOccupationalDict;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 职业病字典视图对象 tj_occupational_dict
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjOccupationalDict.class)
public class TjOccupationalDictVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型")
    private String type;

    /**
     * 类型名称
     */
    @ExcelProperty(value = "类型名称")
    private String typeName;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    private String code;

    /**
     * 值
     */
    @ExcelProperty(value = "值")
    private String value;

    /**
     * 分类
     */
    @ExcelProperty(value = "分类")
    private String sort;

    /**
     * 分类编号
     */
    @ExcelProperty(value = "分类编号")
    private String sortCode;


}
