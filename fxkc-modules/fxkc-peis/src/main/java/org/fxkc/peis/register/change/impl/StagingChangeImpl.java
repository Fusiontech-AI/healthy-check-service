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
 * 体检项目暂存实现类
 */
@Service
@Slf4j
public class StagingChangeImpl extends AbstractRegisterChange {

    public StagingChangeImpl(){
        this.operateCode = "4";
    }


    @Override
    public void changeBeforeCheck(TjRegCombinAddBo tjRegCombinAddBo, TjRegister tjRegister) {
        log.info("体检登记id{},进入体检项目暂存前置参数检查方法!",tjRegCombinAddBo.getRegisterId());
        if(!Objects.equals(HealthyCheckTypeEnum.预约.getCode(),tjRegister.getHealthyCheckStatus())){
            throw new RuntimeException("当前体检记录非预约状态，不能操作暂存功能!");
        }
        //进行公共的性别符合要求的校验
        super.changeBeforeCheck(tjRegCombinAddBo,tjRegister);
    }


    @Override
    public void changeAfterUpdate(TjRegCombinAddBo tjRegCombinAddBo,TjRegister tjRegister) {
        super.changeAfterUpdate(tjRegCombinAddBo,tjRegister);
        tjRegisterMapper.updateById(tjRegister);
        TjRecordLogEvent recordLogEvent = TjRecordLogEvent.builder().healthyCheckCode(tjRegister.getHealthyCheckCode())
            .credentialNumber(tjRegister.getCredentialNumber())
            .name(tjRegister.getName())
            .operType(TjRecordLogEnum.OPER_TYPE_XMZC.getDesc())
            .operDesc(TjRecordLogEnum.OPER_TYPE_XMZC.getDesc()).build();
        tjLogUtils.print(recordLogEvent);
    }
}
