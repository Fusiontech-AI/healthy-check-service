package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检人员登记信息对象 tj_register
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_register")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TjRegister extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

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
     * 年龄
     */
    private Integer age;

    /**
     * 性别（0：男，1：女，2：未知）,见字典sys_user_sex
     */
    private String gender;

    /**
     * 婚姻状况（0：未婚，1：已婚，2：不限）,见字典bus_marriage_status
     */
    private String marriageStatus;

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    private String credentialType;

    /**
     * 证件号
     */
    private String credentialNumber;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 民族
     */
    private String nation;

    /**
     * 体检类型，见字典bus_physical_type
     */
    private String physicalType;

    /**
     * 电话
     */
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
     * 收件人
     */
    private String recipient;

    /**
     * 收件电话
     */
    private String receiptPhone;

    /**
     * 收件地址
     */
    private String postAddress;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 业务类型（1：个检，2：团检）见字典bus_category
     */
    private String businessType;

    /**
     * 是否回收指引单（0：是，1：否）
     */
    private String guideSheetRecived;

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
     * 介绍人
     */
    private Long introducer;

    /**
     * 报到人
     */
    private Long reporter;

    /**
     * 总检医生
     */
    private Long generalReviewDoctor;

    /**
     * 总检时间
     */
    private Date generalReviewTime;

    /**
     * 体检日期
     */
    private Date healthyCheckTime;

    /**
     * 审核医生
     */
    private Long auditDoctor;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 取消登记时间
     */
    private Date cancelRegisterTime;

    /**
     * 取消登记操作人
     */
    private Long cancelRegisterOperator;

    /**
     * 人员状态（0：正常，1：取消登记）
     */
    private String status;

}
