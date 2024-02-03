package org.fxkc.peis.domain.vo.template;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检报告维护视图对象 tj_template
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
public class TjTemplateExtendVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 扩展类型
     */
    private String extendType;

    /**
     * 显示类型("text":文本,"file":"文件")
     */
    private String showType;

    /**
     * 内容
     */
    private String content;


    /**
     * 模板ID
     */
    private Long templateId;
}
