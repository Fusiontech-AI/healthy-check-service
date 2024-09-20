package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TjProjectPackageBo {

    /**
     * 套餐id
     */
    @NotNull(message = "套餐id不能为空")
    private Long packageId;

    /**
     * 职业病必检基础项目id(职业病传)
     */
    private List<String> basicProjectId;
}
