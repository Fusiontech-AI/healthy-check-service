package org.fxkc.peis.rule.execute;

import org.fxkc.peis.domain.bo.RuleExecuteBo;
import org.fxkc.peis.domain.vo.RuleTjInfoExcuteResultVo;

import java.util.List;

/**
 * 规则执行接口
 */
public interface RuleExecuteService {

    /**
     * 规则执行接口
     */
    public List<RuleTjInfoExcuteResultVo> executeRule(RuleExecuteBo ruleExecuteBo);

}
