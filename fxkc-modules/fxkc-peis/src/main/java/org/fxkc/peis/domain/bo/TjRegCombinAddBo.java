package org.fxkc.peis.domain.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;

import java.util.List;

/**
 * 体检人员综合项目信息业务对象 TjRegCombinAddBo
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
public class TjRegCombinAddBo {

    /**
     * 体检人员id
     */
    @NotNull(message = "体检人员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long registerId;

    @Valid
    private List<TjRegCombinItemBo> tjRegCombinItemBos;

}
