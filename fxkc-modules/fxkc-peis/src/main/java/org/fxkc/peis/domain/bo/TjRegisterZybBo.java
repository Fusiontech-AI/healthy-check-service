package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRegisterZyb;

import java.util.Date;

/**
 * 体检登记职业病关联业务对象 tj_register_zyb
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjRegisterZyb.class, reverseConvertGenerate = false)
public class TjRegisterZybBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检登记id
     */
    @NotNull(message = "体检登记id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regId;

    /**
     * 在岗状态,字典bus_duty_status
     */
    @NotBlank(message = "在岗状态,字典bus_duty_status不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dutyStatus;

    /**
     * 照射源,字典bus_shine_source
     */
    @NotBlank(message = "照射源,字典bus_shine_source不能为空", groups = { AddGroup.class, EditGroup.class })
    private String illuminationSource;

    /**
     * 职业照射种类,字典bus_job_illumination_source
     */
    @NotBlank(message = "职业照射种类,字典bus_job_illumination_source不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jobIlluminationType;

    /**
     * 个案卡类别,字典bus_case_card_type
     */
    @NotBlank(message = "个案卡类别,字典bus_case_card_type不能为空", groups = { AddGroup.class, EditGroup.class })
    private String caseCardType;

    /**
     * 工种编码,字典bus_job_code
     */
    @NotBlank(message = "工种编码,字典bus_job_code不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jobCode;

    /**
     * 总工龄(年)
     */
    @NotNull(message = "总工龄(年)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long seniorityYear;

    /**
     * 总工龄(月)
     */
    @NotNull(message = "总工龄(月)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long seniorityMonth;

    /**
     * 接害工龄(年)
     */
    @NotNull(message = "接害工龄(年)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long contactSeniorityYear;

    /**
     * 接害工龄(月)
     */
    @NotNull(message = "接害工龄(月)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long contactSeniorityMonth;

    /**
     * 检查医生
     */
    private Long checkDoctor;

    /**
     * 检查日期
     */
    private Date checkDate;

    /**
     * 职业病检查结果
     */
    private String zybResult;

    /**
     * 职业病处理意见
     */
    private String zybAdvice;

    /**
     * 定性结论
     */
    private String dxResult;

    /**
     * 其他工种名称
     */
    private String otherJobName;

}
