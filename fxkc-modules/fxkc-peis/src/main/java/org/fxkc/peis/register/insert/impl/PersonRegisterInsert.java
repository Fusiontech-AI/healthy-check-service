package org.fxkc.peis.register.insert.impl;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.register.insert.AbstractRegisterInsert;
import org.springframework.stereotype.Service;

/**
 * 个检登记
 */
@Service
@Slf4j
public class PersonRegisterInsert extends AbstractRegisterInsert {

    public PersonRegisterInsert(){
        this.operateCode = "10";
    }


}
