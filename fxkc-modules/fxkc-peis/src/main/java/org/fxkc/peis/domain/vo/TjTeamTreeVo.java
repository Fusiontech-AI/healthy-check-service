package org.fxkc.peis.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TjTeamTreeVo {

    @Schema(description = "单位id")
    private Long id;

    @Schema(description = "单位名称")
    private String teamName;

    @Schema(description = "下级单位")
    private List<TjTeamTreeVo> children;

}
