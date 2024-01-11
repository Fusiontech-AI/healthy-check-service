package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjTeamDept;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 单位部门信息视图对象 tj_team_dept
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamDept.class)
public class TjTeamDeptVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 单位id
     */
    @ExcelProperty(value = "单位id")
    private Long teamId;

    /**
     * 部门编号
     */
    @ExcelProperty(value = "部门编号")
    private String deptNo;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 部门负责人
     */
    @ExcelProperty(value = "部门负责人")
    private String deptManager;

    /**
     * 上级单位
     */
    private String teamName;
}
