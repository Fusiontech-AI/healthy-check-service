package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.fxkc.peis.domain.TjCombinationProject;
import org.fxkc.peis.domain.bo.TjCombinationProjectInfoItemBo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 体检组合项目视图对象 TjCombinationProjectListVo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjCombinationProject.class)
@Accessors(chain = true)
public class TjCombinationProjectListVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

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

    /**
     * 组合项目简称
     */
    @ExcelProperty(value = "组合项目简称")
    private String combinSimpleName;

    /**
     * 拼音简码
     */
    @ExcelProperty(value = "拼音简码")
    private String pySimpleCode;

    /**
     * 检查类型0检查项目 1化验项目 2功能项目
     */
    @ExcelProperty(value = "检查类型0检查项目 1化验项目 2功能项目")
    private String checkType;

    /**
     * 科室主键id
     */
    @ExcelProperty(value = "科室主键id")
    private Long ksId;

    /**
     * 样本类型 0类型1
     */
    @ExcelProperty(value = "样本类型 0类型1")
    private String sampleType;

    /**
     * 是否需要标本
     */
    @ExcelProperty(value = "是否需要标本")
    private String specimenNeedFlag;

    /**
     * 标本类型 0类型1
     */
    @ExcelProperty(value = "标本类型 0类型1")
    private String specimenType;

    /**
     * 标准价格
     */
    @ExcelProperty(value = "标准价格")
    private BigDecimal standardAmount;

    /**
     * 折扣率
     */
    @ExcelProperty(value = "折扣率")
    private BigDecimal discount;

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
     * 组合项目排序
     */
    @ExcelProperty(value = "组合项目排序")
    private Long projectSort;

    /**
     * 适用性别 0男 1女 2不限
     */
    @ExcelProperty(value = "适用性别 0男 1女 2不限")
    private String suitSex;

    /**
     * 项目类型 0普通
     */
    @ExcelProperty(value = "项目类型 0普通")
    private String projectType;

    /**
     * 财务类别 0是
     */
    @ExcelProperty(value = "财务类别 0是")
    private String financialType;

    /**
     * 检查地址
     */
    @ExcelProperty(value = "检查地址")
    private String checkAddress;

    /**
     * 是否进入隐私报告 0是
     */
    private String privacyFlag;


    /**
     * 是否进入指引单 0是
     */
    private String guideFlag;


    /**
     * 是否进入工作站 0是
     */
    private String workerFlag;

    /**
     * 是否外送 0是
     */
    @ExcelProperty(value = "是否外送 0是")
    private String outFlag;

    /**
     * 外送回调地址
     */
    @ExcelProperty(value = "外送回调地址")
    private String outAddress;

    /**
     * 当前预约上限
     */
    @ExcelProperty(value = "当前预约上限")
    private Long useLimit;

    /**
     * 指引单提示信息
     */
    @ExcelProperty(value = "指引单提示信息")
    private String guideNotice;

    /**
     * 项目临床意义
     */
    @ExcelProperty(value = "项目临床意义")
    private String projectClinicalMean;

    /**
     * 项目描述
     */
    @ExcelProperty(value = "项目描述")
    private String projectDescribe;

    /**
     * 组合项目状态（0正常 1停用）
     */
    @ExcelProperty(value = "组合项目状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;


    /**
     * 组合项目下已选基础项目信息
     */
    private List<TjCombinationProjectInfoItemBo> infoItemBos;

    /**
     * 是否必选
     */
    private Boolean required;

}
