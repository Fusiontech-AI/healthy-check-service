package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 团检分组信息业务对象 tj_team_group
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
public class TjTeamGroupProjectBo {

    /**
     * 主键id
     */
    @NotBlank(message = "体检类型不能为空")
    private String physicalType;

    /**
     * 项目分组集合
     */
    @NotEmpty(message = "项目分组集合不能为空")
    private List<TjTeamGroupUpdateBo> list;

}
