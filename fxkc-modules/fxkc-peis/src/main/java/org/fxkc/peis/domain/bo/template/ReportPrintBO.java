package org.fxkc.peis.domain.bo.template;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ReportPrintBO {

    @NotBlank(message = "体检记录ID集合")
    private List<Long> regIdList;
}
