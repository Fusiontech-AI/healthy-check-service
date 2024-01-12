package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjSample;

/**
 * 体检样本业务对象 tj_sample
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjSample.class, reverseConvertGenerate = false)
public class TjSampleBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

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
     * 条码类型
     */
    @NotBlank(message = "条码类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String barCodeType;

    /**
     * 打印顺序
     */
    private Long printSort;

    /**
     * 打印份数
     */
    private Long printNumber;

    /**
     * 是否打印 0是 1否
     */
    private String printFlag;

    /**
     * 打印申请单 0打印 1不打印
     */
    private String printApplyFlag;

    /**
     * 申请单份数
     */
    private Long printApplyNumber;

    /**
     * 备注
     */
    private String remark;


}
