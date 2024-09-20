package org.fxkc.peis.domain.vo;

import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.RuleTjInfo;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 体检项目规则项视图对象 rule_tj_info
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = RuleTjInfo.class)
public class RuleTjInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 规则集id
     */
    @ExcelProperty(value = "规则集id")
    private Long rulesetId;

    /**
     * 规则名称
     */
    @ExcelProperty(value = "规则名称")
    private String name;

    /**
     * 返回值
     */
    @ExcelProperty(value = "返回值")
    private String returnValues;

    /**
     * 逻辑符号 AND/XOR
     */
    @ExcelProperty(value = "逻辑符号 AND/XOR")
    private String logicType;

    /**
     * 诊断建议主键id
     */
    @ExcelProperty(value = "诊断建议主键id")
    private Long zdjyId;

    /**
     * 建议名称
     */
    @ExcelProperty(value = "建议名称")
    @Translation(type = TransConstant.ZDJY_ID_TO_NAME,mapper = "zdjyId")
    private String jymc;

    /**
     * 重要程度（特别重要 重要）
     */
    @ExcelProperty(value = "重要程度", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "特=别重要,重=要")
    private String importance;

    /**
     * 结果类型（正常 异常）
     */
    @ExcelProperty(value = "结果类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "正=常,异=常")
    private String resultType;

    /**
     * 推荐就诊科室编码
     */
    @ExcelProperty(value = "推荐就诊科室编码")
    private String recommendDeptCode;

    /**
     * 是否危机
     */
    @ExcelProperty(value = "是否危机")
    private String crisisType;

    /**
     * 危机值措施
     */
    @ExcelProperty(value = "危机值措施")
    private String crisisDeal;

    /**
     * 是否隐私
     */
    @ExcelProperty(value = "是否隐私")
    private String privacyType;

    /**
     * 是否进入小结
     */
    @ExcelProperty(value = "是否进入小结")
    private String summaryType;

    /**
     * 是否进入诊断
     */
    @ExcelProperty(value = "是否进入诊断")
    private String diagnosisType;

    /**
     * 是否随访
     */
    @ExcelProperty(value = "是否随访")
    private String followType;

    /**
     * 匹配关键词
     */
    @ExcelProperty(value = "匹配关键词")
    private String keyWords;

    /**
     * 优先级
     */
    @ExcelProperty(value = "优先级")
    private Integer priority;

    /**
     * 疾病级别
     */
    @ExcelProperty(value = "疾病级别")
    private String icdLevel;

    /**
     * 疾病分类
     */
    @ExcelProperty(value = "疾病分类")
    private String icdClassification;

    /**
     * 统计分类
     */
    @ExcelProperty(value = "统计分类")
    private String statisticsClassification;


    /**
     * 规则项下条件记录信息
     */
    private List<RuleTjConditionVo> ruleTjConditionVos;

}
