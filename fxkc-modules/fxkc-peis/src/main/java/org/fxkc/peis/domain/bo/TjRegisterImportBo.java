package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TjRegisterImportBo {

    /**
     * 任务id
     */
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    /**
     * 体检类型sys_dict_type(bus_physical_type)
     */
    @NotBlank(message = "体检类型不能为空")
    private String physicalType;

    /**
     * 人员信息
     */
    @NotEmpty(message = "人员信息不能为空")
    private List<TjRegisterImportDetailBo> registerList;
}
