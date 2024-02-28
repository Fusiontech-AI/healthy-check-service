package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRegister;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 体检人员登记信息业务对象 tj_register
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjRegister.class, reverseConvertGenerate = false)
public class TjRegisterBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检号
     */
    @NotBlank(message = "体检号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String healthyCheckCode;

    /**
     * 档案号
     */
    private String recordCode;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer age;

    /**
     * 性别（0：男，1：女，2：未知）,见字典sys_user_sex
     */
    @NotBlank(message = "性别（0：男，1：女，2：未知）,见字典sys_user_sex不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gender;

    /**
     * 婚姻状况（0：未婚，1：已婚，2：未知）,见字典bus_personnel_marriage_status
     */
    @NotBlank(message = "婚姻状况（0：未婚，1：已婚，2：未知）,见字典bus_personnel_marriage_status不能为空", groups = { AddGroup.class })
    private String marriageStatus;

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    @NotBlank(message = "证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type不能为空", groups = { AddGroup.class, EditGroup.class })
    private String credentialType;

    /**
     * 证件号
     */
    @NotBlank(message = "证件号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String credentialNumber;

    /**
     * 生日
     */
    @NotNull(message = "生日不能为空", groups = { AddGroup.class})
    private Date birthday;

    /**
     * 民族
     */
    @NotBlank(message = "民族不能为空", groups = { AddGroup.class})
    private String nation;

    /**
     * 体检类型，见字典bus_physical_type
     */
    @NotBlank(message = "体检类型，见字典bus_physical_type不能为空", groups = { AddGroup.class, EditGroup.class })
    private String physicalType;

    /**
     * 电话
     */
    @NotBlank(message = "电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 用户照片
     */
    private String userImage;

    /**
     * 是否需要总检（0：需要总检，1：无需总检）见字典bus_need_general_review
     */
    private String needGeneralReview;

    /**
     * 报告领取方式0邮寄 1自取,见字典bus_receive_way
     */
    private String receiveWay;

    /**
     * 收件人
     */
    @NotBlank(message = "收件人不能为空", groups = { AddGroup.class})
    private String recipient;

    /**
     * 收件电话
     */
    @NotBlank(message = "收件电话不能为空", groups = { AddGroup.class})
    private String receiptPhone;

    /**
     * 收件地址
     */
    @NotBlank(message = "收件地址不能为空", groups = { AddGroup.class})
    private String postAddress;

    /**
     * 业务类别（1：个检，2：团检）见字典bus_category
     */
    private String businessCategory;

    /**
     * 是否回收指引单（0：是，1：否）
     */
    private String guideSheetReceived;

    /**
     * 是否冻结（0：是，1：否）
     */
    private String freezeStatus;

    /**
     * 体检状态（0：预约，1：登记，2：科室分检，3：分检完成，4：待总检，5：已终检）见字典bus_healthy_check_status
     */
    private String healthyCheckStatus;

    /**
     * 单位id
     */
    @NotNull(message = "单位id不能为空", groups = { AddGroup.class})
    private Long teamId;

    /**
     * 单位分组id
     */
    @NotNull(message = "单位分组id不能为空", groups = { AddGroup.class})
    private Long teamGroupId;

    /**
     * 单位部门id
     */
    @NotNull(message = "单位部门id不能为空", groups = { AddGroup.class})
    private Long teamDeptId;

    /**
     * 介绍人
     */
    @NotNull(message = "介绍人不能为空", groups = { AddGroup.class})
    private Long introducer;

    /**
     * 总检医生
     */
    @NotNull(message = "总检医生不能为空", groups = { AddGroup.class})
    private Long generalReviewDoctor;

    /**
     * 总检时间
     */
    @NotNull(message = "总检时间不能为空", groups = { AddGroup.class})
    private Date generalReviewTime;

    /**
     * 体检日期
     */
    @NotNull(message = "体检日期不能为空", groups = { AddGroup.class})
    private Date healthyCheckTime;

    /**
     * 审核医生
     */
    @NotNull(message = "审核医生不能为空", groups = { AddGroup.class})
    private Long auditDoctor;

    /**
     * 审核时间
     */
    @NotNull(message = "审核时间不能为空", groups = { AddGroup.class})
    private Date auditTime;

    /**
     * 完成时间
     */
    @NotNull(message = "完成时间不能为空", groups = { AddGroup.class})
    private Date finishTime;

    /**
     * 取消登记时间
     */
    @NotNull(message = "取消登记时间不能为空", groups = { AddGroup.class})
    private Date cancelRegisterTime;

    /**
     * 取消登记操作人
     */
    @NotNull(message = "取消登记操作人不能为空", groups = { AddGroup.class})
    private Long cancelRegisterOperator;

    /**
     * 人员状态（0：正常，1：取消登记）
     */
    private String status;

    /**
     * 证件图片
     */
    private String credentialImage;

    /**
     * 人员类别,字典类型bus_person_category
     */
    @NotBlank(message = "人员类别,字典类型bus_person_category不能为空", groups = { AddGroup.class})
    private String personCategory;

    /**
     * 费别,字典类型bus_cost_type
     */
    @NotBlank(message = "费别,字典类型bus_cost_type不能为空", groups = { AddGroup.class})
    private String costType;

    /**
     * 付费类型,字典类型bus_pay_type
     */
    @NotBlank(message = "付费类型,字典类型bus_pay_type不能为空", groups = { AddGroup.class})
    private String payType;

    /**
     * 总计项折扣
     */
    private BigDecimal discount;

    /**
     * 总标准费用(原始金额)
     */
    private BigDecimal totalStandardAmount;

    /**
     * 总费用
     */
    private BigDecimal totalAmount;

    /**
     * 个人费用
     */
    private BigDecimal personAmount;

    /**
     * 单位费用
     */
    private BigDecimal teamAmount;

    /**
     * 已缴总费用
     */
    private BigDecimal paidTotalAmount;

    /**
     * 已缴个人费用
     */
    private BigDecimal paidPersonAmount;

    /**
     * 已缴单位费用
     */
    private BigDecimal paidTeamAmount;

    /**
     * 体检登记(报道)时间
     */
    private Date registerTime;

    /**
     * 体检登记(报道)医生id
     */
    private Long registerDoctorId;

    /**
     * 体检支付方式(渠道),字典类型bus_pay_way
     */
    private String payWay;

    /**
     * 体检收费状态,字典类型bus_pay_status
     */
    private String chargeStatus;

    /**
     * 体检收费时间
     */
    private Date chargeTime;

    /**
     * 体检流程状态,字典类型bus_process_status
     */
    private String processStatus;

    /**
     * 体检次数
     */
    private Long peTimes;

    /**
     * 总检完成标志(0待总检 1 已总检)
     */
    private String insMark;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 指引单打印次数
     */
    private Long guidePrintTimes;

    /**
     * 报告是否打印(0是 1否)
     */
    private String reportPrintFlag;

    /**
     * 报告打印时间
     */
    private Date reportPrintTime;

    /**
     * 报告打印次数
     */
    private Long reportPrintTimes;

    /**
     * 报告领取时间
     */
    private Date reportReceiverTime;

    /**
     * 团检预约到期时间
     */
    private Date teamCheckExpireTime;

    /**
     * 体检预约时间
     */
    private Date healthyReserveTime;

    /**
     * 体检预约开始时间段
     */
    private String reserveStartTime;

    /**
     * 体检预约结束时间段
     */
    private String reserveEndTime;

    /**
     * 单位是否收费(0是 1否)
     */
    private String teamChargeStatus;

    /**
     * 患者id
     */
    private String patientId;

    /**
     * 就诊流水号
     */
    private String visitSerialNo;

    /**
     * 姓名拼音
     */
    private String namePy;

    /**
     * 登记来源,字典类型bus_register_source
     */
    private String registerSource;

    /**
     * 体检综述
     */
    private String peConclusion;

    /**
     * 总检建议
     */
    private String peAdvice;

    /**
     * 替检标志 0是 1否
     */
    private String replaceFlag;

    /**
     * 被替检人姓名
     */
    private String replaceName;

    /**
     * 被替检人证件类型,见字典bus_credential_type
     */
    private String replaceCredentialType;

    /**
     * 被替检人证件号码
     */
    private String replaceCredentialNumber;

    /**
     * 被替检人生日
     */
    private Date replaceBirthday;

    /**
     * 被替检人性别
     */
    private String replaceGender;

    /**
     * 被替检人年龄
     */
    private Integer replaceAge;

    /**
     * 排班id
     */
    private Long schedulingId;

    /**
     * 体检检查类型11初检 21复检,见字典bus_tj_check_type
     */
    @NotBlank(message = "体检检查类型11初检 21复检,见字典bus_tj_check_type不能为空", groups = { AddGroup.class})
    private String checkType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 联系人地址
     */
    private String contactAddress;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 是否职业病(0：是，1：否)
     */
    private String occupationalType;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 职业病关联信息
     */
    @Valid
    private TjRegisterZybBo tjRegisterZybBo;
}
