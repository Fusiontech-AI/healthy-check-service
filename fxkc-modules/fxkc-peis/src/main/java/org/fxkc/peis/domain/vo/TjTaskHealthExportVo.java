package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;

/**
 * 带有下拉选的Excel导出
 *
 * @author Emil.Zhang
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TjTaskHealthExportVo {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    @ColumnWidth(20)
    private String name;

    /**
     * 身份证号
     */
    @ExcelProperty("身份证号")
    @ColumnWidth(25)
    private String idCard;

    /**
     * 性别
     */
    @ExcelProperty("性别")
    @ColumnWidth(15)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String gender;

    /**
     * 年龄
     */
    @ExcelProperty("年龄")
    @ColumnWidth(15)
    private Integer age;

    /**
     * 身份证号
     */
    @ExcelProperty("婚否")
    @ColumnWidth(15)
    @ExcelDictFormat(dictType = "bus_personnel_marriage_status")
    private String marriageStatus;

    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    @ColumnWidth(20)
    private String phone;

    /**
     * 分组
     */
    @ExcelProperty("分组")
    @ColumnWidth(25)
    private String groupName;


}
