package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TjTeamTreeBo {

    /**
     * 单位名称
     */
    private String teamName;

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;
}
