package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TjHazardFactorsCodeBo {

    /**
     * 危害因素编码集合
     */
    @NotEmpty(message = "危害因素不能为空")
    private List<String> codeList;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    @NotEmpty(message = "在岗状态不能为空")
    private String dutyStatus;


    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类sys_dict_type(bus_job_illumination_source)
     */
    private String shineType;
}
