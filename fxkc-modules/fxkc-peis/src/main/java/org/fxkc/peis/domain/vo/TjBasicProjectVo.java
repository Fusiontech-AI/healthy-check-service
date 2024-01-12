package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjBasicProject;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检基础项目视图对象 tj_basic_project
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjBasicProject.class)
public class TjBasicProjectVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 基础项目编码
     */
    @ExcelProperty(value = "基础项目编码")
    private String basicProjectCode;

    /**
     * 基础项目名称
     */
    @ExcelProperty(value = "基础项目名称")
    private String basicProjectName;

    /**
     * 基础项目简称
     */
    @ExcelProperty(value = "基础项目简称")
    private String basicSimpleName;

    /**
     * 所属科室id
     */
    @ExcelProperty(value = "所属科室id")
    private Long ksId;

    /**
     * 计量单位
     */
    @ExcelProperty(value = "计量单位")
    private String unit;

    /**
     * 结果类型 0数字 1文本
     */
    @ExcelProperty(value = "结果类型 0数字 1文本")
    private String resultType;

    /**
     * 结果获取方式 0手工 1导入
     */
    @ExcelProperty(value = "结果获取方式 0手工 1导入")
    private String resultGetWay;

    /**
     * 适用性别 0男 1女 2不限
     */
    @ExcelProperty(value = "适用性别 0男 1女 2不限")
    private String suitSex;

    /**
     * 项目默认值
     */
    @ExcelProperty(value = "项目默认值")
    private String defaultValue;

    /**
     * 基础项目显示序号
     */
    @ExcelProperty(value = "基础项目显示序号")
    private Long projectSort;

    /**
     * 进入小结 0是 1否
     */
    @ExcelProperty(value = "进入小结 0是 1否")
    private String enterSummary;

    /**
     * 进入报告 0是 1否
     */
    @ExcelProperty(value = "进入报告 0是 1否")
    private String enterReport;

    /**
     * 对照lis编码
     */
    @ExcelProperty(value = "对照lis编码")
    private String lisCode;

    /**
     * 对照pacs编码
     */
    @ExcelProperty(value = "对照pacs编码")
    private String pacsCode;

    /**
     * 对照his编码
     */
    @ExcelProperty(value = "对照his编码")
    private String hisCode;

    /**
     * 对照职业病编码
     */
    @ExcelProperty(value = "对照职业病编码")
    private String zybCode;

    /**
     * 基础项目状态（0正常 1停用）
     */
    @ExcelProperty(value = "基础项目状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;


}
