package org.fxkc.peis.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRegister;
import org.springframework.format.annotation.DateTimeFormat;

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
public class TjRegisterPageBo extends BaseEntity {

    /**
     * 体检号
     */
    private String healthyCheckCode;

    /**
     * 档案号
     */
    private String recordCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（0：男，1：女，2：未知）,见字典sys_user_sex
     */
    private String gender;


    /**
     * 证件号
     */
    private String credentialNumber;

    /**
     * 体检类型，见字典bus_physical_type
     */
    private String physicalType;

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
    private Long teamId;

    /**
     * 单位分组id
     */
    private Long teamGroupId;

    /**
     * 单位部门id
     */
    private Long teamDeptId;

    /**
     * 总检医生
     */
    private Long generalReviewDoctor;

    /**
     * 总检时间开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date generalReviewTimeStart;

    /**
     * 总检时间结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date generalReviewTimeEnd;

    /**
     * 体检日期开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date healthyCheckTimeStart;

    /**
     * 体检日期结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date healthyCheckTimeEnd;

    /**
     * 审核医生
     */
    private Long auditDoctor;

    /**
     * 审核时间开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTimeStart;

    /**
     * 审核时间结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTimeEnd;

    /**
     * 取消登记时间开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cancelRegisterTimeStart;

    /**
     * 取消登记时间结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cancelRegisterTimeEnd;

    /**
     * 取消登记操作人
     */
    private Long cancelRegisterOperator;

    /**
     * 人员状态（0：正常，1：取消登记）
     */
    private String status;

    /**
     * 人员类别,字典类型bus_person_category
     */
    private String personCategory;

    /**
     * 体检登记(报道)时间开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTimeStart;

    /**
     * 体检登记(报道)时间结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTimeEnd;

    /**
     * 体检登记(报道)医生id
     */
    private Long registerDoctorId;

    /**
     * 体检收费时间开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date chargeTimeStart;

    /**
     * 体检收费时间结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date chargeTimeEnd;

    /**
     * 总检完成标志(0待总检 1 已总检)
     */
    private String insMark;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 体检检查类型11初检 21复检,见字典bus_tj_check_type
     */
    private String checkType;

    /**
     * 是否职业病(0：是，1：否)
     */
    private String occupationalType;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 单位结账id
     */
    private Long teamSettleId;

    /**
     * 单位结账时间
     */
    private Date teamSettleTime;

    /**
     * 创建人姓名
     */
    private String createByName;

}
