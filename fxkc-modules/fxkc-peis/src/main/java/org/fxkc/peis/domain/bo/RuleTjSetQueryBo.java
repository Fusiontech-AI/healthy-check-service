package org.fxkc.peis.domain.bo;

import lombok.Builder;
import lombok.Data;

/**
 * 规则集记录信息查询请求Bo
 */
@Data
@Builder
public class RuleTjSetQueryBo {

    /**
     * 基础项目id
     */
    private Long basicProjectId;

    /**
     * 是否职业病(0：职业，1：健康  2老年人)
     */
    private String occupationalType;

    /**
     * 规则类型 1诊断建议 2危急值 3高危异常
     */
    private String ruleType;

}
