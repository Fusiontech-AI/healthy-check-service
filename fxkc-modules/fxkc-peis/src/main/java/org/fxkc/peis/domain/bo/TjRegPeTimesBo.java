package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 查询体检次数业务对象 TjRegPeTimesBo
 *
 * @author JunBaiChen
 * @date 2024-02-21
 */
@Data
public class TjRegPeTimesBo {

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    @NotBlank(message = "证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type不能为空")
    private String credentialType;

    /**
     * 证件号
     */
    @NotBlank(message = "证件号不能为空")
    private String credentialNumber;

    public TjRegPeTimesBo(String credentialType, String credentialNumber) {
        this.credentialType = credentialType;
        this.credentialNumber = credentialNumber;
    }

    public TjRegPeTimesBo() {
    }
}
