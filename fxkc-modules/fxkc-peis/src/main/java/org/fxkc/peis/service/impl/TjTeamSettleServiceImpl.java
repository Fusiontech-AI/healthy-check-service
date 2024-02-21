package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjRegCombinationProject;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.TjTeamTask;
import org.fxkc.peis.domain.bo.TjRegisterPageBo;
import org.fxkc.peis.domain.bo.TjTeamTaskDiscountSealBo;
import org.fxkc.peis.domain.vo.*;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjRegCombinationProjectMapper;
import org.fxkc.peis.mapper.TjRegisterMapper;
import org.fxkc.peis.mapper.TjTeamTaskMapper;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTeamSettleBo;
import org.fxkc.peis.domain.TjTeamSettle;
import org.fxkc.peis.mapper.TjTeamSettleMapper;
import org.fxkc.peis.service.ITjTeamSettleService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 体检单位结账信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
@RequiredArgsConstructor
@Service
public class TjTeamSettleServiceImpl implements ITjTeamSettleService {

    private final TjTeamSettleMapper baseMapper;
    private final TjTeamTaskMapper tjTeamTaskMapper;
    private final TjRegisterMapper tjRegisterMapper;
    private final TjRegCombinationProjectMapper tjRegCombinationProjectMapper;

    /**
     * 查询体检单位结账任务分组列表
     */
    @Override
    public TableDataInfo<TjTeamSettleTaskGroupVo> teamSettleTaskGroupList(TjTeamSettleBo bo, PageQuery pageQuery) {
        TjTeamTask tjTeamTask = tjTeamTaskMapper.selectById(bo.getTeamTaskId());
        bo.setHealthyCheckStatus(StrUtil.equals(tjTeamTask.getChargeType(),"1") ? "1" : StrUtil.equals(tjTeamTask.getChargeType(),"2") ? "0" : null);
        Page<TjTeamSettleTaskGroupVo> result = baseMapper.teamSettleTaskGroupList(pageQuery.build(),bo);
        TjTeamSettleTaskGroupVo tjTeamSettleTaskGroupVo = baseMapper.teamSettleTaskNoGroup(bo);
        if(ObjectUtil.isNotNull(tjTeamSettleTaskGroupVo)){
            List<TjTeamSettleTaskGroupVo> newRecords = Lists.newArrayList(result.getRecords());
            newRecords.add(tjTeamSettleTaskGroupVo);
            result.setRecords(newRecords);
            result.setTotal(result.getTotal() + 1L);
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检单位结账任务分组统计
     */
    @Override
    public TjTeamSettleTaskGroupStatisticsVo teamSettleTaskGroupStatistics(TjTeamSettleBo bo) {
        TjTeamTask tjTeamTask = tjTeamTaskMapper.selectById(bo.getTeamTaskId());
        bo.setHealthyCheckStatus(StrUtil.equals(tjTeamTask.getChargeType(),"1") ? "1" : StrUtil.equals(tjTeamTask.getChargeType(),"2") ? "0" : null);
        if(ObjectUtil.equal(bo.getTeamGroupId(),0L)){
            return MapstructUtils.convert(baseMapper.teamSettleTaskNoGroup(bo), TjTeamSettleTaskGroupStatisticsVo.class);
        }
        TjTeamSettleTaskGroupStatisticsVo vo = baseMapper.teamSettleTaskGroupStatistics(bo);
        if(ObjectUtil.isNull(bo.getTeamGroupId())){
            TjTeamSettleTaskGroupVo tjTeamSettleTaskGroupVo = baseMapper.teamSettleTaskNoGroup(bo);
            vo.setTeamReceiveAmount(NumberUtil.add(vo.getTeamReceiveAmount(),tjTeamSettleTaskGroupVo.getTeamReceiveAmount()));
            vo.setTotalPeople(vo.getTotalPeople() + tjTeamSettleTaskGroupVo.getTotalPeople());
            vo.setGroupAmount(NumberUtil.add(vo.getGroupAmount(),tjTeamSettleTaskGroupVo.getGroupAmount()));
        }
        return vo;
    }

    /**
     * 查询体检单位结账任务分组人员明细列表
     */
    @Override
    public TableDataInfo<TjRegisterPageVo> teamSettleTaskGroupDetailList(TjTeamSettleBo bo, PageQuery pageQuery) {
        TjRegisterPageBo tjRegisterPageBo = new TjRegisterPageBo();
        tjRegisterPageBo.setTeamId(bo.getTeamId());
        tjRegisterPageBo.setTaskId(bo.getTeamTaskId());
        tjRegisterPageBo.setTeamGroupId(ObjectUtil.isNotNull(bo.getTeamGroupId()) ? bo.getTeamGroupId() : 0L);
        Page<TjRegisterPageVo> result = tjRegisterMapper.selectPage(tjRegisterPageBo, pageQuery.build());
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检单位结账信息
     */
    @Override
    public TjTeamSettleVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询体检单位结账人员明细列表
     */
    @Override
    public TableDataInfo<TjRegisterPageVo> teamSettleDetailList(Long id, TjTeamSettleBo bo, PageQuery pageQuery) {
        TjRegisterPageBo tjRegisterPageBo = new TjRegisterPageBo();
        tjRegisterPageBo.setTeamSettleId(id);
        tjRegisterPageBo.setTeamId(bo.getTeamId());
        tjRegisterPageBo.setTaskId(bo.getTeamTaskId());
        Page<TjRegisterPageVo> result = tjRegisterMapper.selectPage(tjRegisterPageBo, pageQuery.build());
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检单位结账信息列表
     */
    @Override
    public TableDataInfo<TjTeamSettleVo> queryPageList(TjTeamSettleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTeamSettle> lqw = buildQueryWrapper(bo);
        Page<TjTeamSettleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询体检单位结账信息列表
     */
    @Override
    public List<TjTeamSettleVo> queryList(TjTeamSettleBo bo) {
        LambdaQueryWrapper<TjTeamSettle> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTeamSettle> buildQueryWrapper(TjTeamSettleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjTeamSettle> lqw = Wrappers.lambdaQuery();
        lqw.eq(ObjectUtil.isNotNull(bo.getTeamId()), TjTeamSettle::getTeamId, bo.getTeamId());
        lqw.eq(ObjectUtil.isNotNull(bo.getTeamTaskId()), TjTeamSettle::getTeamTaskId, bo.getTeamTaskId());
        lqw.eq(TjTeamSettle::getDelFlag,CommonConstants.NORMAL);
        return lqw;
    }

    /**
     * 新增体检单位结账信息
     */
    @Override
    public Boolean insertByBo(TjTeamSettleBo bo) {
        validEntityBeforeSave(bo);
        TjTeamSettle add = MapstructUtils.convert(bo, TjTeamSettle.class);
        add.setChargeNumber(DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_MS_PATTERN));
        add.setSettleOfficer(LoginHelper.getLoginUser().getUserId());
        return baseMapper.insert(add) > 0;
    }

    /**
     * 体检单位结账开票
     */
    @Override
    public Boolean teamInvoice(TjTeamSettleBo bo) {
        TjTeamSettle update = new TjTeamSettle();
        update.setPrintInvoice("1");
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,List.of(bo.getIds()))) > 0;
    }

    /**
     * 体检单位结账发票作废
     */
    @Override
    public Boolean teamInvalidInvoice(TjTeamSettleBo bo) {
        TjTeamSettle update = new TjTeamSettle();
        update.setPrintInvoice("0");
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,List.of(bo.getIds()))) > 0;
    }

    /**
     * 体检单位结账作废
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean teamInvalidSettle(TjTeamSettleBo bo) {
        validEntityBeforeSave(bo);
        List<Long> ids = List.of(bo.getIds());
        List<TjTeamSettle> tjTeamSettleList = baseMapper.selectList(new LambdaQueryWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids));
        long notNormalCount = tjTeamSettleList.stream().filter(f -> !StrUtil.equals(f.getStatus(),CommonConstants.NORMAL)).count();
        if(notNormalCount > 0){
            throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_STATUS_REFRESH);
        }
        List<Long> regIds = tjRegisterMapper.selectObjs(new LambdaQueryWrapper<TjRegister>()
            .select(TjRegister::getId)
            .in(TjRegister::getTeamSettleId,ids)
            .eq(TjRegister::getDelFlag,CommonConstants.NORMAL));
        if(CollUtil.isNotEmpty(regIds)){
            tjRegCombinationProjectMapper.update(TjRegCombinationProject.builder().payStatus("0").build(),
                new LambdaUpdateWrapper<TjRegCombinationProject>()
                    .in(TjRegCombinationProject::getRegisterId,regIds)
                    .eq(TjRegCombinationProject::getPayMode,"1")
                    .eq(TjRegCombinationProject::getDelFlag,CommonConstants.NORMAL));
        }
        tjRegisterMapper.updateTjRegisterTeamSettleNull(ids);
        TjTeamSettle update = new TjTeamSettle();
        update.setStatus("2");
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids)) > 0;
    }

    /**
     * 获取体检单位结账金额统计
     */
    @Override
    public TjTeamSettleAmountStatisticsVo teamSettleAmountStatistics(TjTeamSettleBo bo) {
        TjTeamSettleAmountStatisticsVo vo = new TjTeamSettleAmountStatisticsVo();
        BigDecimal receivedAmount = baseMapper.selectObjs(new LambdaQueryWrapper<TjTeamSettle>()
            .select(TjTeamSettle::getReceivedAmount)
            .eq(TjTeamSettle::getTeamId,bo.getTeamId())
            .eq(TjTeamSettle::getTeamTaskId,bo.getTeamTaskId())
            .eq(TjTeamSettle::getDelFlag,CommonConstants.NORMAL)
            .eq(TjTeamSettle::getStatus,CommonConstants.NORMAL))
            .stream().map(m -> NumberUtil.toBigDecimal((Number) m))
            .reduce(BigDecimal.ZERO,BigDecimal::add);
        vo.setReceivedAmount(receivedAmount);
        vo.setSettledAmount(baseMapper.teamSettledAmount(bo));
        vo.setBalance(NumberUtil.sub(vo.getReceivedAmount(),vo.getSettledAmount()));
        return vo;
    }

    /**
     * 体检单位结账任务折扣
     */
    @Override
    public Boolean teamTaskDiscount(TjTeamTaskDiscountSealBo bo) {
        TjTeamSettleBo tjTeamSettleBo = new TjTeamSettleBo();
        tjTeamSettleBo.setTeamTaskId(bo.getId());
        validEntityBeforeSave(tjTeamSettleBo);
        TjTeamTask tjTeamTask = MapstructUtils.convert(bo, TjTeamTask.class);
        return tjTeamTaskMapper.updateById(tjTeamTask) > 0;
    }

    /**
     * 体检单位结账封账
     */
    @Override
    public Boolean teamSettleSeal(TjTeamTaskDiscountSealBo bo) {
        TjTeamTask tjTeamTask = MapstructUtils.convert(bo, TjTeamTask.class);
        tjTeamTask.setIsSeal("1");
        return tjTeamTaskMapper.updateById(tjTeamTask) > 0;
    }

    /**
     * 体检单位结账解除封账
     */
    @Override
    public Boolean teamSettleUnseal(TjTeamTaskDiscountSealBo bo) {
        TjTeamTask tjTeamTask = MapstructUtils.convert(bo, TjTeamTask.class);
        tjTeamTask.setIsSeal("0");
        return tjTeamTaskMapper.updateById(tjTeamTask) > 0;
    }

    /**
     * 体检单位结账审核通过
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean teamSettleCheckPass(TjTeamSettleBo bo) {
        validEntityBeforeSave(bo);
        validEntityBeforeCheck(bo);
        List<Long> ids = List.of(bo.getIds());
        List<TjTeamSettle> tjTeamSettleList = baseMapper.selectList(new LambdaQueryWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids));
        long notNormalCount = tjTeamSettleList.stream().filter(f -> !StrUtil.equals(f.getCheckStatus(),CommonConstants.NORMAL)).count();
        if(notNormalCount > 0){
            throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_CHECKED_REFRESH);
        }
        Date date = DateUtil.date();
        List<TjRegister> tjRegisterList = tjRegisterMapper.selectList(new LambdaQueryWrapper<TjRegister>()
            .eq(TjRegister::getTeamId,bo.getTeamId())
            .eq(TjRegister::getTaskId,bo.getTeamTaskId())
            .isNull(TjRegister::getTeamSettleId)
            .eq(TjRegister::getHealthyCheckStatus,"5")
            .eq(TjRegister::getDelFlag,CommonConstants.NORMAL));
        List<TjRegister> updateTjRegisterList = Lists.newArrayList();
        tjTeamSettleList.stream().forEach(f ->
            updateTjRegisterList.addAll(tjRegisterList.stream().filter(ff -> {
                if(ObjectUtil.isNotNull(ff.getTeamSettleId())){
                    return false;
                }
                BigDecimal balance = NumberUtil.sub(f.getReceivedAmount(),ff.getTeamAmount());
                f.setReceivedAmount(balance);
                return ObjectUtil.compare(f.getReceivedAmount(),BigDecimal.ZERO) >= 0;
            }).map(fff -> {
                TjRegister tjRegister = new TjRegister();
                tjRegister.setTeamChargeStatus(CommonConstants.NORMAL);
                tjRegister.setTeamSettleId(f.getId());
                tjRegister.setTeamSettleTime(date);
                tjRegister.setId(fff.getId());
                return tjRegister;
            }).collect(Collectors.toList()))
        );
        if(CollUtil.isNotEmpty(updateTjRegisterList)){
            tjRegisterMapper.updateBatchById(updateTjRegisterList);
            List<Long> regIds = updateTjRegisterList.stream().map(TjRegister::getId).collect(Collectors.toList());
            tjRegCombinationProjectMapper.update(TjRegCombinationProject.builder().payStatus("1").build(),
                new LambdaUpdateWrapper<TjRegCombinationProject>()
                    .in(TjRegCombinationProject::getRegisterId,regIds)
                    .eq(TjRegCombinationProject::getPayMode,"1")
                    .eq(TjRegCombinationProject::getDelFlag,CommonConstants.NORMAL));
        }
        TjTeamSettle update = new TjTeamSettle();
        update.setAuditor(LoginHelper.getLoginUser().getUserId());
        update.setCheckStatus("1");
        update.setCheckTime(date);
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,ids)) > 0;
    }

    /**
     * 体检单位结账审核驳回
     */
    @Override
    public Boolean teamSettleCheckReject(TjTeamSettleBo bo) {
        validEntityBeforeSave(bo);
        validEntityBeforeCheck(bo);
        TjTeamSettle update = new TjTeamSettle();
        update.setAuditor(LoginHelper.getLoginUser().getUserId());
        update.setCheckStatus("2");
        update.setCheckTime(DateUtil.date());
        return baseMapper.update(update,new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,List.of(bo.getIds()))) > 0;
    }

    /**
     * 结账审核前的数据校验
     */
    private void validEntityBeforeCheck(TjTeamSettleBo bo){
        List<TjTeamSettle> tjTeamSettleList = baseMapper.selectList(new LambdaUpdateWrapper<TjTeamSettle>().in(TjTeamSettle::getId,List.of(bo.getIds())));
        long notNormalStatusCount = tjTeamSettleList.stream().filter(f -> !StrUtil.equals(f.getStatus(),CommonConstants.NORMAL)).count();
        if(notNormalStatusCount > 0){
            throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_STATUS_REFRESH);
        }
        long notNormalCheckStatusCount = tjTeamSettleList.stream().filter(f -> !StrUtil.equals(f.getCheckStatus(),CommonConstants.NORMAL)).count();
        if(notNormalCheckStatusCount > 0){
            throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_CHECKSTATUS_REFRESH);
        }
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamSettleBo bo){
        TjTeamTask tjTeamTask = tjTeamTaskMapper.selectById(bo.getTeamTaskId());
        if(StrUtil.equals(tjTeamTask.getIsSeal(),"1")){
            throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_SEAL);
        }
    }

    /**
     * 批量删除体检单位结账信息
     */
    @Override
    public Boolean deleteWithValidByIds(TjTeamSettleBo bo, Boolean isValid) {
        List<Long> ids = List.of(bo.getIds());
        if(isValid){
            validEntityBeforeSave(bo);
            Long count = baseMapper.selectCount(new LambdaUpdateWrapper<TjTeamSettle>()
                .in(TjTeamSettle::getId,ids)
                .eq(TjTeamSettle::getStatus, CommonConstants.NORMAL));
            if(count > 0){
                throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMSETTLE_FIRST_VOID);
            }
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
