package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.exception.ServiceException;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.log.enums.TjRecordLogEnum;
import org.fxkc.common.log.event.TjRecordLogEvent;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.oss.core.OssClient;
import org.fxkc.common.oss.factory.OssFactory;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.*;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.domain.vo.*;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.enums.RegisterStatusEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.*;
import org.fxkc.peis.register.change.RegisterChangeHolder;
import org.fxkc.peis.register.change.RegisterChangeService;
import org.fxkc.peis.register.insert.RegisterInsertHolder;
import org.fxkc.peis.register.insert.RegisterInsertService;
import org.fxkc.peis.service.ITjPackageService;
import org.fxkc.peis.service.ITjRegisterService;
import org.fxkc.peis.utils.TjLogUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 体检人员登记信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class TjRegisterServiceImpl implements ITjRegisterService {

    private final TjRegisterMapper baseMapper;

    private final RegisterInsertHolder registerInsertHolder;

    private final RegisterChangeHolder registerChangeHolder;

    private final TjRegisterZybMapper tjRegisterZybMapper;

    private final  TjRegisterZybHazardMapper tjRegisterZybHazardMapper;

    private final TjRegCombinationProjectMapper tjRegCombinationProjectMapper;

    private final TjRegBasicProjectMapper tjRegBasicProjectMapper;

    private final TjCombinationProjectInfoMapper tjCombinationProjectInfoMapper;

    private final TjArchivesMapper tjArchivesMapper;

    private final TjTeamGroupMapper tjTeamGroupMapper;

    private final TjTeamGroupHistoryMapper tjTeamGroupHistoryMapper;

    private final ITjPackageService tjPackageService;

    protected final TjLogUtils tjLogUtils;
    /**
     * 查询体检人员登记信息
     */
    @Override
    public TjRegisterVo queryById(Long id){
        TjRegisterVo tjRegisterVo = baseMapper.selectVoById(id);
        if(tjRegisterVo.getTeamGroupId()!=null){
            TjTeamGroupVo teamGroupVo = getTjTeamGroupVoById(tjRegisterVo.getTeamGroupId(), id,tjRegisterVo.getHealthyCheckStatus());
            //填充分组响应内容到前端
            tjRegisterVo.setTjTeamGroupVo(teamGroupVo);
        }

        //职业病类型需要再关联查询职业病相关信息
        if(Objects.equals(tjRegisterVo.getOccupationalType(),"0")){
            TjRegisterZybVo tjRegisterZybVo = tjRegisterZybMapper.selectVoOne(new LambdaQueryWrapper<TjRegisterZyb>()
                .eq(TjRegisterZyb::getRegId, tjRegisterVo.getId()));
            List<TjRegisterZybHazardVo> tjRegisterZybHazardVos = tjRegisterZybHazardMapper.selectVoList(new LambdaQueryWrapper<TjRegisterZybHazard>()
                .eq(TjRegisterZybHazard::getRegId, tjRegisterVo.getId()));
            tjRegisterZybVo.setTjRegisterZybHazardVos(tjRegisterZybHazardVos);
            tjRegisterVo.setTjRegisterZybVo(tjRegisterZybVo);
        }

        return tjRegisterVo;
    }

    /**
     * 查询体检人员登记信息列表
     */
    @Override
    public TableDataInfo<TjRegisterPageVo> queryPageList(TjRegisterPageBo bo, PageQuery pageQuery) {
        Page<TjRegisterPageVo> result = baseMapper.selectPage(bo, pageQuery.build());
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检人员登记信息列表
     */
    @Override
    public List<TjRegisterVo> queryList(TjRegisterBo bo) {
        LambdaQueryWrapper<TjRegister> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjRegister> buildQueryWrapper(TjRegisterBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjRegister> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getHealthyCheckCode()), TjRegister::getHealthyCheckCode, bo.getHealthyCheckCode());
        lqw.eq(StringUtils.isNotBlank(bo.getRecordCode()), TjRegister::getRecordCode, bo.getRecordCode());
        lqw.like(StringUtils.isNotBlank(bo.getName()), TjRegister::getName, bo.getName());
        lqw.eq(bo.getAge() != null, TjRegister::getAge, bo.getAge());
        lqw.eq(StringUtils.isNotBlank(bo.getGender()), TjRegister::getGender, bo.getGender());
        lqw.eq(StringUtils.isNotBlank(bo.getMarriageStatus()), TjRegister::getMarriageStatus, bo.getMarriageStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getCredentialType()), TjRegister::getCredentialType, bo.getCredentialType());
        lqw.eq(StringUtils.isNotBlank(bo.getCredentialNumber()), TjRegister::getCredentialNumber, bo.getCredentialNumber());
        lqw.eq(bo.getBirthday() != null, TjRegister::getBirthday, bo.getBirthday());
        lqw.eq(StringUtils.isNotBlank(bo.getNation()), TjRegister::getNation, bo.getNation());
        lqw.eq(StringUtils.isNotBlank(bo.getPhysicalType()), TjRegister::getPhysicalType, bo.getPhysicalType());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), TjRegister::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getUserImage()), TjRegister::getUserImage, bo.getUserImage());
        lqw.eq(StringUtils.isNotBlank(bo.getNeedGeneralReview()), TjRegister::getNeedGeneralReview, bo.getNeedGeneralReview());
        lqw.eq(StringUtils.isNotBlank(bo.getRecipient()), TjRegister::getRecipient, bo.getRecipient());
        lqw.eq(StringUtils.isNotBlank(bo.getReceiptPhone()), TjRegister::getReceiptPhone, bo.getReceiptPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getPostAddress()), TjRegister::getPostAddress, bo.getPostAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getBusinessCategory()), TjRegister::getBusinessCategory, bo.getBusinessCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getGuideSheetReceived()), TjRegister::getGuideSheetReceived, bo.getGuideSheetReceived());
        lqw.eq(StringUtils.isNotBlank(bo.getFreezeStatus()), TjRegister::getFreezeStatus, bo.getFreezeStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getHealthyCheckStatus()), TjRegister::getHealthyCheckStatus, bo.getHealthyCheckStatus());
        lqw.eq(bo.getTeamId() != null, TjRegister::getTeamId, bo.getTeamId());
        lqw.eq(bo.getTeamGroupId() != null, TjRegister::getTeamGroupId, bo.getTeamGroupId());
        lqw.eq(bo.getTeamDeptId() != null, TjRegister::getTeamDeptId, bo.getTeamDeptId());
        lqw.eq(bo.getIntroducer() != null, TjRegister::getIntroducer, bo.getIntroducer());
        lqw.eq(bo.getGeneralReviewDoctor() != null, TjRegister::getGeneralReviewDoctor, bo.getGeneralReviewDoctor());
        lqw.eq(bo.getGeneralReviewTime() != null, TjRegister::getGeneralReviewTime, bo.getGeneralReviewTime());
        lqw.eq(bo.getHealthyCheckTime() != null, TjRegister::getHealthyCheckTime, bo.getHealthyCheckTime());
        lqw.eq(bo.getAuditDoctor() != null, TjRegister::getAuditDoctor, bo.getAuditDoctor());
        lqw.eq(bo.getAuditTime() != null, TjRegister::getAuditTime, bo.getAuditTime());
        lqw.eq(bo.getFinishTime() != null, TjRegister::getFinishTime, bo.getFinishTime());
        lqw.eq(bo.getCancelRegisterTime() != null, TjRegister::getCancelRegisterTime, bo.getCancelRegisterTime());
        lqw.eq(bo.getCancelRegisterOperator() != null, TjRegister::getCancelRegisterOperator, bo.getCancelRegisterOperator());
        return lqw;
    }

    /**
     * 新增体检人员登记信息
     */
    @Override
    public Long insertByBo(TjRegisterAddBo bo) {
        //根据团检类型 和 体检类型的业务分类组成联合key路由到相应策略实现类执行
        RegisterInsertService registerInsertService = registerInsertHolder.selectBuilder(bo.getBusinessCategory() + bo.getOccupationalType());
        List<TjRegister> tjRegisters = registerInsertService.RegisterInsert(Arrays.asList(bo));
        return tjRegisters.get(0).getId();
    }

    /**
     * 修改体检人员登记信息
     */
    @Override
    public Boolean updateByBo(TjRegisterBo bo) {
        TjRegister update = MapstructUtils.convert(bo, TjRegister.class);
        //validEntityBeforeSave(update);
        if(Objects.nonNull(bo.getTjRegisterZybBo())){
            TjRegisterZyb tjRegisterZyb = MapstructUtils.convert(bo.getTjRegisterZybBo(), TjRegisterZyb.class);
            tjRegisterZybMapper.updateById(tjRegisterZyb);
        }

        if(CollUtil.isNotEmpty(bo.getTjRegisterZybHazardBos())){
            tjRegisterZybHazardMapper.delete(new LambdaQueryWrapper<TjRegisterZybHazard>()
                .eq(TjRegisterZybHazard::getRegId,bo.getId()));
            List<TjRegisterZybHazard> tjRegisterZybHazardList = MapstructUtils.convert(bo.getTjRegisterZybHazardBos(), TjRegisterZybHazard.class);
            tjRegisterZybHazardMapper.insertBatch(tjRegisterZybHazardList);
        }
        //不应在这里修改体检人员照片信息
        update.setUserImage(null);

        TjRecordLogEvent recordLogEvent = TjRecordLogEvent.builder().healthyCheckCode(update.getHealthyCheckCode())
            .credentialNumber(update.getCredentialNumber())
            .name(update.getName())
            .operType(TjRecordLogEnum.OPER_TYPE_RYXG.getDesc())
            .operDesc(TjRecordLogEnum.OPER_TYPE_RYXG.getDesc()).build();
        tjLogUtils.print(recordLogEvent);

        return baseMapper.updateById(update) > 0;
    }


    /**
     * 批量删除体检人员登记信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        List<TjRegister> tjRegisters = baseMapper.selectBatchIds(ids);
        tjRegisters.stream().forEach(register->{
            TjRecordLogEvent recordLogEvent = TjRecordLogEvent.builder().healthyCheckCode(register.getHealthyCheckCode())
                .credentialNumber(register.getCredentialNumber())
                .name(register.getName())
                .operType(TjRecordLogEnum.OPER_TYPE_RYSC.getDesc())
                .operDesc(TjRecordLogEnum.OPER_TYPE_RYSC.getDesc()).build();
            tjLogUtils.print(recordLogEvent);
        });
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 批量取消体检人员登记
     * @param ids
     * @return
     */
    @Override
    public Boolean cancelRegistration(Collection<Long> ids) {
        LambdaQueryWrapper<TjRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(TjRegister::getId,ids)
            .eq(TjRegister::getDelFlag, CommonConstants.NORMAL)
            .in(TjRegister::getHealthyCheckStatus, Arrays.asList(HealthyCheckTypeEnum.预约.getCode(),HealthyCheckTypeEnum.登记.getCode()));
        Long actualCount = baseMapper.selectCount(wrapper);
        if(ids.size() - actualCount != 0){
            throw new ServiceException("仅限预约、登记状态人员可取消登记，请重新选择！");
        }
        //TODO 逻辑删除该人员登记时的项目信息，或在数据统计时强制必须关联一下主表，否则数据统计可能会有问题
        //TODO 判断人员是否缴费，如果已缴费，向HIS发起退费申请，走退费逻辑

        return baseMapper.update(TjRegister.builder()
                .status(RegisterStatusEnum.取消登记.getCode())
                .delFlag(CommonConstants.DISABLE)
            .cancelRegisterOperator(LoginHelper.getLoginUser().getUserId())
            .cancelRegisterTime(DateUtil.date()).build()
            ,Wrappers.lambdaQuery(TjRegister.class).in(TjRegister::getId,ids))>0;
    }

    @Override
    public Boolean reRegistration(Collection<Long> ids) {
        //TODO 恢复该人员登记时的项目信息，或在数据统计时强制必须关联一下主表，否则数据统计可能会有问题

        return baseMapper.update(TjRegister.builder()
                .status(RegisterStatusEnum.正常.getCode()).delFlag(CommonConstants.NORMAL).build()
            ,Wrappers.lambdaQuery(TjRegister.class).in(TjRegister::getId,ids))>0;
    }

    @Override
    public Boolean freeze(Collection<Long> ids) {
        return baseMapper.update(TjRegister.builder().freezeStatus("0").delFlag(CommonConstants.NORMAL).build(),
            new LambdaUpdateWrapper<TjRegister>().in(TjRegister::getId,ids)) > 0;
    }

    @Override
    public Boolean unfreeze(Collection<Long> ids) {
        return baseMapper.update(TjRegister.builder().freezeStatus("1").delFlag(CommonConstants.NORMAL).build(),
            new LambdaUpdateWrapper<TjRegister>().in(TjRegister::getId,ids)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeRegCombin(TjRegCombinAddBo bo) {
        RegisterChangeService registerChangeService = registerChangeHolder.selectBuilder(bo.getOperationType());
        registerChangeService.changeRegCombin(bo);
        return true;
    }

    @Override
    public Boolean changeRegReplaceInfo(TjRegReplaceInfoBo bo) {
        //先查询已有记录 是否为替检过状态
        TjRegister tjRegister = baseMapper.selectById(bo.getRegisterId());
        Assert.notNull(tjRegister,"根据登记id["+bo.getRegisterId()+"],未找到登录记录!");
        TjRegister updateEntity = new TjRegister();
        updateEntity.setId(bo.getRegisterId());
        //不等于0是的时候 初次将原体检人信息存入 replace相关字段。
        if(!Objects.equals("0",tjRegister.getReplaceFlag())){
            updateEntity.setReplaceName(tjRegister.getName());
            updateEntity.setReplaceGender(tjRegister.getGender());
            updateEntity.setReplaceCredentialType(tjRegister.getCredentialType());
            updateEntity.setReplaceCredentialNumber(tjRegister.getCredentialNumber());
            updateEntity.setReplaceAge(tjRegister.getAge());
            updateEntity.setReplaceBirthday(tjRegister.getBirthday());
            updateEntity.setReplacePhone(tjRegister.getPhone());
        }
        updateEntity.setReplaceFlag("0");
        updateEntity.setName(bo.getReplaceName());
        updateEntity.setGender(bo.getReplaceGender());
        updateEntity.setCredentialType(bo.getReplaceCredentialType());
        updateEntity.setCredentialNumber(bo.getReplaceCredentialNumber());
        updateEntity.setAge(bo.getReplaceAge());
        updateEntity.setBirthday(bo.getReplaceBirthday());
        updateEntity.setPhone(bo.getReplacePhone());
        return baseMapper.updateById(updateEntity)> 0;
    }

    @Override
    public Boolean changeReportReceiveWay(TjRegReceiveWayBo bo) {
        //先查询已有记录
        TjRegister tjRegister = baseMapper.selectById(bo.getRegisterId());
        Assert.notNull(tjRegister,"根据登记id["+bo.getRegisterId()+"],未找到登录记录!");
        TjRegister updateEntity = new TjRegister();
        updateEntity.setId(bo.getRegisterId());
        updateEntity.setReceiveWay(bo.getReceiveWay());
        updateEntity.setReceiptPhone(bo.getReceiptPhone());
        updateEntity.setRecipient(bo.getRecipient());
        updateEntity.setPostAddress(bo.getPostAddress());
        return baseMapper.updateById(updateEntity)> 0;
    }

    @Override
    public void updateGuideSheetPrint(ReportPrintBO bo) {
        this.baseMapper.updateGuideSheetPrint(bo);
    }

    @Override
    public Boolean saveDiagnosis(TjRegSaveDiagnosisBo bo) {
        //拿到当前需要保存的组合项目登记主键记录
        TjRegCombinationProject tjRegCombinationProject = tjRegCombinationProjectMapper.selectById(bo.getRegItemId());
        Assert.notNull(tjRegCombinationProject,"根据登记组合id"+bo.getRegItemId()+"未找到对应记录!");
        //为弃检状态时不能保存结果
        if(Objects.equals("2",tjRegCombinationProject.getCheckStatus())){
            throw new RuntimeException("此组合项目已放弃检查,不能保存!");
        }

        //修改基础子项结果内容
        List<TjRegBasicProjectSaveBo> regBasicProjects = bo.getRegBasicProjects();
        if(CollUtil.isNotEmpty(regBasicProjects)){
            List<TjRegBasicProject> tjRegBasicProjects = MapstructUtils.convert(regBasicProjects, TjRegBasicProject.class);
            tjRegBasicProjectMapper.updateBatchById(tjRegBasicProjects);
        }
        //修改组合项目结果内容
        tjRegCombinationProject.setCheckDoctor(bo.getCheckDoctor()==null ? LoginHelper.getUserId():bo.getCheckDoctor());
        tjRegCombinationProject.setCheckDoctorName(StringUtils.isEmpty(bo.getCheckDoctorName())? LoginHelper.getUsername(): bo.getCheckDoctorName());
        tjRegCombinationProject.setCheckTime(bo.getCheckTime()==null ? DateUtil.date() : bo.getCheckTime());
        tjRegCombinationProject.setCheckStatus(StringUtils.isEmpty(bo.getCheckStatus())
            ? "1" : bo.getCheckStatus());//已检查
        tjRegCombinationProjectMapper.updateById(tjRegCombinationProject);

        //这里需要更改登记记录允许总检状态  以及对诊断明细相关记录的保存
        return true;
    }

    @Override
    public Boolean deleteTaskRegister(Long id) {
        TjRegister tjRegister = baseMapper.selectById(id);
        if(Objects.nonNull(tjRegister) && ObjectUtil.notEqual(tjRegister.getHealthyCheckStatus(), HealthyCheckTypeEnum.预约.getCode())) {
            throw new PeisException(ErrorCodeConstants.PEIS_REGISTER_NOT_APPIONT);
        }
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mergeArchives(TjArchivesBo bo) {
        List<TjArchivesBo.TjArchivesData> dataList = bo.getDataList();
        if(dataList.size() < 1) {
            throw new PeisException(ErrorCodeConstants.PEIS_ARCHIVES_NOT_LESS);
        }
        TjArchivesBo.TjArchivesData tjArchivesData;
        if(Objects.equals(CommonConstants.NORMAL, bo.getIsAuto())) {
            tjArchivesData = dataList.get(0);
        }else {
            Optional<TjArchivesBo.TjArchivesData> optional = StreamUtils.filter(dataList, e -> Objects.equals(CommonConstants.NORMAL, e.getIsChoose()))
                .stream().findFirst();
            if(optional.isPresent()) {
                tjArchivesData = optional.get();
            }else {
                throw new PeisException(ErrorCodeConstants.PEIS_ARCHIVES_PARAM_ERROR);
            }
        }
        TjArchivesBo.TjArchivesData finalTjArchivesData = tjArchivesData;
        List<TjArchivesBo.TjArchivesData> mergeList = StreamUtils.filter(dataList, e -> ObjectUtil.notEqual(e.getRecordCode(), finalTjArchivesData.getRecordCode()));
        mergeList.forEach(k -> {
            baseMapper.update(TjRegister.builder().recordCode(finalTjArchivesData.getRecordCode())
                .oldRecordCode(k.getRecordCode())
                .mergeRecordBy(LoginHelper.getUserId())
                .mergeRecordTime(DateUtil.date()).build(),
                Wrappers.lambdaUpdate(TjRegister.class)
                    .eq(TjRegister::getRecordCode, k.getRecordCode()));
            tjArchivesMapper.delete(Wrappers.lambdaQuery(TjArchives.class)
                .eq(TjArchives::getArchivesNo, k.getRecordCode()));
        });
    }

    @Override
    public void updatePersonalReportPrint(ReportPrintBO bo) {
        baseMapper.updatePersonalReportPrint(bo);
    }

    @Override
    public Boolean teamToPerson(TjRegTeamToPersonBo bo) {
        List<TjRegister> tjRegisters = baseMapper.selectBatchIds(bo.getRegIds());
        //相关数据校验
        tjRegisters.stream().forEach(tjRegister -> {
            if(!Objects.equals("2",tjRegister.getBusinessCategory())){
                throw new RuntimeException("存在非团检类型记录数据,不可变更!");
            }
            if(!Objects.equals("0",tjRegister.getChargeStatus())){
                throw new RuntimeException("存在已缴费记录数据,不可变更!");
            }
        });


        List<TjRegister> registers = tjRegisters.stream().map(tjRegister -> {
            AmountCalculationVo amountCalculationVo = billingByRegister(tjRegister);
            TjRegister register = new TjRegister();
            register.setId(tjRegister.getId());
            register.setBusinessCategory("1");
            register.setTotalStandardAmount(amountCalculationVo.getStandardAmount());
            register.setTotalAmount(amountCalculationVo.getReceivableAmount());
            register.setPersonAmount(amountCalculationVo.getPersonAmount());
            register.setDiscount(amountCalculationVo.getDiscount());
            register.setTeamAmount(amountCalculationVo.getTeamAmount());
            register.setPaidTotalAmount(amountCalculationVo.getPaidTotalAmount());
            register.setPaidPersonAmount(amountCalculationVo.getPaidPersonAmount());
            register.setPaidTeamAmount(amountCalculationVo.getPaidTeamAmount());
            return register;
        }).collect(Collectors.toList());

        return baseMapper.updateBatchById(registers);
    }

    @Override
    public Boolean personToTeam(TjRegPersonToTeamBo bo) {
        List<TjRegister> tjRegisters = baseMapper.selectBatchIds(bo.getRegIds());
        //相关数据校验
        tjRegisters.stream().forEach(tjRegister -> {
            if(!Objects.equals("1",tjRegister.getBusinessCategory())){
                throw new RuntimeException("存在非个检类型记录数据,不可变更!");
            }
            if(!Objects.equals("0",tjRegister.getChargeStatus())){
                throw new RuntimeException("存在已缴费记录数据,不可变更!");
            }
        });


        List<TjRegister> registers = tjRegisters.stream().map(tjRegister -> {
            tjRegister.setTeamGroupId(bo.getTeamGroupId());
            AmountCalculationVo amountCalculationVo = billingByRegister(tjRegister);
            TjRegister register = new TjRegister();
            register.setId(tjRegister.getId());
            register.setBusinessCategory("2");
            register.setTotalStandardAmount(amountCalculationVo.getStandardAmount());
            register.setTotalAmount(amountCalculationVo.getReceivableAmount());
            register.setPersonAmount(amountCalculationVo.getPersonAmount());
            register.setDiscount(amountCalculationVo.getDiscount());
            register.setTeamAmount(amountCalculationVo.getTeamAmount());
            register.setPaidTotalAmount(amountCalculationVo.getPaidTotalAmount());
            register.setPaidPersonAmount(amountCalculationVo.getPaidPersonAmount());
            register.setPaidTeamAmount(amountCalculationVo.getPaidTeamAmount());
            register.setTeamId(bo.getTeamId());
            register.setTeamGroupId(bo.getTeamGroupId());
            register.setTaskId(bo.getTaskId());
            return register;
        }).collect(Collectors.toList());

        return baseMapper.updateBatchById(registers);
    }

    @Override
    public AmountCalculationVo billingByRegister(TjRegister tjRegister) {
        //组装算费请求对象 重新计算相关费用情况并更新
        List<TjRegCombinationProject> combinationProjects = tjRegCombinationProjectMapper.selectList(new LambdaQueryWrapper<TjRegCombinationProject>()
            .eq(TjRegCombinationProject::getRegisterId, tjRegister.getId()));
        if(CollUtil.isEmpty(combinationProjects)){
            //直接返回都是金额0和折扣默认100的初始值信息。
            AmountCalculationVo amountCalculationVo = new AmountCalculationVo();
            amountCalculationVo.setTeamAmount(new BigDecimal("0"));
            amountCalculationVo.setPersonAmount(new BigDecimal("0"));
            amountCalculationVo.setUnPaidTotalAmount(new BigDecimal("0"));
            amountCalculationVo.setReceivableAmount(new BigDecimal("0"));
            amountCalculationVo.setPaidPersonAmount(new BigDecimal("0"));
            amountCalculationVo.setPaidTeamAmount(new BigDecimal("0"));
            amountCalculationVo.setPaidTotalAmount(new BigDecimal("0"));
            amountCalculationVo.setStandardAmount(new BigDecimal("0"));
            amountCalculationVo.setDiscount(new BigDecimal("100"));
            return amountCalculationVo;
        }
        AmountCalculationBo amountCalculationBo = new AmountCalculationBo();
        amountCalculationBo.setRegType(tjRegister.getBusinessCategory());
        amountCalculationBo.setChangeType("3");//固定可以按照新增项目去计算
        if(tjRegister.getTeamGroupId()!=null){
            //组装算费的分组请求信息
            amountCalculationBo.setGroupFlag("1");
            TjTeamGroupVo teamGroupVo = getTjTeamGroupVoById(tjRegister.getTeamGroupId(), tjRegister.getId(),tjRegister.getHealthyCheckStatus());
            AmountCalGroupBo amountCalGroupBo = new AmountCalGroupBo(teamGroupVo.getGroupType(),teamGroupVo.getPrice(),teamGroupVo.getGroupPayType(),teamGroupVo.getAddPayType(),teamGroupVo.getItemDiscount(),teamGroupVo.getAddDiscount());
            amountCalculationBo.setAmountCalGroupBo(amountCalGroupBo);
        }
        //组装算费新增的记录信息参数
        List<AmountCalculationItemBo> amountCalculationItemBos = combinationProjects.stream().map(combinationProject -> {
            AmountCalculationItemBo itemBo = new AmountCalculationItemBo();
            itemBo.setId(combinationProject.getId());
            itemBo.setCombinProjectId(combinationProject.getCombinationProjectId());
            itemBo.setSort(1);
            itemBo.setStandardAmount(combinationProject.getStandardAmount());
            itemBo.setDiscount(combinationProject.getDiscount());
            itemBo.setReceivableAmount(combinationProject.getReceivableAmount());
            itemBo.setPersonAmount(combinationProject.getPersonAmount());
            itemBo.setTeamAmount(combinationProject.getTeamAmount());
            itemBo.setPayType(combinationProject.getPayMode());
            itemBo.setPayStatus(combinationProject.getPayStatus());
            itemBo.setTcFlag(combinationProject.getProjectType());
            return itemBo;
        }).collect(Collectors.toList());
        amountCalculationBo.setAmountCalculationItemBos(amountCalculationItemBos);
        return tjPackageService.commonDynamicBilling(amountCalculationBo);
    }

    @Override
    public TjTeamGroupVo getTjTeamGroupVoById(Long teamGroupId,Long regId,String healthyCheckStatus) {
        //查询分组信息
        TjTeamGroupVo teamGroupVo = tjTeamGroupMapper.selectVoById(teamGroupId);
        Assert.notNull(teamGroupVo,"根据分组id["+teamGroupId+"],未找到对应分组记录!");
        if(Objects.equals("1",teamGroupVo.getIsSyncProject())|| !Objects.equals(HealthyCheckTypeEnum.预约.getCode(), healthyCheckStatus)){
            //是否同步为否时  需要取groupHis记录中信息
            TjTeamGroupHistoryVo tjTeamGroupHistoryVo = tjTeamGroupHistoryMapper.selectVoOne(new LambdaQueryWrapper<TjTeamGroupHistory>()
                .eq(TjTeamGroupHistory::getRegId, regId));
            if(tjTeamGroupHistoryVo!=null){
                teamGroupVo.setGroupName(tjTeamGroupHistoryVo.getGroupName());
                teamGroupVo.setDutyStatus(tjTeamGroupHistoryVo.getDutyStatus());
                teamGroupVo.setStartAge(tjTeamGroupHistoryVo.getStartAge());
                teamGroupVo.setEndAge(tjTeamGroupHistoryVo.getEndAge());
                teamGroupVo.setPrice(tjTeamGroupHistoryVo.getPrice());
                teamGroupVo.setGroupPayType(tjTeamGroupHistoryVo.getGroupPayType());
                teamGroupVo.setAddPayType(tjTeamGroupHistoryVo.getAddPayType());
                teamGroupVo.setItemDiscount(tjTeamGroupHistoryVo.getItemDiscount());
                teamGroupVo.setAddDiscount(tjTeamGroupHistoryVo.getAddDiscount());
                teamGroupVo.setGroupType(tjTeamGroupHistoryVo.getGroupPayType());
            }
        }
        return teamGroupVo;
    }

    @Override
    public Long getPeTimes(TjRegPeTimesBo bo) {
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<TjRegister>()
            .eq(TjRegister::getCredentialNumber, bo.getCredentialNumber())
            .eq(TjRegister::getCredentialType,bo.getCredentialType())
        );
        return count+1;
    }

    @Override
    public Boolean batchReport(List<Long> regIds) {
        regIds.stream().forEach(regId->{
            //组装报到请求参数对象
            TjRegCombinAddBo bo = new TjRegCombinAddBo();
            bo.setBatchFlag("0");//设置批量标志
            bo.setRegisterId(regId);
            RegisterChangeService registerChangeService = registerChangeHolder.selectBuilder("2");
            registerChangeService.changeRegCombin(bo);
        });

        return true;
    }

    @Override
    public List<TjRegisterVo> getByIds(List<Long> regIdList) {
        return this.baseMapper.getByIds(regIdList);
    }

    @Override
    public TjRegisterVo getSingleInfo(TjRegisterSingleBo bo) {
        LambdaQueryWrapper<TjRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(bo.getId() != null,TjRegister::getId,bo.getId())
            .eq(StrUtil.isNotEmpty(bo.getHealthyCheckCode()),TjRegister::getHealthyCheckCode,bo.getHealthyCheckCode())
            .eq(TjRegister::getDelFlag,CommonConstants.NORMAL);
        TjRegisterVo vo = baseMapper.selectVoOne(wrapper);
        if(vo != null && StrUtil.isNotEmpty(vo.getUserImage())){
            OssClient ossClient = OssFactory.instance();
            vo.setUserImage(ossClient.getPrivateUrlWithoutSuffix(vo.getUserImage()));
        }
        return vo;
    }
}
