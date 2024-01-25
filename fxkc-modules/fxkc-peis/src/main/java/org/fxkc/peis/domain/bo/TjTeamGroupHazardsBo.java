package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamGroupHazards;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 单位分组危害因素业务对象 tj_team_group_hazards
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamGroupHazards.class, reverseConvertGenerate = false)
public class TjTeamGroupHazardsBo extends BaseEntity {


    /**
     * 危害因素编码
     */
    @NotBlank(message = "危害因素编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactorsCode;

    /**
     * 危害因素名称
     */
    @NotBlank(message = "危害因素名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactorsName;

    /**
     * 其他危害因素名称
     */
    @NotBlank(message = "其他危害因素名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactorsOtherName;


}
