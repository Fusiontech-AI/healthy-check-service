package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;

import java.util.Date;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-23 14:14
 **/
@Data
public class TjRegCombinationProjectListVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 组合项目id
     */
    private Long combinationProjectId;


    /**
     * 组合项目编码
     */
    private String combinProjectCode;

    /**
     * 组合项目名称
     */
    private String combinProjectName;

    /**
     * 科室名称
     */
    private String sectionName;

    /**
     * 项目类型（0：套餐项目，1：加项项目）见字典bus_combination_project_type
     */
    private String projectType;

    /**
     * 项目属性（0：选检项目，1：必检项目）见字典bus_project_required_type
     */
    private String projectRequiredType;

    /**
     * 延期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date delayTime;

    /**
     * 延期理由
     */
    private String delayReason;
}
