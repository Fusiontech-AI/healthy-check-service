package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.fxkc.peis.domain.TjYxType;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 体检阳性分类视图对象 tj_yx_type
 *
 * @author JunBaiChen
 * @date 2024-03-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjYxType.class)
public class TjYxTypeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 父id
     */
    @ExcelProperty(value = "父id")
    private Long parentId;

    /**
     * 类别名称
     */
    @ExcelProperty(value = "类别名称")
    private String name;

    /**
     * 类别序号
     */
    @ExcelProperty(value = "类别序号")
    private Integer sort;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;


    /**
     * 所属子项
     */
    private List<TjYxTypeVo> children;

}
