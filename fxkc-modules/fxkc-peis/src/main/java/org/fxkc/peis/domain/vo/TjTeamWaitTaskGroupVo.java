package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检任务分组对象
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@Data
@ExcelIgnoreUnannotated
public class TjTeamWaitTaskGroupVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 任务分组名称
     */
    @ExcelProperty(value = "任务分组名称")
    private String name;

    /**
     * 上级任务ID
     */
    @ExcelProperty(value = "上级任务ID")
    private Long parentId;

}
