package org.fxkc.peis.domain.bo;

import lombok.Data;

@Data
public class TjStatisticsCommonBo {

    /**
     * 体检号
     */
    private String healthyCheckCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（0：男，1：女，2：未知）,见字典sys_user_sex
     */
    private String gender;

    /**
     * 开始体检日期(yyyy-MM-dd)
     */
    private String beginDate;

    /**
     * 结束体检日期(yyyy-MM-dd)
     */
    private String endDate;

    /**
     * 体检类型，见字典bus_physical_type
     */
    private String physicalType;

    /**
     * 档案号
     */
    private String recordCode;

    /**
     * 证件号
     */
    private String credentialNumber;

    /**
     * 单位id
     */
    private Long teamId;

    /**
     * 体检状态（0：预约，1：登记，2：科室分检，3：分检完成，4：待总检，5：已终检）见字典bus_healthy_check_status
     */
    private String healthyCheckStatus;
}
