package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 查询历史本科小结列表查询请求对象 TjRegProjectHistoryListBo
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Data
public class TjRegProjectHistoryListBo {

    /**
     * 体检登记id
     */
    @NotNull(message = "体检登记id不能为空!")
    private Long regId;

    /**
     * 组合项目id
     */
    @NotNull(message = "组合项目id不能为空!")
    private Long combinationProjectId;

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    @NotBlank(message = "证件类型不能为空")
    private String credentialType;

    /**
     * 证件号
     */
    @NotBlank(message = "证件号不能为空")
    private String credentialNumber;
}
