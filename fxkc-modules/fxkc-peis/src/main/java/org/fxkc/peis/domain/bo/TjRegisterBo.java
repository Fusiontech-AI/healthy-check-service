package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

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
    @NotBlank(message = "档案号不能为空", groups = { AddGroup.class, EditGroup.class })
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
     * 婚姻状况（0：未婚，1：已婚，2：不限）,见字典bus_marriage_status
     */
    @NotBlank(message = "婚姻状况（0：未婚，1：已婚，2：不限）,见字典bus_marriage_status不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotNull(message = "生日不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date birthday;

    /**
     * 民族
     */
    @NotBlank(message = "民族不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotBlank(message = "用户照片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userImage;

    /**
     * 是否需要总检（0：需要总检，1：无需总检）见字典bus_need_general_review
     */
    @NotBlank(message = "是否需要总检（0：需要总检，1：无需总检）见字典bus_need_general_review不能为空", groups = { AddGroup.class, EditGroup.class })
    private String needGeneralReview;

    /**
     * 收件人
     */
    @NotBlank(message = "收件人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String recipient;

    /**
     * 收件电话
     */
    @NotBlank(message = "收件电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String receiptPhone;

    /**
     * 收件地址
     */
    @NotBlank(message = "收件地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String postAddress;

    /**
     * 业务类型（1：个检，2：团检）见字典bus_category
     */
    @NotBlank(message = "业务类型（1：个检，2：团检）见字典bus_category不能为空", groups = { AddGroup.class, EditGroup.class })
    private String businessType;

    /**
     * 是否回收指引单（0：是，1：否）
     */
    @NotBlank(message = "是否回收指引单（0：是，1：否）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String guideSheetRecived;

    /**
     * 是否冻结（0：是，1：否）
     */
    @NotBlank(message = "是否冻结（0：是，1：否）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String freezeStatus;

    /**
     * 体检状态（0：预约，1：登记，2：科室分检，3：分检完成，4：待总检，5：已终检）见字典bus_healthy_check_status
     */
    @NotBlank(message = "体检状态（0：预约，1：登记，2：科室分检，3：分检完成，4：待总检，5：已终检）见字典bus_healthy_check_status不能为空", groups = { AddGroup.class, EditGroup.class })
    private String healthyCheckStatus;

    /**
     * 单位id
     */
    @NotNull(message = "单位id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teamId;

    /**
     * 单位分组id
     */
    @NotNull(message = "单位分组id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teamGroupId;

    /**
     * 单位部门id
     */
    @NotNull(message = "单位部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teamDeptId;

    /**
     * 介绍人
     */
    @NotNull(message = "介绍人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long introducer;

    /**
     * 报到人
     */
    @NotNull(message = "报到人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long reporter;

    /**
     * 总检医生
     */
    @NotNull(message = "总检医生不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long generalReviewDoctor;

    /**
     * 总检时间
     */
    @NotNull(message = "总检时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date generalReviewTime;

    /**
     * 体检日期
     */
    @NotNull(message = "体检日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date healthyCheckTime;

    /**
     * 审核医生
     */
    @NotNull(message = "审核医生不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long auditDoctor;

    /**
     * 审核时间
     */
    @NotNull(message = "审核时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date auditTime;

    /**
     * 完成时间
     */
    @NotNull(message = "完成时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date finishTime;

    /**
     * 取消登记时间
     */
    @NotNull(message = "取消登记时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date cancelRegisterTime;

    /**
     * 取消登记操作人
     */
    @NotNull(message = "取消登记操作人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long cancelRegisterOperator;

    /**
     * 人员状态（0：正常，1：取消登记）
     */
    private String status;
}
