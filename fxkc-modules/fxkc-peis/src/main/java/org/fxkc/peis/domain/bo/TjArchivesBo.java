package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjArchives;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 体检档案业务对象 tj_archives
 *
 * @author JunBaiChen
 * @date 2024-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjArchives.class, reverseConvertGenerate = false)
public class TjArchivesBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检档案号
     */
    @NotBlank(message = "体检档案号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String archivesNo;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    @NotBlank(message = "证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type不能为空", groups = { AddGroup.class, EditGroup.class })
    private String credentialType;

    /**
     * 证件号
     */
    @NotBlank(message = "证件号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String credentialNumber;


}
