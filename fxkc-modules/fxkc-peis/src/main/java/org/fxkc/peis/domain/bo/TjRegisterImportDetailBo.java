package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@AutoMappers({@AutoMapper(target = TjRegisterAddBo.class), @AutoMapper(target = TjRegisterZybBo.class)})
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
    private String credentialNumber;

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
    private Long teamGroupId;

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
    private Long seniorityYear;

    /**
     * 总工龄月
     */
    private Long seniorityMonth;

    /**
     * 接害工龄年
     */
    private Long contactSeniorityYear;

    /**
     * 接触工龄月
     */
    private Long contactSeniorityMonth;

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
    private String serialNumber;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 照射源,字典bus_shine_source
     */
    private String illuminationSource;

    /**
     * 职业照射种类,字典bus_job_illumination_source
     */
    private String jobIlluminationType;
}
