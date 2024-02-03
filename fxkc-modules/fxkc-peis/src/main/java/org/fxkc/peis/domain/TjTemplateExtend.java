package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

/**
 * 体检报告扩展对象 tj_template_extend
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_template_extend")
public class TjTemplateExtend extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

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
    private String templateId;


}
