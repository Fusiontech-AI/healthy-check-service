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


}
