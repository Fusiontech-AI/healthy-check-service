package org.fxkc.peis.domain.vo.template;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 体检报告维护视图对象 tj_template
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
public class TjTemplateVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 模板ID
     */
    private Long templateInfoId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板名称
     */
    private String templateCode;

    /**
     * 体检类型
     */
    private String physicalType;

    /**
     * 报告类型
     */
    private String reportType;

    /**
     * 模板路径
     */
    private String templatePath;

    /**
     * 扩展数据
     */
    private String extendType;

    /**
     * 扩展详细数据
     */
    private List<TjTemplateExtendVo> extendList;
}
