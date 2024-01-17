package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 体检样本业务对象 TjSampleBatchUpdateBo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class TjSampleBatchUpdateBo {

    /**
     * 主键id串
     */
    @NotNull(message = "主键id不能为空")
    private  List<Long> ids;


    /**
     * 样本类别
     */
    @NotBlank(message = "样本类别不能为空")
    private String sampleCategory;

}
