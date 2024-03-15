package org.fxkc.peis.register.change.impl;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.log.enums.TjRecordLogEnum;
import org.fxkc.common.log.event.TjRecordLogEvent;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.bo.TjRegCombinAddBo;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.register.change.AbstractRegisterChange;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 项目变更实现类
 */
@Service
@Slf4j
public class ProjectChangeImpl extends AbstractRegisterChange {

    public ProjectChangeImpl(){
        this.operateCode = "3";
    }


    @Override
    public void changeBeforeCheck(TjRegCombinAddBo tjRegCombinAddBo, TjRegister tjRegister) {
        log.info("体检登记id{},进入体检项目变更前置参数检查方法!",tjRegCombinAddBo.getRegisterId());
        if(Objects.equals(HealthyCheckTypeEnum.预约.getCode(),tjRegister.getHealthyCheckStatus())
          || Objects.equals(HealthyCheckTypeEnum.已终检.getCode(),tjRegister.getHealthyCheckStatus())){
            throw new RuntimeException("当前体检项目变更状态不合法，不能操作变更项目功能!");
        }
    }


    @Override
    public void changeAfterUpdate(TjRegCombinAddBo tjRegCombinAddBo,TjRegister tjRegister) {
        super.changeAfterUpdate(tjRegCombinAddBo,tjRegister);
        tjRegisterMapper.updateById(tjRegister);
        TjRecordLogEvent recordLogEvent = TjRecordLogEvent.builder().healthyCheckCode(tjRegister.getHealthyCheckCode())
            .credentialNumber(tjRegister.getCredentialNumber())
            .name(tjRegister.getName())
            .operType(TjRecordLogEnum.OPER_TYPE_XMBG.getDesc())
            .operDesc(TjRecordLogEnum.OPER_TYPE_XMBG.getDesc()).build();
        tjLogUtils.print(recordLogEvent);
    }
}
