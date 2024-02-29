package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 团转个业务对象 TjRegTeamToPersonBo
 *
 * @author JunBaiChen
 * @date 2024-02-21
 */
@Data
public class TjRegTeamToPersonBo {
    /**
     * 选中体检数据
     */
    @NotEmpty(message = "所选体检数据不能为空")
    private List<Long> regIds;

}
