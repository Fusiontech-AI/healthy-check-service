package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjZdjywhTjks;

import java.io.Serial;
import java.io.Serializable;


/**
 * 诊断建议和科室关联视图对象 tj_zdjywh_tjks
 *
 * @author JunBaiChen
 * @date 2024-03-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjZdjywhTjks.class)
public class TjZdjywhTjksVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检科室id
     */
    @ExcelProperty(value = "体检科室id")
    private Long ksId;

    /**
     * 诊断建议id
     */
    @ExcelProperty(value = "诊断建议id")
    private Long zdjyId;


}
