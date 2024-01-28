package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
    private String name;

    /**
     * 身份证号(此列格式需改为文本)
     */
    @ExcelProperty("身份证号(此列格式需改为文本)")
    private String idCard;

    /**
     * 性别
     */
    @ExcelProperty("性别")
    private String gender;

    /**
     * 年龄
     */
    @ExcelProperty("年龄")
    private Integer age;

    /**
     * 身份证号
     */
    @ExcelProperty("婚否")
    @ExcelDictFormat(dictType = "bus_personnel_marriage_status")
    private String marriageStatus;

    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    private String phone;

    /**
     * 分组
     */
    @ExcelProperty("分组")
    private String groupName;


}
