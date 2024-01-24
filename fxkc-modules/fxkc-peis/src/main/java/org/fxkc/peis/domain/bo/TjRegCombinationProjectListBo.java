package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-23 14:22
 **/
@Data
public class TjRegCombinationProjectListBo {

    /**
     * 检查状态（0：未检查，1：已检查，2：弃捡，3：未保存，4：延期）见字典bus_check_status
     */
    @NotBlank(message = "检查状态（0：未检查，1：已检查，2：弃捡，3：未保存，4：延期）见字典bus_check_status不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkStatus;

    /**
     * 体检人员id
     */
    @NotBlank(message = "体检人员id不能为空")
    private Long registerId;
}
