package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjTemplateInfo;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检报告模板视图对象 tj_template_info
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTemplateInfo.class)
public class TjTemplateInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 模板名称
     */
    @ExcelProperty(value = "模板名称")
    private String templateName;

    /**
     * 体检类型
     */
    @ExcelProperty(value = "体检类型")
    private String physicalType;

    /**
     * 报告类型
     */
    @ExcelProperty(value = "报告类型")
    private String reportType;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String describe;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String createUser;

    /**
     * 模板路径
     */
    @ExcelProperty(value = "模板路径")
    private String templatePath;

    /**
     * 模板数据
     */
    @ExcelProperty(value = "模板数据")
    private String templateData;

    /**
     * 扩展类型
     */
    @ExcelProperty(value = "扩展类型")
    private String extendType;


}
