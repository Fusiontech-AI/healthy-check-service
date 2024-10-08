package org.fxkc.peis.register.insert;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.PinYinUtil;
import org.fxkc.common.core.utils.SequenceNoUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.log.enums.TjRecordLogEnum;
import org.fxkc.common.log.event.TjRecordLogEvent;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjArchives;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.bo.TjRegPeTimesBo;
import org.fxkc.peis.domain.bo.TjRegisterAddBo;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjArchivesMapper;
import org.fxkc.peis.mapper.TjRegisterMapper;
import org.fxkc.peis.service.ITjRegisterService;
import org.fxkc.peis.utils.TjLogUtils;
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
    protected ITjRegisterService tjRegisterService;

    @Autowired
    RegisterInsertHolder registerInsertHolder;

    @Autowired
    protected TjArchivesMapper tjArchivesMapper;

    @Autowired
    protected TjLogUtils tjLogUtils;

    @PostConstruct
    public void init() {
        registerInsertHolder.putBuilder(operateCode, this);
    }

    @Override
    public List<TjRegister> RegisterInsert(List<TjRegisterAddBo> tjRegisterAddBos) {
        List<TjRegister> tjRegisters = MapstructUtils.convert(tjRegisterAddBos, TjRegister.class);
        //默认字段信息赋值
        List<TjArchives> tjArchivesList = CollUtil.newArrayList();
        tjRegisters.stream().forEach(m->{
            fillArchives(m, tjArchivesList);
            fillCommonField(m);
            TjRecordLogEvent recordLogEvent = TjRecordLogEvent.builder().healthyCheckCode(m.getHealthyCheckCode())
                .credentialNumber(m.getCredentialNumber())
                .name(m.getName())
                .operType(TjRecordLogEnum.OPER_TYPE_RYDJ.getDesc())
                .operDesc(TjRecordLogEnum.OPER_TYPE_RYDJ.getDesc()).build();
            tjLogUtils.print(recordLogEvent);
        });
        tjRegisterMapper.insertBatch(tjRegisters);
        if(CollUtil.isNotEmpty(tjArchivesList)) {
            tjArchivesMapper.insertBatch(tjArchivesList);
        }
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
        tjRegister.setHealthyCheckCode(SequenceNoUtils.createNo(tjRegisterMapper.nextHealthyCode(),6,'0'));
        Long peTimes = tjRegisterService.getPeTimes(new TjRegPeTimesBo(tjRegister.getCredentialType(), tjRegister.getCredentialNumber()));
        tjRegister.setPeTimes(peTimes);
    }

    public void fillArchives(TjRegister tjRegister, List<TjArchives> tjArchivesList) {
        TjArchives tjArchives = tjArchivesMapper.selectOne(Wrappers.lambdaQuery(TjArchives.class)
            .eq(TjArchives::getName, tjRegister.getName())
            .eq(TjArchives::getCredentialType, tjRegister.getCredentialType())
            .eq(TjArchives::getCredentialNumber, tjRegister.getCredentialNumber()));
        if(Objects.nonNull(tjArchives)) {
            tjRegister.setRecordCode(tjArchives.getArchivesNo());
        }else {
            String tenantId;
            try {
                tenantId = LoginHelper.getLoginUser().getTenantId();
            }catch (Exception e) {
                throw new PeisException(ErrorCodeConstants.PEIS_NOT_LOGGED_IN);
            }
            String archivesNo = tjArchivesMapper.queryTjArchives();
            TjArchives resp = new TjArchives();
            resp.setArchivesNo("TJ".concat(tenantId).concat(StringUtils.zeroPrefix(archivesNo, 3)));
            resp.setName(tjRegister.getName());
            resp.setCredentialType(tjRegister.getCredentialType());
            resp.setCredentialNumber(tjRegister.getCredentialNumber());
            tjRegister.setRecordCode(resp.getArchivesNo());
            tjArchivesList.add(resp);
        }
    }

}
