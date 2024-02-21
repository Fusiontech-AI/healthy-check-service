package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检档案对象 tj_archives
 *
 * @author JunBaiChen
 * @date 2024-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_archives")
public class TjArchives extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检档案号
     */
    private String archivesNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    private String credentialType;

    /**
     * 证件号
     */
    private String credentialNumber;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
