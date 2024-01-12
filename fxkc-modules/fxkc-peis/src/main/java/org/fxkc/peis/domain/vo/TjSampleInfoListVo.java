package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检样本配置信息视图对象 TjSampleInfoListVo
 *
 * @author JunBaiChen
 * @date 2024-01-11
 */
@Data
public class TjSampleInfoListVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 组合项目主键id
     */
    @NotNull(message = "组合项目主键id不能为空")
    private Long combinProjectId;

    /**
     * 组合项目编码
     */
    @ExcelProperty(value = "组合项目编码")
    private String combinProjectCode;

    /**
     * 组合项目名称
     */
    @ExcelProperty(value = "组合项目名称")
    private String combinProjectName;

}
