package org.fxkc.peis.register.insert;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.PinYinUtil;
import org.fxkc.common.core.utils.SequenceNoUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.bo.TjRegisterAddBo;
import org.fxkc.peis.mapper.TjRegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AbstractRegisterInsert implements RegisterInsertService{

    protected String operateCode;

    @Autowired
    protected  TjRegisterMapper tjRegisterMapper;


    @Autowired
    RegisterInsertHolder registerInsertHolder;

    @PostConstruct
    public void init() {
        registerInsertHolder.putBuilder(operateCode, this);
    }

    @Override
    public List<TjRegister> RegisterInsert(List<TjRegisterAddBo> tjRegisterAddBos) {
        List<TjRegister> tjRegisters = MapstructUtils.convert(tjRegisterAddBos, TjRegister.class);
        //默认字段信息赋值
        tjRegisters.stream().forEach(m->{
            fillCommonField(m);
        });
        tjRegisterMapper.insertBatch(tjRegisters);
        return tjRegisters;
    }

    @Override
    public void RegisterPreCheck(List<TjRegisterAddBo> tjRegisterAddBos) {

        log.info("进行前置检查记录数为"+tjRegisterAddBos.size());
        tjRegisterAddBos.stream().forEach(m->{
            if(StringUtils.isEmpty(m.getName())){
                throw new RuntimeException("姓名为空!");
            }
            if(StringUtils.isEmpty(m.getCredentialType())){
                throw new RuntimeException("证件类型为空!");
            }
            if(StringUtils.isEmpty(m.getCredentialNumber())){
                throw new RuntimeException("证件号为空!");
            }
            if(Objects.isNull(m.getBirthday())){
                throw new RuntimeException("出生日期为空!");
            }
            if(StringUtils.isEmpty(m.getPhysicalType())){
                throw new RuntimeException("体检类型为空!");
            }
            if(StringUtils.isEmpty(m.getOccupationalType())){
                throw new RuntimeException("体检类型分类为空!");
            }
            if(StringUtils.isEmpty(m.getPhone())){
                throw new RuntimeException("联系电话为空!");
            }
            if(StringUtils.isEmpty(m.getBusinessCategory())){
                throw new RuntimeException("业务类别为空!");
            }
        });
    }


    /**
     * 体检登记信息插入前相关默认值处理赋值
     * @param tjRegister
     */
    public void fillCommonField(TjRegister tjRegister){
        tjRegister.setNamePy(PinYinUtil.getPinyin(tjRegister.getName()));
        tjRegister.setRecordCode(tjRegister.getCredentialNumber());
        if(StrUtil.isBlank(tjRegister.getHealthyCheckStatus())) {
            tjRegister.setHealthyCheckStatus("1");//登记状态
        }
        tjRegister.setHealthyCheckCode(SequenceNoUtils.padl(tjRegisterMapper.nextHealthyCode(),6,'0'));
        Long count = tjRegisterMapper.selectCount(new LambdaQueryWrapper<TjRegister>().eq(TjRegister::getCredentialNumber, tjRegister.getCredentialNumber()));
        tjRegister.setPeTimes(count + 1);
    }
}
