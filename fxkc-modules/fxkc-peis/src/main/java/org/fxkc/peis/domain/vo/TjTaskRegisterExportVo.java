package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import lombok.Data;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;

@Data
@ExcelIgnoreUnannotated
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class TjTaskRegisterExportVo {

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 性别（0：男，1：女，2：未知）,见字典sys_user_sex
     */
    @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String gender;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    private Integer age;

    /**
     * 证件号
     */
    @ExcelProperty(value = "身份证号")
    private String credentialNumber;

    /**
     * 单位分组名称
     */
    @ExcelProperty(value = "项目分组")
    private String groupName;

    /**
     * 在岗类型
     */
    @ExcelProperty(value = "在岗类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_duty_status")
    private String dutyStatus;

    /**
     * 体检状态（0：预约，1：登记，2：科室分检，3：分检完成，4：待总检，5：已终检）见字典bus_healthy_check_status
     */
    @ExcelProperty(value = "体检状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_healthy_check_status")
    private String healthyCheckStatus;




}
