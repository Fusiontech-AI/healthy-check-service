package org.fxkc.peis.register.insert.impl;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.utils.IDUtil;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.TjRegisterZyb;
import org.fxkc.peis.domain.TjRegisterZybHazard;
import org.fxkc.peis.domain.bo.TjRegisterAddBo;
import org.fxkc.peis.domain.bo.TjRegisterZybBo;
import org.fxkc.peis.domain.bo.TjRegisterZybHazardBo;
import org.fxkc.peis.mapper.TjRegisterZybHazardMapper;
import org.fxkc.peis.mapper.TjRegisterZybMapper;
import org.fxkc.peis.register.insert.AbstractRegisterInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 团检职业病登记
 */
@Service
@Slf4j
public class TeamOccuRegisterInsert extends AbstractRegisterInsert {

    @Autowired
    private TjRegisterZybMapper tjRegisterZybMapper;

    @Autowired
    private TjRegisterZybHazardMapper tjRegisterZybHazardMapper;

    public TeamOccuRegisterInsert(){
        this.operateCode = "20";
    }

    @Override
    public List<TjRegister> RegisterInsert(List<TjRegisterAddBo> tjRegisterAddBos) {
        List<TjRegister> tjRegisters = new ArrayList<>() ;
        List<TjRegisterZyb> tjRegisterZybs = new ArrayList<>();
        List<TjRegisterZybHazard> tjRegisterZybHazards = new ArrayList<>();
        //进行职业病相关联记录的插入
        tjRegisterAddBos.stream().forEach(m->{
            TjRegister tjRegister = MapstructUtils.convert(m, TjRegister.class);
            fillCommonField(tjRegister);
            tjRegister.setId(IDUtil.getInstance().nextId());
            tjRegisters.add(tjRegister);
            TjRegisterZybBo tjRegisterZybBo = m.getTjRegisterZybBo();
            TjRegisterZyb tjRegisterZyb = MapstructUtils.convert(tjRegisterZybBo, TjRegisterZyb.class);
            tjRegisterZyb.setRegId(tjRegister.getId());
            tjRegisterZybs.add(tjRegisterZyb);
            List<TjRegisterZybHazardBo> tjRegisterZybHazardBos = m.getTjRegisterZybHazardBos();
            List<TjRegisterZybHazard> tjRegisterZybHazardList = MapstructUtils.convert(tjRegisterZybHazardBos, TjRegisterZybHazard.class);
            tjRegisterZybHazardList.stream().forEach(hazard->{
                hazard.setRegId(tjRegister.getId());
            });
            tjRegisterZybHazards.addAll(tjRegisterZybHazardList);
        });

        tjRegisterMapper.insertBatch(tjRegisters);
        tjRegisterZybMapper.insertBatch(tjRegisterZybs);
        tjRegisterZybHazardMapper.insertBatch(tjRegisterZybHazards);
        return tjRegisters;
    }

    @Override
    public void RegisterPreCheck(List<TjRegisterAddBo> tjRegisterAddBos) {
        super.RegisterPreCheck(tjRegisterAddBos);
        //在公共前置检查完毕后  这里对团检职业病特有字段进行相关校验
        tjRegisterAddBos.stream().forEach(m->{
            TjRegisterZybBo tjRegisterZybBo = m.getTjRegisterZybBo();
            Assert.notNull(tjRegisterZybBo,"职业病相关参数对象不能为空!");
            List<TjRegisterZybHazardBo> tjRegisterZybHazardBos = m.getTjRegisterZybHazardBos();
            if(CollUtil.isEmpty(tjRegisterZybHazardBos)){
                throw new RuntimeException("危害因素信息为空!");
            }
            if(StringUtils.isEmpty(tjRegisterZybBo.getDutyStatus())){
                throw new RuntimeException("在岗状态为空!");
            }
            if(StringUtils.isEmpty(tjRegisterZybBo.getCaseCardType())){
                throw new RuntimeException("个案卡类别为空!");
            }
            if(StringUtils.isEmpty(tjRegisterZybBo.getJobCode())){
                throw new RuntimeException("工种编码为空!");
            }
            if(Objects.isNull(tjRegisterZybBo.getContactSeniorityYear())){
                throw new RuntimeException("接害工龄年份为空!");
            }
            if(Objects.isNull(tjRegisterZybBo.getContactSeniorityMonth())){
                throw new RuntimeException("接害工龄月份为空!");
            }
            if(Objects.isNull(tjRegisterZybBo.getSeniorityYear())){
                throw new RuntimeException("总工龄年份为空!");
            }
            if(Objects.isNull(tjRegisterZybBo.getSeniorityMonth())){
                throw new RuntimeException("总工龄月份为空!");
            }
        });

    }
}
