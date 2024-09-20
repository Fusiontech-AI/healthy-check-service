package org.fxkc.peis.domain.bo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;

import java.util.Date;

/**
 * 体检人员综合项目信息业务对象 TjRegCombinAddBo
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
public class TjRegReplaceInfoBo {

    /**
     * 体检人员id
     */
    @NotNull(message = "体检人员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long registerId;

    /**
     * 被替检人姓名
     */
    @NotBlank(message = "被替检人姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String replaceName;

    /**
     * 被替检人证件类型,见字典bus_credential_type
     */
    @NotBlank(message = "被替检人证件类型,见字典bus_credential_type不能为空", groups = { AddGroup.class, EditGroup.class })
    private String replaceCredentialType;

    /**
     * 被替检人证件号码
     */
    @NotBlank(message = "被替检人证件号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String replaceCredentialNumber;

    /**
     * 被替检人生日
     */
    @NotNull(message = "被替检人生日不能为空", groups = { AddGroup.class, EditGroup.class })
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date replaceBirthday;

    /**
     * 被替检人性别
     */
    @NotBlank(message = "被替检人性别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String replaceGender;

    /**
     * 被替检人年龄
     */
    @NotNull(message = "被替检人年龄不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer replaceAge;

    /**
     * 被替检人电话
     */
    private String replacePhone;
}
