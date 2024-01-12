package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjSampleInfo;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检样本配置信息视图对象 tj_sample_info
 *
 * @author JunBaiChen
 * @date 2024-01-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjSampleInfo.class)
public class TjSampleInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 样本主键id
     */
    @ExcelProperty(value = "样本主键id")
    private Long sampleId;

    /**
     * 组合项目主键id
     */
    @ExcelProperty(value = "组合项目主键id")
    private Long combinProjectId;


}
