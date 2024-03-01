package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjTjxmKeywordsRelation;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检项目关键字和诊断建议关系视图对象 tj_tjxm_keywords_relation
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTjxmKeywordsRelation.class)
public class TjTjxmKeywordsRelationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private String id;

    /**
     * 关键词id
     */
    @ExcelProperty(value = "关键词id")
    private String keywordId;

    /**
     * 诊断建议id
     */
    @ExcelProperty(value = "诊断建议id")
    private Long zdjyId;


}
