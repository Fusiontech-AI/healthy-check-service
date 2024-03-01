package org.fxkc.peis.rule.execute.impl;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.rule.execute.AbstractRuleExecute;
import org.springframework.stereotype.Service;

/**
 * 高危异常规则执行实现类
 */
@Service
@Slf4j
public class HighRiskRuleImpl extends AbstractRuleExecute {

    public HighRiskRuleImpl(){
        this.operateCode = "3";
    }



}
