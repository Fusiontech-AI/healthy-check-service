package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjCombinationProjectInfo;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检组合项目详细信息视图对象 tj_combination_project_info
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjCombinationProjectInfo.class)
public class TjCombinationProjectInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 基础项目主键id
     */
    @ExcelProperty(value = "基础项目主键id")
    private Long basicProjectId;

    /**
     * 组合项目主键id
     */
    @ExcelProperty(value = "组合项目主键id")
    private Long combinProjectId;


}
