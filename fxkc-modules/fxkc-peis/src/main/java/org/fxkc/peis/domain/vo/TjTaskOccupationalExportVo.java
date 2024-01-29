package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 带有下拉选的Excel导出
 *
 * @author Emil.Zhang
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TjTaskOccupationalExportVo {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    @ColumnWidth(20)
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 身份证号
     */
    @ExcelProperty("身份证号")
    @ColumnWidth(25)
    @NotBlank(message = "身份证号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String idCard;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
    @ColumnWidth(15)
    @ExcelDictFormat(dictType = "sys_user_sex")
    @NotBlank(message = "性别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gender;

    /**
     * 年龄
     */
    @ExcelProperty("年龄")
    @ColumnWidth(15)
    @NotNull(message = "年龄不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer age;

    /**
     * 婚否
     */
    @ExcelProperty(value = "婚否", converter = ExcelDictConvert.class)
    @ColumnWidth(15)
    @ExcelDictFormat(dictType = "bus_personnel_marriage_status")
    private String marriageStatus;

    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    @ColumnWidth(20)
    @NotBlank(message = "联系电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 分组
     */
    @ExcelProperty("分组")
    @ColumnWidth(25)
    private String groupName;

    /**
     * 工种名称
     */
    @ExcelProperty(value = "工种名称", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_job_code")
    @ColumnWidth(25)
    @NotBlank(message = "工种名称不能为空", groups = { EditGroup.class })
    private String jobCode;

    /**
     * 其他工种名称
     */
    @ExcelProperty("其他工种名称")
    @ColumnWidth(25)
    private String otherJobName;

    /**
     * 总工龄年
     */
    @ExcelProperty("总工龄年")
    @ColumnWidth(15)
    @NotBlank(message = "总工龄年不能为空", groups = { EditGroup.class })
    private String seniorityYear;

    /**
     * 总工龄月
     */
    @ExcelProperty("总工龄月")
    @ColumnWidth(15)
    @NotBlank(message = "总工龄月不能为空", groups = { EditGroup.class })
    private String seniorityMonth;

    /**
     * 接害工龄年
     */
    @ExcelProperty("接害工龄年")
    @ColumnWidth(15)
    @NotBlank(message = "接害工龄年不能为空", groups = { EditGroup.class })
    private String contactSeniorityYear;

    /**
     * 接触工龄月
     */
    @ExcelProperty("接触工龄月")
    @ColumnWidth(15)
    @NotBlank(message = "接触工龄月不能为空", groups = { EditGroup.class })
    private String contactSeniorityMonth;

    /**
     * 个案卡类别
     */
    @ExcelProperty(value = "个案卡类别", converter = ExcelDictConvert.class)
    @ColumnWidth(20)
    @ExcelDictFormat(dictType = "bus_case_card_type")
    @NotBlank(message = "个案卡类别不能为空", groups = { EditGroup.class })
    private String caseCardType;

    /**
     * 检查类型
     */
    @ExcelProperty(value = "检查类型", converter = ExcelDictConvert.class)
    @ColumnWidth(20)
    @ExcelDictFormat(dictType = "bus_tj_check_type")
    @NotBlank(message = "检查类型不能为空", groups = { EditGroup.class })
    private String checkType;

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 单位名称
     */
    private String teamName;

    /**
     * 任务名称
     */
    private String taskName;
}
