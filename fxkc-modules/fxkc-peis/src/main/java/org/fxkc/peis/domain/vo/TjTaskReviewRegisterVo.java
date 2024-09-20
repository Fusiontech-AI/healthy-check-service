package org.fxkc.peis.domain.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjRegister;

import java.util.Date;

@Data
@AutoMapper(target = TjRegister.class)
@Accessors(chain = true)
public class TjTaskReviewRegisterVo {
    /**
     * 主键id
     */
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
     * 证件号
     */
    private String credentialNumber;

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
     * 婚姻状况（0：未婚，1：已婚，2：未知）,见字典bus_personnel_marriage_status
     */
    private String marriageStatus;

    /**
     * 电话
     */
    private String phone;

    /**
     * 业务类别（1：个检，2：团检）见字典bus_category
     */
    private String businessCategory;

    /**
     * 体检状态（0：预约，1：登记，2：科室分检，3：分检完成，4：待总检，5：已终检）见字典bus_healthy_check_status
     */
    private String healthyCheckStatus;

    /**
     * 单位分组id
     */
    private Long teamGroupId;

    /**
     * 单位分组id
     */
    @Translation(type = TransConstant.TEAM_GROUP_ID_TO_NAME, mapper = "teamGroupId")
    private String groupName;

    /**
     * 总检完成标志(0待总检 1 已总检)
     */
    private String insMark;

    /**
     * 体检预约时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
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
     * 单位部门id
     */
    private Long teamDeptId;

    /**
     * 单位部门名称
     */
    private String deptName;

    /**
     * 在岗状态
     */
    private String dutyStatus;
}
