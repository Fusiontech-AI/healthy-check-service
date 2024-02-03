package org.fxkc.peis.domain.bo.template;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ReportPrintBO {

    @NotBlank(message = "体检记录ID集合")
    private List<Long> regIdList;

    /**
     * 是否打印，0 下载 ，1.打印 默认打印
     */
    private Boolean isPrint = true;

    /**
     *  是否预览，0 非 ，1.是 默认预览
     */
    private Boolean isPreview = true;
}
