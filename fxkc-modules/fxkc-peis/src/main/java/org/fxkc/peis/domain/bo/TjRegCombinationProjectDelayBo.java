package org.fxkc.peis.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.common.core.validate.EditGroup;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-23 13:58
 **/
@Data
public class TjRegCombinationProjectDelayBo {

    /**
     * 主键id
     */
    @NotEmpty(message = "主键不能为空",groups = {EditGroup.class})
    private List<Long> ids;

    /**
     * 延期理由
     */
    private String delayReason;

    /**
     * 延期时间
     */
    @NotNull(message = "延期时间不能为空",groups = {EditGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date delayTime;
}
