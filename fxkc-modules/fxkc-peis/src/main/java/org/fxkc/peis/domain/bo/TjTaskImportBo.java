package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    @NotBlank(message = "任务id不能为空")
    private Long taskId;

    /**
     * 上传文件
     */
    @NotNull(message = "上传模板不能为空")
    private MultipartFile file;
}
