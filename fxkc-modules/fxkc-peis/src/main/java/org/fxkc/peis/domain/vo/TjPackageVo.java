package org.fxkc.peis.domain.vo;

import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjPackage;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 体检套餐视图对象 tj_package
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjPackage.class)
public class TjPackageVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检类型
     */
    @ExcelProperty(value = "体检类型")
    private String tjType;

    /**
     * 适用性别 0男 1女 2不限
     */
    @ExcelProperty(value = "适用性别 0男 1女 2不限")
    private String suitSex;

    /**
     * 套餐名称
     */
    @ExcelProperty(value = "套餐名称")
    private String packageName;

    /**
     * 套餐简称
     */
    @ExcelProperty(value = "套餐简称")
    private String packageSimpleName;

    /**
     * 拼音简码
     */
    @ExcelProperty(value = "拼音简码")
    private String pySimpleCode;

    /**
     * 组合项目排序
     */
    @ExcelProperty(value = "组合项目排序")
    private Long packageSort;

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

    /**
     * 组合项目状态（0正常 1停用）
     */
    @ExcelProperty(value = "组合项目状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;


}
