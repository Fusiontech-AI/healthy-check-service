package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;

/**
 * 体检样本业务对象 tj_sample
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class TjSamplePageBo {


    /**
     * 样本编码
     */
    @NotBlank(message = "样本编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sampleCode;

    /**
     * 样本名称
     */
    @NotBlank(message = "样本名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sampleName;

    /**
     * 样本类别
     */
    @NotBlank(message = "样本类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sampleCategory;

    /**
     * 样本类型
     */
    @NotBlank(message = "样本类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sampleType;

    /**
     * 样本状态（0正常 1停用）
     */
    private String status;

    /**
     * 组合项目编码
     */
    private String combinProjectCode;

    /**
     * 组合项目名称
     */
    private String combinProjectName;
}
