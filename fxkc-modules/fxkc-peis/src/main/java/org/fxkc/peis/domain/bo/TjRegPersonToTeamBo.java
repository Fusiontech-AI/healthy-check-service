package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 个转团业务对象 TjRegPersonToTeamBo
 *
 * @author JunBaiChen
 * @date 2024-02-21
 */
@Data
public class TjRegPersonToTeamBo {
    /**
     * 选中体检数据
     */
    @NotEmpty(message = "所选体检数据不能为空")
    private List<Long> regIds;

    /**
     * 单位id
     */
    @NotBlank(message = "所选单位id不能为空")
    private Long teamId;

    /**
     * 单位分组id
     */
    private Long teamGroupId;

    /**
     * 任务id
     */
    @NotBlank(message = "所选任务id不能为空")
    private Long taskId;
}
