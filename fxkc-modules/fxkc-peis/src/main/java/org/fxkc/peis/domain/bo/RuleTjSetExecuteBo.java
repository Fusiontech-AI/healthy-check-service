package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 规则集记录信息执行请求Bo
 */
@Data
public class RuleTjSetExecuteBo {

    /**
     * 体检人员id
     */
    @NotNull(message = "体检人员id不能为空")
    private Long registerId;

    /**
     * 基础项目主键id
     */
    private Long basicProjectId;

    /**
     * 基础项目名称
     */
    private String basicProjectName;

    /**
     * 组合项目主键id
     */
    private Long combinProjectId;

    /**
     * 是否职业病(0：职业，1：健康  2老年人)
     */
    private String occupationalType;


    private RuleTjSetExecuteParamBo paramDto;

}
