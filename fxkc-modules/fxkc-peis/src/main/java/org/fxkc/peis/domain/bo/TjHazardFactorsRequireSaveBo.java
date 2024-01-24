package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class TjHazardFactorsRequireSaveBo {

    /**
     * 主键id(编辑传入)
     */
    private String id;

    /**
     * 危害因素名称
     */
    @NotBlank(message = "危害因素名称不能为空")
    private String hazardFactorsName;

    /**
     * 危害因素编码
     */
    @NotBlank(message = "危害因素编码不能为空")
    private String hazardFactorsCode;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

    /**
     * 启用状态(0:启用1:不启用)
     */
    private String enableStatus;

    /**
     * 必检项目或目标职业病或职业禁忌症
     */
    private List<String> itemList;

    /**
     * 关联类型(1:必检项目2:目标类型3:职业禁忌症4:症状询问重点5:体格检查6:检查周期7:评价标准)
     */
    @NotBlank(message = "关联类型不能为空")
    private String associationType;

    /**
     * 症状询问重点
     */
    private String symptomFocus;

    /**
     * 体格检查
     */
    private String physicalExamination;

    /**
     * 检查周期
     */
    private String inspectionCycle;

    /**
     * 评价标准
     */
    private String evaluationCriterion;

    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类sys_dict_type(bus_job_illumination_source)
     */
    private String shineType;

    /**
     * 放射小类编码
     */
    private String sortCode;

}
