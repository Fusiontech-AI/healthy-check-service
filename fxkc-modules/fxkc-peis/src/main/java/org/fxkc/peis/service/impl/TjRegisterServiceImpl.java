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
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.oss.core.OssClient;
import org.fxkc.common.oss.factory.OssFactory;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.*;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.domain.vo.TjRegisterPageVo;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.enums.CheckStatusEnum;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.enums.RegisterStatusEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.*;
import org.fxkc.peis.register.insert.RegisterInsertHolder;
import org.fxkc.peis.register.insert.RegisterInsertService;
import org.fxkc.peis.service.ITjRegisterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    private final TjRegisterZybMapper tjRegisterZybMapper;

    private final TjRegCombinationProjectMapper tjRegCombinationProjectMapper;

    private final TjRegBasicProjectMapper tjRegBasicProjectMapper;

    private final TjCombinationProjectInfoMapper tjCombinationProjectInfoMapper;

    private final TjArchivesMapper tjArchivesMapper;

    /**
     * 查询体检人员登记信息
     */
    @Override
    public TjRegisterVo queryById(Long id){
        return baseMapper.selectVoById(id);
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
        //不应在这里修改体检人员照片信息
        update.setUserImage(null);

        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjRegister entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除体检人员登记信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
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
        List<TjRegCombinItemBo> combinItemBos = bo.getTjRegCombinItemBos();
        List<TjRegCombinationProject> tjRegCombinationProjects = tjRegCombinationProjectMapper.selectList(new LambdaQueryWrapper<TjRegCombinationProject>()
            .eq(TjRegCombinationProject::getRegisterId, bo.getRegisterId()));
        if(CollUtil.isNotEmpty(tjRegCombinationProjects)){
            //筛选出需要删除的记录信息
            List<TjRegCombinItemBo> comBinIds = combinItemBos.stream().filter(m -> Objects.nonNull(m.getId())).collect(Collectors.toList());
            List<TjRegCombinationProject> deleteItems = tjRegCombinationProjects.stream().filter(m -> !comBinIds.contains(m.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(deleteItems)){
                //待删除记录中，存在项目己检查的 无法删除 (后面还要除去 不显示的项目记录)
                if(deleteItems.stream().anyMatch(m -> Objects.equals(CheckStatusEnum.已检查.getCode(), m.getCheckStatus()))){
                   throw new RuntimeException("已检查的项目无法删除!");
                }

                //删除相关记录 并删除管理的子项记录
                tjRegCombinationProjectMapper.deleteBatchIds(deleteItems);
                tjRegBasicProjectMapper.delete(new LambdaQueryWrapper<TjRegBasicProject>()
                    .in(TjRegBasicProject::getRegItemId,deleteItems.stream().map(m->m.getId()).collect(Collectors.toList())));
            }

        }else{
            log.info("当前登记id{},为第一次添加项目信息！",bo.getRegisterId());
        }

        //筛选出需要变更的记录信息 这里做价格的更新
        List<TjRegCombinItemBo> updateItems = combinItemBos.stream().filter(m -> Objects.nonNull(m.getId())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(updateItems)){
            List<TjRegCombinationProject> collect = updateItems.stream().map(m -> {
                TjRegCombinationProject build = TjRegCombinationProject.builder()
                    .id(m.getId())
                    .standardAmount(m.getStandardAmount())
                    .receivableAmount(m.getReceivableAmount())
                    .discount(m.getDiscount()).build();
                return build;
            }).collect(Collectors.toList());
            tjRegCombinationProjectMapper.updateBatchById(collect);
        }

        //筛选出需要新增的记录信息
        List<TjRegCombinItemBo> addItems = combinItemBos.stream().filter(m -> Objects.isNull(m.getId())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(addItems)){
            List<TjRegCombinationProject> combinationProjects = MapstructUtils.convert(addItems, TjRegCombinationProject.class);
            combinationProjects.stream().forEach(m->{
                m.setRegisterId(bo.getRegisterId());
            });
            //这里需要针对于项目无需显示的项目信息 默认赋值已检查
            tjRegCombinationProjectMapper.insertBatch(combinationProjects);
            //需要根据组合项目id 关联查询出对应的基础项目信息 并插入
            List<TjCombinationProjectInfo> tjCombinationProjectInfos = tjCombinationProjectInfoMapper.selectList(new LambdaQueryWrapper<TjCombinationProjectInfo>()
                .in(TjCombinationProjectInfo::getCombinProjectId, combinationProjects.stream().map(m -> m.getCombinationProjectId()).collect(Collectors.toList())));

            if(CollUtil.isNotEmpty(tjCombinationProjectInfos)){
                combinationProjects.stream().forEach(m->{
                    List<TjCombinationProjectInfo> infos = tjCombinationProjectInfos.stream().filter(f -> Objects.equals(f.getCombinProjectId(), m.getCombinationProjectId())).collect(Collectors.toList());
                    List<TjRegBasicProject> tjRegBasicProjectList = infos.stream().map(f -> {
                        TjRegBasicProject build = TjRegBasicProject.builder()
                            .regId(bo.getRegisterId())
                            .regItemId(m.getId())
                            .combinationProjectId(m.getCombinationProjectId())
                            .basicProjectId(f.getBasicProjectId()).build();
                        return build;
                    }).collect(Collectors.toList());
                    tjRegBasicProjectMapper.insertBatch(tjRegBasicProjectList);
                });

            }

            }

        return true;
    }

    @Override
    public Boolean changeRegReplaceInfo(TjRegReplaceInfoBo bo) {
        //先查询已有记录 是否为替检过状态
        TjRegister tjRegister = baseMapper.selectById(bo.getRegisterId());
        Assert.notNull(tjRegister,"根据登记id["+bo.getRegisterId()+"],未找到登录记录!");
        TjRegister updateEntity = new TjRegister();
        updateEntity.setReplaceFlag("0");
        updateEntity.setId(bo.getRegisterId());
        //不等于0是的时候 初次将原体检人信息存入 replace相关字段。
        if(!Objects.equals("0",tjRegister.getReplaceFlag())){
            updateEntity.setReplaceName(tjRegister.getName());
            updateEntity.setReplaceGender(tjRegister.getGender());
            updateEntity.setReplaceCredentialType(tjRegister.getCredentialType());
            updateEntity.setReplaceCredentialNumber(tjRegister.getReplaceCredentialNumber());
            updateEntity.setReplaceAge(tjRegister.getAge());
            updateEntity.setReplaceBirthday(tjRegister.getBirthday());
        }
        updateEntity.setName(bo.getReplaceName());
        updateEntity.setGender(bo.getReplaceGender());
        updateEntity.setCredentialType(bo.getReplaceCredentialType());
        updateEntity.setCredentialNumber(bo.getReplaceCredentialNumber());
        updateEntity.setAge(bo.getReplaceAge());
        updateEntity.setBirthday(bo.getReplaceBirthday());
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
        tjRegCombinationProject.setCheckDoctor(bo.getCheckDoctor());
        tjRegCombinationProject.setCheckDoctorName(bo.getCheckDoctorName());
        tjRegCombinationProject.setCheckTime(bo.getCheckTime());
        tjRegCombinationProject.setCheckStatus(StringUtils.isEmpty(bo.getCheckStatus())
            ? "1" : bo.getCheckStatus());//已检查
        tjRegCombinationProjectMapper.updateById(tjRegCombinationProject);

        //这里需要更改登记记录允许总检状态  以及对诊断明细相关记录的保存
        return null;
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
            baseMapper.update(TjRegister.builder().recordCode(finalTjArchivesData.getRecordCode()).build(),
                Wrappers.lambdaUpdate(TjRegister.class)
                    .eq(TjRegister::getRecordCode, k.getRecordCode()));
            tjArchivesMapper.delete(Wrappers.lambdaQuery(TjArchives.class)
                .eq(TjArchives::getArchivesNo, k.getRecordCode()));
        });
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
