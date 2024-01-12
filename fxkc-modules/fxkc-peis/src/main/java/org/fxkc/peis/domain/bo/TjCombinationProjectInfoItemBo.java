package org.fxkc.peis.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 体检组合项目详细信息请求对象 TjCombinationProjectInfoItemBo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
public class TjCombinationProjectInfoItemBo {

    /**
     * 基础项目主键id
     */
    @NotNull(message = "基础项目主键id不能为空")
    private Long basicProjectId;

    /**
     * 组合项目主键id
     */
    @NotNull(message = "组合项目主键id不能为空")
    private Long combinProjectId;

    /**
     * 基础项目编码
     */
    @ExcelProperty(value = "基础项目编码")
    private String basicProjectCode;

    /**
     * 基础项目名称
     */
    private String basicProjectName;

}
