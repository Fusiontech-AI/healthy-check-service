package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjZdjywh;

/**
 * 诊断建议主业务对象 tj_zdjywh
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjZdjywh.class, reverseConvertGenerate = false)
public class TjZdjywhBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 主要诊断
     */
    @NotBlank(message = "主要诊断不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zyzd;

    /**
     * 建议名称
     */
    @NotBlank(message = "建议名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jymc;

    /**
     * 诊断描述
     */
    private String zdms;

    /**
     * 是否疾病（0-是，1-否）
     */
    @NotBlank(message = "是否疾病（0-是，1-否）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sfjb;

    /**
     * 常见疾病（0-是，1-否）
     */
    private String cjjb;

    /**
     * 拼音简码
     */
    private String pyjm;

    /**
     * 科普说明
     */
    private String kpsm;

    /**
     * 职业病建议
     */
    private String zybjy;

    /**
     * ICD-10疾病编码
     */
    private String icdCode;

    /**
     * 看诊科室推荐编码
     */
    private String kzkstjCode;

    /**
     * 看诊科室推荐名称
     */
    private String kzkstjName;

    /**
     * 重要程度(1正常2一般3重要4非常重要)
     */
    private String importance;

    /**
     * 程度排序
     */
    private Long degreeSort;

    /**
     * 体检科室id
     */
    private Long ksId;

}
