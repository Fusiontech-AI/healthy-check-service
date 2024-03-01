package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 诊断建议主对象 tj_zdjywh
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_zdjywh")
public class TjZdjywh extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 主要诊断
     */
    private String zyzd;

    /**
     * 建议名称
     */
    private String jymc;

    /**
     * 诊断描述
     */
    private String zdms;

    /**
     * 是否疾病（0-是，1-否）
     */
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
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
