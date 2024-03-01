package org.fxkc.peis.domain.bo;

import lombok.Data;

/**
 * 规则项条件记录信息查询请求Bo
 */
@Data
public class RuleTjConditionQueryBo {
    /**
     * 规则项id
     */
    private Long ruleId;
    /**
     * 条件名称
     */
    private String name;
}
