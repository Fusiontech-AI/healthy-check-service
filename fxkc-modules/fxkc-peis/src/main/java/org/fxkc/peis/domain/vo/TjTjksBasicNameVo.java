package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 体检科室视图对象 TjTjksBasicNameVo
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@Data
@ExcelIgnoreUnannotated
public class TjTjksBasicNameVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 科室编码
     */
    @ExcelProperty(value = "科室编码")
    private String ksCode;

    /**
     * 科室名称
     */
    @ExcelProperty(value = "科室名称")
    private String ksName;


    private List<TjBasicProjectVo> basicProjectVos;

}
