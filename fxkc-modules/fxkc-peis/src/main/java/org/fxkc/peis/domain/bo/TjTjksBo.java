package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTjks;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 体检科室业务对象 tj_tjks
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTjks.class, reverseConvertGenerate = false)
public class TjTjksBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 科室编码
     */
    @NotBlank(message = "科室编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksCode;

    /**
     * 科室名称
     */
    @NotBlank(message = "科室名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksName;

    /**
     * 科室简拼
     */
    @NotBlank(message = "科室简拼不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksSimplePy;

    /**
     * 是否打印条码 0是 1否
     */
    @NotBlank(message = "是否打印条码 0是 1否不能为空", groups = { AddGroup.class, EditGroup.class })
    private String printFlag;

    /**
     * 科室显示序号
     */
    @NotNull(message = "科室显示序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ksSort;

    /**
     * 科室状态（0正常 1停用）
     */
    @NotBlank(message = "科室状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;


}
