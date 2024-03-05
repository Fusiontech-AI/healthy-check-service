package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjBasicCommonResult;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检基础项目常见结果视图对象 tj_basic_common_result
 *
 * @author JunBaiChen
 * @date 2024-03-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjBasicCommonResult.class)
public class TjBasicCommonResultVo implements Serializable {

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
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String name;

    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private Integer sort;


}
