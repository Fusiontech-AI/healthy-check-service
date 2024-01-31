package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.mybatis.core.domain.BaseEntity;

/**
 * 体检人员批量换组业务对象
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TjTeamWaitSwitchGroupBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotEmpty(message = "主键id不能为空")
    private Long[] id;

    /**
     * 科室编码
     */
    @NotNull(message = "分组id不能为空")
    private Long groupId;

}
