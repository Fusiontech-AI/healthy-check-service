package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class TjRegisterImportDetailBo {

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    /**
     * 性别
     */
    @NotBlank(message = "性别不能为空")
    private String gender;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空")
    private Integer age;

    /**
     * 婚否
     */
    private String marriageStatus;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    /**
     * 分组
     */
    @NotBlank(message = "分组不能为空")
    private String groupName;

    /**
     * 工种名称
     */
    private String jobCode;

    /**
     * 其他工种名称
     */
    private String otherJobName;

    /**
     * 总工龄年
     */
    private String seniorityYear;

    /**
     * 总工龄月
     */
    private String seniorityMonth;

    /**
     * 接害工龄年
     */
    private String contactSeniorityYear;

    /**
     * 接触工龄月
     */
    private String contactSeniorityMonth;

    /**
     * 个案卡类别
     */
    private String caseCardType;

    /**
     * 检查类型
     */
    private String checkType;

    /**
     * 流水号
     */
    @NotBlank(message = "流水号不能为空")
    private String serialNumber;

    /**
     * 添加时间
     */
    private Date addTime;


}
