package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjZdjywhTjks;

/**
 * 诊断建议和科室关联业务对象 tj_zdjywh_tjks
 *
 * @author JunBaiChen
 * @date 2024-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjZdjywhTjks.class, reverseConvertGenerate = false)
public class TjZdjywhTjksBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检科室id
     */
    @NotNull(message = "体检科室id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ksId;

    /**
     * 诊断建议id
     */
    @NotNull(message = "诊断建议id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long zdjyId;


}
