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
    @ExcelDictFormat(dictType = "bus_gender")
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

    /**
     * 业务类型（1：个检，2：团检）见字典bus_category
     */
    @ExcelProperty(value = "业务类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_category")
    private String businessType;

    /**
     * 是否回收指引单（0：是，1：否）
     */
    @ExcelProperty(value = "是否回收指引单", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=：是，1：否")
    private String guideSheetRecived;

    /**
     * 是否冻结（0：是，1：否）
     */
    @ExcelProperty(value = "是否冻结", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=：是，1：否")
    private String freezeStatus;

    /**
     * 体检状态（0：预约，1：登记，2：科室分检，3：分检完成，4：待总检，5：已终检）见字典bus_healthy_check_status
     */
    @ExcelProperty(value = "体检状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_healthy_check_status")
    private String healthyCheckStatus;

    /**
     * 单位id
     */
    @ExcelProperty(value = "单位id")
    private Long teamId;

    /**
     * 单位分组id
     */
    @ExcelProperty(value = "单位分组id")
    private Long teamGroupId;

    /**
     * 单位部门id
     */
    @ExcelProperty(value = "单位部门id")
    private Long teamDeptId;

    /**
     * 介绍人
     */
    @ExcelProperty(value = "介绍人")
    private Long introducer;

    /**
     * 报到人
     */
    @ExcelProperty(value = "报到人")
    private Long reporter;

    /**
     * 总检医生
     */
    @ExcelProperty(value = "总检医生")
    private Long generalReviewDoctor;

    /**
     * 总检时间
     */
    @ExcelProperty(value = "总检时间")
    private Date generalReviewTime;

    /**
     * 体检日期
     */
    @ExcelProperty(value = "体检日期")
    private Date healthyCheckTime;

    /**
     * 审核医生
     */
    @ExcelProperty(value = "审核医生")
    private Long auditDoctor;

    /**
     * 审核时间
     */
    @ExcelProperty(value = "审核时间")
    private Date auditTime;

    /**
     * 完成时间
     */
    @ExcelProperty(value = "完成时间")
    private Date finishTime;

    /**
     * 取消登记时间
     */
    @ExcelProperty(value = "取消登记时间")
    private Date cancelRegisterTime;

    /**
     * 取消登记操作人
     */
    @ExcelProperty(value = "取消登记操作人")
    private Long cancelRegisterOperator;

    /**
     * 人员状态（0：正常，1：取消登记）
     */
    @ExcelProperty(value = "人员状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=：正常，1：取消登记")
    private String status;
}
