package org.fxkc.peis.register.change.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.log.enums.TjRecordLogEnum;
import org.fxkc.common.log.event.TjRecordLogEvent;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.domain.TjRegCombinationProject;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.bo.TjRegCombinAddBo;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.register.change.AbstractRegisterChange;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
        log.info("体检登记id{},进入体检项目报到前置参数检查方法!",tjRegCombinAddBo.getRegisterId());
        if(!Objects.equals(HealthyCheckTypeEnum.预约.getCode(),tjRegister.getHealthyCheckStatus())){
            throw new RuntimeException("当前体检记录非预约状态，不能操作报到功能!");
        }

        //报道需要校验是否已有项目信息记录
        List<TjRegCombinationProject> tjRegCombinationProjects = tjRegCombinationProjectMapper.selectList(new LambdaQueryWrapper<TjRegCombinationProject>()
            .eq(TjRegCombinationProject::getRegisterId, tjRegCombinAddBo.getRegisterId()));
        if(CollUtil.isEmpty(tjRegCombinationProjects)){
            throw new RuntimeException("当前体检记录未选择项目信息，不能操作报到功能!");
        }
        //进行公共的性别符合要求的校验
        super.changeBeforeCheck(tjRegCombinAddBo,tjRegister);
    }


    @Override
    public void doService(TjRegCombinAddBo bo) {
        //批量报道 无需进行子项相关操作和修改
        if(!Objects.equals("0",bo.getBatchFlag())){
            super.doService(bo);
        }
    }

    @Override
    public void changeAfterUpdate(TjRegCombinAddBo tjRegCombinAddBo,TjRegister tjRegister) {
        //非批量报到方式 就需要走价格更新逻辑  否则批量报道只需要修改登记状态和时间即可
        if(!Objects.equals("0",tjRegCombinAddBo.getBatchFlag())){
            super.changeAfterUpdate(tjRegCombinAddBo,tjRegister);
        }
        //登记操作 还需要更新体检登记状态为 登记状态
        tjRegister.setHealthyCheckStatus(HealthyCheckTypeEnum.登记.getCode());
        tjRegister.setRegisterTime(new Date());//更新登记时间
        tjRegister.setRegisterDoctorId(LoginHelper.getUserId());//更新登记人id
        tjRegisterMapper.updateById(tjRegister);

        TjRecordLogEvent recordLogEvent = TjRecordLogEvent.builder().healthyCheckCode(tjRegister.getHealthyCheckCode())
            .credentialNumber(tjRegister.getCredentialNumber())
            .name(tjRegister.getName())
            .operType(TjRecordLogEnum.OPER_TYPE_RYBD.getDesc())
            .operDesc(TjRecordLogEnum.OPER_TYPE_RYBD.getDesc()).build();
        tjLogUtils.print(recordLogEvent);
    }
}
