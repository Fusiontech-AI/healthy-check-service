package org.fxkc.peis.domain.bo.template;

import lombok.Data;


/**
 * 模板查询条件
 * @since 2022-07-20
 */
@Data
public class TjTemplateQueryBO{

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
     * 租户ID
     */
    private String tenantId;
}
