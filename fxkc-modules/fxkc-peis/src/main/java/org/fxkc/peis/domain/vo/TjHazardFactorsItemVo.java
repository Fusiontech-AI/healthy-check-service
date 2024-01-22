package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjHazardFactorsItem;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 危害因素必检项目关联视图对象 hazard_factors_item
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjHazardFactorsItem.class)
public class TjHazardFactorsItemVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 危害因素必检项目主表id
     */
    @ExcelProperty(value = "危害因素必检项目主表id")
    private Long factorsId;

    /**
     * 基础项目id
     */
    @ExcelProperty(value = "基础项目id")
    private Long itemId;


}
