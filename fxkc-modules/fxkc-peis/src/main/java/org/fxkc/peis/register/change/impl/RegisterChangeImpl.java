package org.fxkc.peis.register.change.impl;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.bo.TjRegCombinAddBo;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.register.change.AbstractRegisterChange;
import org.springframework.stereotype.Service;

/**
 * 项目登记实现类
 */
@Service
@Slf4j
public class RegisterChangeImpl extends AbstractRegisterChange {

    public RegisterChangeImpl(){
        this.operateCode = "1";
    }


    @Override
    public void changeBeforeCheck(TjRegCombinAddBo tjRegCombinAddBo, TjRegister tjRegister) {
        log.info("体检登记id{},进入体检项目登记前置参数检查方法!",tjRegCombinAddBo.getRegisterId());
        if(CollUtil.isEmpty(tjRegCombinAddBo.getTjRegCombinItemBos())){
            throw new RuntimeException("当前进行体检登记时,选择项目信息为空!");
        }
        //进行公共的性别符合要求的校验
        super.changeBeforeCheck(tjRegCombinAddBo,tjRegister);

    }

    @Override
    public void doService(TjRegCombinAddBo bo) {
        //体检项目登记 针对于 初次添加项目信息登记  直接入库操作。
        addRegCombinItems(bo.getTjRegCombinItemBos(),bo.getRegisterId());
    }

    @Override
    public void changeAfterUpdate(TjRegCombinAddBo tjRegCombinAddBo,TjRegister tjRegister) {
        super.changeAfterUpdate(tjRegCombinAddBo,tjRegister);
        //登记操作 还需要更新体检登记状态为 登记状态
        tjRegister.setHealthyCheckStatus(HealthyCheckTypeEnum.登记.getCode());
        tjRegisterMapper.updateById(tjRegister);
    }
}
