package org.fxkc.peis.register.insert.impl;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.register.insert.AbstractRegisterInsert;
import org.springframework.stereotype.Service;

/**
 * 团检健康登记
 */
@Service
@Slf4j
public class TeamHealthRegisterInsert extends AbstractRegisterInsert {

    public TeamHealthRegisterInsert(){
        this.operateCode = "21";
    }

}
