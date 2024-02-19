package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.peis.domain.TjRegBasicProject;

/**
 * 体检项目诊断结果保存基础bo TjRegBasicProjectSaveBo
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@AutoMapper(target = TjRegBasicProject.class, reverseConvertGenerate = false)
public class TjRegBasicProjectSaveBo {

    /**
     * 登记基础项目主键id
     */
    @NotNull(message = "主键id不能为空")
    private Long id;

    /**
     * 检查部位
     */
    private String checkPart;

    /**
     * 是否阳性 0是 1否
     */
    private String isPositive;

    /**
     * 是否正常（0-正常 1-不正常 2-偏高 3-偏低4-高于极限 5低于极限 ）字典bus_check_result
     */
    private String isAbnormal;

    /**
     * 参考值
     */
    private String reference;

    /**
     * 检查结果
     */
    private String checkResult;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数值上限
     */
    private String upperLimit;

    /**
     * 数值下限
     */
    private String lowLimit;


}
