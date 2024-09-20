package org.fxkc.peis.domain.bo;

import lombok.Data;

/**
 * 规则项记录信息查询请求Bo
 */
@Data
public class RuleTjInfoQueryBo {

    /**
     * 规则集id
     */
    private Long rulesetId;

    /**
     * 规则名称
     */
    private String name;
}
