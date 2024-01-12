package org.fxkc.peis.domain.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.vo.TjSampleInfoListVo;

import java.util.List;

/**
 * 体检样本业务对象 tj_sample
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class TjSampleUpdateBo {

    /**
     * 主键id
     */
    @NotNull(message = "样本主键id不能为空", groups = { EditGroup.class })
    private Long id;


    /**
     * 待修改的组合项目信息
     */
    @Valid
    private List<TjSampleInfoListVo> sampleInfoListVos;



}
