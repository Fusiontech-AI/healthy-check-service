package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

/**
 * 体检报告模板对象 tj_template_info
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_template_info")
public class TjTemplateInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 体检类型
     */
    private String physicalType;

    /**
     * 报告类型
     */
    private String reportType;

    /**
     * 描述
     */
    private String describe;

    /**
     * 模板路径
     */
    private String templatePath;

    /**
     * 删除标志（0-有效,1-删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 模板数据
     */
    private String templateData;

    /**
     * 扩展类型
     */
    private String extendType;


}
