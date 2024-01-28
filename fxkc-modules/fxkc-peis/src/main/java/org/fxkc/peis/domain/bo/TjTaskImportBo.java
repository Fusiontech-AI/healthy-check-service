package org.fxkc.peis.domain.bo;

import cn.hutool.core.date.DateUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class TjTaskImportBo {

    /**
     * 模板类型(JKTJ:健康ZYJKTJ:职业健康体检FSTJ:放射体检)
     */
    @NotBlank(message = "模板类型不能为空")
    private String templateType;

    /**
     * 任务id
     */
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    /**
     * 是否自动入组
     */
    @NotNull(message = "是否自动入组不能为空")
    private Boolean autoGroup;

    private String taskName;

    private String teamName;

    private Date time = DateUtil.date();
}
