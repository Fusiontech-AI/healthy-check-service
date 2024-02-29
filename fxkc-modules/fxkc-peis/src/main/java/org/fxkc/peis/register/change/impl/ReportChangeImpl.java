package org.fxkc.peis.register.change.impl;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.bo.TjRegCombinAddBo;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.register.change.AbstractRegisterChange;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 项目报道实现类
 */
@Service
@Slf4j
public class ReportChangeImpl extends AbstractRegisterChange {

    public ReportChangeImpl(){
        this.operateCode = "2";
    }


    @Override
    public void changeBeforeCheck(TjRegCombinAddBo tjRegCombinAddBo, TjRegister tjRegister) {
        log.info("体检登记id{},进入体检项目报道前置参数检查方法!",tjRegCombinAddBo.getRegisterId());
        if(!Objects.equals(HealthyCheckTypeEnum.预约.getCode(),tjRegister.getHealthyCheckStatus())){
            throw new RuntimeException("当前体检记录非预约状态，不能操作报道功能!");
        }
        //进行公共的性别符合要求的校验
        super.changeBeforeCheck(tjRegCombinAddBo,tjRegister);
    }


    @Override
    public void changeAfterUpdate(TjRegCombinAddBo tjRegCombinAddBo,TjRegister tjRegister) {
        super.changeAfterUpdate(tjRegCombinAddBo,tjRegister);
        //登记操作 还需要更新体检登记状态为 登记状态
        tjRegister.setHealthyCheckStatus(HealthyCheckTypeEnum.登记.getCode());
        tjRegister.setRegisterTime(new Date());//更新登记时间
        tjRegister.setRegisterDoctorId(LoginHelper.getUserId());//更新登记人id
        tjRegisterMapper.updateById(tjRegister);
    }
}
