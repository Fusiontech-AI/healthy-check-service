package org.fxkc.peis.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@AutoMapper(target = TjHazardFactorsCodeBo.class)
public class TjOtherCompulsoryInspectionBo {

    /**
     * 组合项目id
     */
    @NotNull(message = "组合项目不能为空")
    private Long combinProjectId;

    /**
     * 危害因素编码
     */
    @NotEmpty(message = "危害因素不能为空")
    private List<String> codeList;

    /**
     * 在岗状态
     */
    @NotBlank(message = "在岗状态不能为空")
    private String dutyStatus;

    /**
     * 已选组合项目id
     */
    @NotNull(message = "已选组合项目id不能为空")
    private List<Long> combinProjectIdList;

    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类sys_dict_type(bus_job_illumination_source)
     */
    private String shineType;

    @JsonInclude
    private List<String> itemList;
}
