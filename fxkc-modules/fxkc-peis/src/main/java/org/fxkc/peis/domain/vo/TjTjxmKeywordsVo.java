package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjTjxmKeywords;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检项目关键字库视图对象 tj_tjxm_keywords
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTjxmKeywords.class)
public class TjTjxmKeywordsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private String id;

    /**
     * 关键词编码
     */
    @ExcelProperty(value = "关键词编码")
    private String code;

    /**
     * 关键词名称
     */
    @ExcelProperty(value = "关键词名称")
    private String name;

    /**
     * 备注信息
     */
    @ExcelProperty(value = "备注信息")
    private String remark;

    /**
     * 优先级
     */
    @ExcelProperty(value = "优先级")
    private Integer priority;

    /**
     * 关键词类型ID
     */
    @ExcelProperty(value = "关键词类型ID")
    private String keyTypeId;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 诊断建议id
     */
    private Long zdjyId;

}
