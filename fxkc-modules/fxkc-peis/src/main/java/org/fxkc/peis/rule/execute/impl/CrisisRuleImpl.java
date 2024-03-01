package org.fxkc.peis.rule.execute.impl;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.rule.execute.AbstractRuleExecute;
import org.springframework.stereotype.Service;

/**
 * 危急值规则执行实现类
 */
@Service
@Slf4j
public class CrisisRuleImpl extends AbstractRuleExecute {

    public CrisisRuleImpl(){
        this.operateCode = "2";
    }



}
