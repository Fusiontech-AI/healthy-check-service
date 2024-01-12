package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjSampleInfo;

/**
 * 体检样本配置信息业务对象 tj_sample_info
 *
 * @author JunBaiChen
 * @date 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjSampleInfo.class, reverseConvertGenerate = false)
public class TjSampleInfoBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 样本主键id
     */
    @NotNull(message = "样本主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sampleId;

    /**
     * 组合项目主键id
     */
    @NotNull(message = "组合项目主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long combinProjectId;


}
