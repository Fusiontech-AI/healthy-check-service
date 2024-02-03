package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.common.core.validate.AddGroup;

/**
 * 报告领取方式信息登记 TjRegReceiveWayBo
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
public class TjRegReceiveWayBo {

    /**
     * 体检人员id
     */
    @NotNull(message = "体检人员id不能为空", groups = { AddGroup.class})
    private Long registerId;

    /**
     * 报告领取方式0邮寄 1自取,见字典bus_receive_way
     */
    @NotBlank(message = "报告领取方式0邮寄 1自取不能为空", groups = { AddGroup.class})
    private String receiveWay;

    /**
     * 收件人
     */
    @NotBlank(message = "收件人不能为空", groups = { AddGroup.class})
    private String recipient;

    /**
     * 收件电话
     */
    @NotBlank(message = "收件电话不能为空", groups = { AddGroup.class})
    private String receiptPhone;

    /**
     * 收件地址
     */
    @NotBlank(message = "收件地址不能为空", groups = { AddGroup.class})
    private String postAddress;

}
