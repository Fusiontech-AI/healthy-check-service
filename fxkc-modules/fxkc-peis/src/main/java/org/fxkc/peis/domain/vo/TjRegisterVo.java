package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.fxkc.peis.domain.TjRegister;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


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
    private String guideSheetReceived;

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

    /**
     * 证件图片
     */
    @ExcelProperty(value = "证件图片")
    private String credentialImage;

    /**
     * 人员类别,字典类型bus_person_category
     */
    @ExcelProperty(value = "人员类别,字典类型bus_person_category")
    private String personCategory;

    /**
     * 费别,字典类型bus_cost_type
     */
    @ExcelProperty(value = "费别,字典类型bus_cost_type")
    private String costType;

    /**
     * 付费类型,字典类型bus_pay_type
     */
    @ExcelProperty(value = "付费类型,字典类型bus_pay_type")
    private String payType;

    /**
     * 总费用
     */
    @ExcelProperty(value = "总费用")
    private Long totalAmount;

    /**
     * 个人费用
     */
    @ExcelProperty(value = "个人费用")
    private Long personAmount;

    /**
     * 单位费用
     */
    @ExcelProperty(value = "单位费用")
    private Long teamAmount;

    /**
     * 已缴总费用
     */
    @ExcelProperty(value = "已缴总费用")
    private Long paidTotalAmount;

    /**
     * 已缴个人费用
     */
    @ExcelProperty(value = "已缴个人费用")
    private Long paidPersonAmount;

    /**
     * 已缴单位费用
     */
    @ExcelProperty(value = "已缴单位费用")
    private Long paidTeamAmount;

    /**
     * 体检登记(报道)时间
     */
    @ExcelProperty(value = "体检登记(报道)时间")
    private Date registerTime;

    /**
     * 体检登记(报道)医生id
     */
    @ExcelProperty(value = "体检登记(报道)医生id")
    private Long registerDoctorId;

    /**
     * 体检支付方式(渠道),字典类型bus_pay_way
     */
    @ExcelProperty(value = "体检支付方式(渠道),字典类型bus_pay_way")
    private String payWay;

    /**
     * 体检收费状态,字典类型bus_pay_status
     */
    @ExcelProperty(value = "体检收费状态,字典类型bus_pay_status")
    private String chargeStatus;

    /**
     * 体检收费时间
     */
    @ExcelProperty(value = "体检收费时间")
    private Date chargeTime;

    /**
     * 体检流程状态,字典类型bus_process_status
     */
    @ExcelProperty(value = "体检流程状态,字典类型bus_process_status")
    private String processStatus;

    /**
     * 体检次数
     */
    @ExcelProperty(value = "体检次数")
    private Long peTimes;

    /**
     * 总检完成标志(0待总检 1 已总检)
     */
    @ExcelProperty(value = "总检完成标志(0待总检 1 已总检)")
    private String insMark;

    /**
     * 套餐id
     */
    @ExcelProperty(value = "套餐id")
    private Long packageId;

    /**
     * 指引单打印次数
     */
    @ExcelProperty(value = "指引单打印次数")
    private Long guidePrintTimes;

    /**
     * 报告是否打印(0是 1否)
     */
    @ExcelProperty(value = "报告是否打印(0是 1否)")
    private String reportPrintFlag;

    /**
     * 报告打印时间
     */
    @ExcelProperty(value = "报告打印时间")
    private Date reportPrintTime;

    /**
     * 报告打印次数
     */
    @ExcelProperty(value = "报告打印次数")
    private Long reportPrintTimes;

    /**
     * 报告领取时间
     */
    @ExcelProperty(value = "报告领取时间")
    private Date reportReceiverTime;

    /**
     * 团检预约到期时间
     */
    @ExcelProperty(value = "团检预约到期时间")
    private Date teamCheckExpireTime;

    /**
     * 体检预约时间
     */
    @ExcelProperty(value = "体检预约时间")
    private Date healthyReserveTime;

    /**
     * 单位是否收费(0是 1否)
     */
    @ExcelProperty(value = "单位是否收费(0是 1否)")
    private String teamChargeStatus;

    /**
     * 患者id
     */
    @ExcelProperty(value = "患者id")
    private String patientId;

    /**
     * 就诊流水号
     */
    @ExcelProperty(value = "就诊流水号")
    private String visitSerialNo;

    /**
     * 姓名拼音
     */
    @ExcelProperty(value = "姓名拼音")
    private String namePy;

    /**
     * 登记来源,字典类型bus_register_source
     */
    @ExcelProperty(value = "登记来源,字典类型bus_register_source")
    private String registerSource;

    /**
     * 体检综述
     */
    @ExcelProperty(value = "体检综述")
    private String peConclusion;

    /**
     * 总检建议
     */
    @ExcelProperty(value = "总检建议")
    private String peAdvice;

    /**
     * 替检标志 0是 1否
     */
    @ExcelProperty(value = "替检标志 0是 1否")
    private String replaceFlag;

    /**
     * 被替检人姓名
     */
    @ExcelProperty(value = "被替检人姓名")
    private String replaceName;

    /**
     * 被替检人证件类型,见字典bus_credential_type
     */
    @ExcelProperty(value = "被替检人证件类型,见字典bus_credential_type")
    private String replaceCredentialType;

    /**
     * 被替检人证件号码
     */
    @ExcelProperty(value = "被替检人证件号码")
    private String replaceCredentialNumber;

    /**
     * 被替检人生日
     */
    @ExcelProperty(value = "被替检人生日")
    private Date replaceBirthday;

    /**
     * 被替检人性别
     */
    @ExcelProperty(value = "被替检人性别")
    private String replaceGender;

    /**
     * 被替检人年龄
     */
    @ExcelProperty(value = "被替检人年龄")
    private Long replaceAge;

    /**
     * 排班id
     */
    @ExcelProperty(value = "排班id")
    private Long schedulingId;

    /**
     * 体检检查类型1初检 2复检,见字典bus_tj_check_type
     */
    @ExcelProperty(value = "体检检查类型1初检 2复检,见字典bus_tj_check_type")
    private String checkType;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 联系人地址
     */
    @ExcelProperty(value = "联系人地址")
    private String contactAddress;

    /**
     * 联系人邮箱
     */
    @ExcelProperty(value = "联系人邮箱")
    private String contactEmail;

    /**
     * 是否职业病(0：是，1：否)
     */
    @ExcelProperty(value = "是否职业病(0：是，1：否)")
    private String occupationalType;

    /**
     * 任务id
     */
    @ExcelProperty(value = "任务id")
    private Long taskId;
}
