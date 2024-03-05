package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjRegBasicProject;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 体检登记基础项目关联视图对象 tj_reg_basic_project
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegBasicProject.class)
public class TjRegBasicProjectVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检登记项目记录主键id
     */
    @ExcelProperty(value = "体检登记项目记录主键id")
    private Long regItemId;

    /**
     * 体检登记id
     */
    @ExcelProperty(value = "体检登记id")
    private Long regId;

    /**
     * 组合项目id
     */
    @ExcelProperty(value = "组合项目id")
    private Long combinationProjectId;

    /**
     * 基础项目id
     */
    @ExcelProperty(value = "基础项目id")
    private Long basicProjectId;

    /**
     * 基础项目名称
     */
    @Translation(type = TransConstant.BASIC_ID_TO_NAME,mapper = "basicProjectId")
    private String basicProjectName;

    /**
     * 检查部位
     */
    @ExcelProperty(value = "检查部位")
    private String checkPart;

    /**
     * 是否阳性 0是 1否
     */
    @ExcelProperty(value = "是否阳性 0是 1否")
    private String isPositive;

    /**
     * 是否正常（0-正常 1-不正常 2-偏高 3-偏低4-高于极限 5低于极限 ）字典bus_check_result
     */
    @ExcelProperty(value = "是否正常", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-正常,1=-不正常,2=-偏高,3=-偏低4-高于极限,5=低于极限")
    private String isAbnormal;

    /**
     * 参考值
     */
    @ExcelProperty(value = "参考值")
    private String reference;

    /**
     * 检查结果
     */
    @ExcelProperty(value = "检查结果")
    private String checkResult;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 数值上限
     */
    @ExcelProperty(value = "数值上限")
    private String upperLimit;

    /**
     * 数值下限
     */
    @ExcelProperty(value = "数值下限")
    private String lowLimit;


    /**
     * pacs图片标识
     */
    private String checkResultImg;

    /**
     * 常见结果id,多个之间逗号分割
     */
    private String commonResultIds;

    /**
     * 是否下拉选择框(有常见结果选项就是下拉框，无常见结果为输入框) 0:输入款，1：下拉框
     */
    @ExcelProperty(value = "是否下拉选择框(有常见结果选项就是下拉框，无常见结果为输入框) 0:输入款，1：下拉框")
    private String  selectFlag;

    /**
     * 常见结果选项list信息
     */
    private List<TjBasicCommonResultVo> basicCommonResultVos;

}
