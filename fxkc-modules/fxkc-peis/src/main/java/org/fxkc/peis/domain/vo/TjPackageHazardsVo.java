package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjPackageHazards;

import java.io.Serial;
import java.io.Serializable;


/**
 * 套餐危害因素关联视图对象 tj_package_hazards
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjPackageHazards.class)
public class TjPackageHazardsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 危害因素编码
     */
    @ExcelProperty(value = "危害因素编码")
    private String hazardFactorsCode;

    /**
     * 危害因素名称
     */
    @ExcelProperty(value = "危害因素名称")
    private String hazardFactorsName;

    /**
     * 其他危害因素名称
     */
    @ExcelProperty(value = "其他危害因素名称")
    private String hazardFactorsOther;


}
