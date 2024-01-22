package org.fxkc.peis.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.fxkc.peis.domain.TjRegister;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检人员登记信息视图对象 tj_register
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegister.class)
public class TjRegisterVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检号
     */
    @ExcelProperty(value = "体检号")
    private String healthyCheckCode;

    /**
     * 档案号
     */
    @ExcelProperty(value = "档案号")
    private String recordCode;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    private Integer age;

    /**
     * 性别（0：男，1：女，2：未知）,见字典sys_user_sex
     */
    @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String gender;

    /**
     * 婚姻状况（0：未婚，1：已婚，2：不限）,见字典bus_marriage_status
     */
    @ExcelProperty(value = "婚姻状况", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_marriage_status")
    private String marriageStatus;

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    @ExcelProperty(value = "证件类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_credential_type")
    private String credentialType;

    /**
     * 证件号
     */
    @ExcelProperty(value = "证件号")
    private String credentialNumber;

    /**
     * 生日
     */
    @ExcelProperty(value = "生日")
    private Date birthday;

    /**
     * 民族
     */
    @ExcelProperty(value = "民族")
    private String nation;

    /**
     * 体检类型，见字典bus_physical_type
     */
    @ExcelProperty(value = "体检类型，见字典bus_physical_type", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_physical_type")
    private String physicalType;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话")
    private String phone;

    /**
     * 用户照片
     */
    @ExcelProperty(value = "用户照片")
    private String userImage;

    /**
     * 是否需要总检（0：需要总检，1：无需总检）见字典bus_need_general_review
     */
    @ExcelProperty(value = "是否需要总检", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_need_general_review")
    private String needGeneralReview;

    /**
     * 收件人
     */
    @ExcelProperty(value = "收件人")
    private String recipient;

    /**
     * 收件电话
     */
    @ExcelProperty(value = "收件电话")
    private String receiptPhone;

    /**
     * 收件地址
     */
    @ExcelProperty(value = "收件地址")
    private String postAddress;


}
