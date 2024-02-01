package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjPackageInfo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 体检组合项目详细信息视图对象 tj_package_info
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjPackageInfo.class)
public class TjPackageInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 套餐主键id
     */
    @ExcelProperty(value = "套餐主键id")
    private Long packageId;

    /**
     * 组合项目主键id
     */
    @ExcelProperty(value = "组合项目主键id")
    private Long combinProjectId;

    /**
     * 组合项目名称
     */
    @Translation(type = TransConstant.COMBINATION_ID_TO_NAME,mapper = "combinProjectId")
    private String combinProjectName;

    /**
     * 组合项目编码
     */
    @Translation(type = TransConstant.COMBINATION_ID_TO_CODE,mapper = "combinProjectId")
    private String combinProjectCode;

    /**
     * 标准价格
     */
    @ExcelProperty(value = "标准价格")
    private BigDecimal standardAmount;

    /**
     * 折扣
     */
    @ExcelProperty(value = "折扣")
    private BigDecimal discount;

    /**
     * 应收金额
     */
    @ExcelProperty(value = "应收金额")
    private BigDecimal receivableAmount;


}
