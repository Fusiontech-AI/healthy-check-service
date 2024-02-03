package org.fxkc.peis.domain.bo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjRegister;

import java.util.Date;
import java.util.List;

/**
 * 体检人员登记信息新增业务对象 TjRegisterAddBo
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@Data
@AutoMapper(target = TjRegister.class)
public class TjRegisterAddBo {

    /**
     * 主键id
     */
    private Long id;

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
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date birthday;

    /**
     * 民族
     */
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
     * 业务类别（1：个检，2：团检）见字典bus_category
     */
    @NotBlank(message = "业务类别（1：个检，2：团检）见字典bus_category不能为空", groups = { AddGroup.class, EditGroup.class })
    private String businessCategory;

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
     * 体检日期
     */
    @NotNull(message = "体检日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date healthyCheckTime;


    /**
     * 证件图片
     */
    private String credentialImage;

    /**
     * 人员类别,字典类型bus_person_category
     */
    private String personCategory;

    /**
     * 费别,字典类型bus_cost_type
     */
    private String costType;

    /**
     * 付费类型,字典类型bus_pay_type
     */
    private String payType;

    /**
     * 体检预约时间
     */
    private Date healthyReserveTime;

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
     * 排班id
     */
    private Long schedulingId;

    /**
     * 体检检查类型11初检 21复检,见字典bus_tj_check_type
     */
    @NotBlank(message = "体检检查类型11初检 21复检,见字典bus_tj_check_type不能为空", groups = { AddGroup.class, EditGroup.class })
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
    private TjRegisterZybBo tjRegisterZybBo;

    /**
     * 职业病关联危害因素
     */
    private List<TjRegisterZybHazardBo> tjRegisterZybHazardBos;
}
