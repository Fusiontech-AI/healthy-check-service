package org.fxkc.peis.domain.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 体检项目诊断结果保存 TjRegSaveDiagnosisBo
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
public class TjRegSaveDiagnosisBo {

    /**
     * 体检登记项目记录主键id
     */
    @NotNull(message = "体检登记项目记录主键id不能为空")
    private Long regItemId;

    /**
     * 体检登记id
     */
    @NotNull(message = "体检登记id不能为空")
    private Long regId;

    /**
     * 检查医生
     */
    private Long checkDoctor;

    /**
     * 检查医生姓名
     */
    private String checkDoctorName;

    /**
     * 检查时间
     */
    private Date checkTime;

    /**
     * 检查状态（0：未检查，1：已检查，2：弃捡，3：未保存，4：延期）见字典bus_check_status
     */
    private String checkStatus;

    /**
     * 登记基础项目列表信息
     */
    @Valid
    private List<TjRegBasicProjectSaveBo> regBasicProjects;

}
