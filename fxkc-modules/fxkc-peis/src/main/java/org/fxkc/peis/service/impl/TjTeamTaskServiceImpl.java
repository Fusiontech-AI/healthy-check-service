package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.domain.TjTeamTask;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.TjTeamGroupVo;
import org.fxkc.peis.domain.vo.TjTeamTaskDetailVo;
import org.fxkc.peis.domain.vo.TjTeamTaskVo;
import org.fxkc.peis.domain.vo.VerifyMessageVo;
import org.fxkc.peis.enums.GroupTypeEnum;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.enums.PhysicalTypeEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjRegisterMapper;
import org.fxkc.peis.mapper.TjTeamGroupMapper;
import org.fxkc.peis.mapper.TjTeamTaskMapper;
import org.fxkc.peis.service.ITjTeamInfoService;
import org.fxkc.peis.service.ITjTeamTaskService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 团检任务管理Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@RequiredArgsConstructor
@Service
public class TjTeamTaskServiceImpl extends ServiceImpl<TjTeamTaskMapper, TjTeamTask> implements ITjTeamTaskService {

    private final TjTeamGroupMapper tjTeamGroupMapper;

    private final ITjTeamInfoService iTjTeamInfoService;

    private final TjRegisterMapper tjRegisterMapper;

    /**
     * 查询团检任务管理
     */
    @Override
    public TjTeamTaskDetailVo queryById(Long id){
        TjTeamTask teamTask = baseMapper.selectById(id);
        TjTeamTaskDetailVo vo = MapstructUtils.convert(teamTask, TjTeamTaskDetailVo.class);
        List<TjTeamGroupVo> groupVoList = tjTeamGroupMapper.selectVoList(Wrappers.lambdaQuery(TjTeamGroup.class)
            .eq(TjTeamGroup::getTaskId, id));
        vo.setGroupList(groupVoList);
        return vo;
    }

    /**
     * 查询团检任务管理列表
     */
    @Override
    public TableDataInfo<TjTeamTaskVo> queryPageList(TjTeamTaskQueryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTeamTask> lqw = buildQueryWrapper(bo);
        Page<TjTeamTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询团检任务管理列表
     */
    @Override
    public List<TjTeamTaskVo> queryList(TjTeamTaskQueryBo bo) {
        LambdaQueryWrapper<TjTeamTask> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTeamTask> buildQueryWrapper(TjTeamTaskQueryBo bo) {
        LambdaQueryWrapper<TjTeamTask> lqw = Wrappers.lambdaQuery();
        lqw.eq(Objects.nonNull(bo.getTeamId()), TjTeamTask::getTeamId, bo.getTeamId())
            .like(StrUtil.isNotBlank(bo.getTaskName()), TjTeamTask::getTaskName, bo.getTaskName())
            .ge(Objects.nonNull(bo.getSignBeginDate()), TjTeamTask::getSignDate, bo.getSignBeginDate())
            .le(Objects.nonNull(bo.getSignEndDate()), TjTeamTask::getSignDate, bo.getSignEndDate());
        return lqw;
    }

    /**
     * 新增团检任务管理
     */
    @Override
    public List<TjTeamGroupVo> insertByBo(TjTeamTaskBo bo) {
        validEntityBeforeSave(bo, Boolean.TRUE);
        TjTeamTask add = MapstructUtils.convert(bo, TjTeamTask.class);
        boolean flag = baseMapper.insert(add) > 0;
        List<TjTeamGroup> groupList = MapstructUtils.convert(bo.getGroupList(), TjTeamGroup.class);
        String teamName = iTjTeamInfoService.selectTeamNameById(add.getTeamId());
        groupList.forEach(k -> k.setTaskId(add.getId()).setTaskName(add.getTaskName())
            .setTeamId(add.getTeamId()).setTeamName(teamName));
        tjTeamGroupMapper.insertBatch(groupList);
        return tjTeamGroupMapper.selectVoList(Wrappers.lambdaQuery(TjTeamGroup.class)
            .eq(TjTeamGroup::getTaskId, add.getId())
            .eq(TjTeamGroup::getGroupType, GroupTypeEnum.ITEM.getCode()));
    }

    /**
     * 修改团检任务管理
     */
    @Override
    public List<TjTeamGroupVo> updateByBo(TjTeamTaskBo bo) {
        validEntityBeforeSave(bo, Boolean.FALSE);
        TjTeamTask update = MapstructUtils.convert(bo, TjTeamTask.class);
        baseMapper.updateById(update);
        List<TjTeamGroup> groupList = MapstructUtils.convert(bo.getGroupList(), TjTeamGroup.class);
        String teamName = iTjTeamInfoService.selectTeamNameById(update.getTeamId());
        groupList.forEach(k -> k.setTaskId(update.getId()).setTaskName(update.getTaskName())
            .setTeamId(update.getTeamId()).setTeamName(teamName));
        //修改任务分组记录分组人员分组信息
        List<TjTeamGroup> recordList = StreamUtils.filter(groupList, e -> Objects.nonNull(e.getId()));



        tjTeamGroupMapper.insertOrUpdateBatch(groupList);
        return tjTeamGroupMapper.selectVoList(Wrappers.lambdaQuery(TjTeamGroup.class)
            .eq(TjTeamGroup::getTaskId, update.getId())
            .eq(TjTeamGroup::getGroupType, GroupTypeEnum.ITEM.getCode()));
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamTaskBo bo, Boolean isAdd){
        //TODO 做一些数据校验,如唯一约束
        //同单位任务名称校验
        if(isAdd) {
            long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjTeamTask.class)
                .eq(TjTeamTask::getTaskName, bo.getTaskName())
                .eq(TjTeamTask::getTeamId, bo.getTeamId()));
            if(count > 0) {
                throw new PeisException(ErrorCodeConstants.PEIS_TASKNAME_ISEXIST, bo.getTaskName());
            }
        }
        //同任务相同分组名称校验
        Set<String> set = CollUtil.newHashSet();
        Set<TjTeamGroupBo> groupNameSet = bo.getGroupList().stream().filter(e -> !set.add(e.getGroupName())).collect(Collectors.toSet());
        if(CollUtil.isNotEmpty(groupNameSet)) {
            throw new PeisException(ErrorCodeConstants.PEIS_GROUPNAME_REPEAT,
                groupNameSet.stream().map(TjTeamGroupBo::getGroupName).collect(Collectors.joining(StrUtil.COMMA)));
        }
        //职业病在岗状态校验
        boolean flag = (Objects.equals(bo.getPhysicalType(), PhysicalTypeEnum.ZYJKTJ.name()) ||
            Objects.equals(bo.getPhysicalType(), PhysicalTypeEnum.FSTJ.name())) &&
            bo.getGroupList().stream().anyMatch(e -> Objects.isNull(e.getDutyStatus()));
        Map<String, Object> enumMap = EnumUtil.getNameFieldMap(PhysicalTypeEnum.class, "desc");
        if(flag) {
            throw new PeisException(ErrorCodeConstants.PEIS_DUTY_STATUS_NOT_EMPTY, enumMap.get(bo.getPhysicalType()));
        }
        List<TjTeamGroupBo> boList = bo.getGroupList().stream().filter(e -> Objects.equals(e.getGroupType(), GroupTypeEnum.ITEM.getCode()) ||
            Objects.equals(e.getGroupType(), GroupTypeEnum.PRICE.getCode())).toList();
        //项目分组、金额加项折扣校验
        if(boList.stream().anyMatch(e -> Objects.isNull(e.getAddDiscount()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_ITEM_DISCOUNT_NOT_EMPTY);
        }
        //项目分组、金额加项支付方式校验
        if(boList.stream().anyMatch(e -> StrUtil.isBlank(e.getAddPayType()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_ADD_PAY_TYPE_NOT_EMPTY);
        }
        //金额分组金额校验
        if(bo.getGroupList().stream().anyMatch(e -> (Objects.isNull(e.getPrice()) || e.getPrice().compareTo(BigDecimal.ZERO) <= 0) &&
            Objects.equals(e.getGroupType(), GroupTypeEnum.PRICE.getCode()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_PRICE_NOT_EMPTY);
        }
    }

    /**
     * 批量删除团检任务管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public VerifyMessageVo verifyGroupData(List<VerifyGroupBo> list) {
        VerifyMessageVo vo = new VerifyMessageVo();
        List<TjTeamGroupBo> boList = MapstructUtils.convert(list, TjTeamGroupBo.class);
        validEntityBeforeSave(new TjTeamTaskBo().setGroupList(boList), Boolean.FALSE);
        List<Long> idList = StreamUtils.toList(list, VerifyGroupBo::getId);
        List<TjTeamGroup> groupList = tjTeamGroupMapper.selectBatchIds(idList);
        List<TjRegister> tjRegisterList = tjRegisterMapper.selectList(Wrappers.lambdaQuery(TjRegister.class)
            .in(TjRegister::getTeamGroupId, idList)
            .eq(TjRegister::getHealthyCheckStatus, HealthyCheckTypeEnum.预约.getCode()));
        Map<Long, Long> tjRegisterMap = tjRegisterList.stream().collect(Collectors.groupingBy(
            TjRegister::getTeamGroupId, Collectors.counting()));
        StringBuffer buffer = new StringBuffer();
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(k -> groupList.forEach(s -> {
            if(Objects.equals(k.getId(), s.getId())) {
                boolean isPrompt = Boolean.FALSE;
                Long count = tjRegisterMap.getOrDefault(k.getId(), 0L);
                buffer.append(index.getAndIncrement()).append(StrUtil.DOT).append("【").append(k.getGroupName()).append("】");
                if(k.getItemDiscount().compareTo(s.getItemDiscount()) != 0) {
                    isPrompt = Boolean.TRUE;
                    buffer.append("【折扣调整为<span style='color:red'>").append(k.getItemDiscount())
                        .append("</span>】、");
                }
                if(ObjectUtil.notEqual(k.getGroupType(), GroupTypeEnum.DISCOUNT.getCode())) {
                    if(k.getAddDiscount().compareTo(s.getAddDiscount()) != 0) {
                        isPrompt = Boolean.TRUE;
                        buffer.append("【加项折扣调整为<span style='color:red'>").append(k.getAddDiscount())
                            .append("</span>】、");
                    }
                    if(ObjectUtil.notEqual(k.getAddPayType(), s.getAddPayType())) {
                        isPrompt = Boolean.TRUE;
                        buffer.append("【加项支付方式调整为<span style='color:red'>")
                            .append(Objects.equals("0", k.getAddPayType()) ? "个人" : "单位")
                            .append("</span>】、");
                    }
                }
                if(ObjectUtil.notEqual(k.getGroupPayType(), s.getGroupPayType())) {
                    isPrompt = Boolean.TRUE;
                    buffer.append("【分组内支付方式调整为<span style='color:red'>")
                        .append(Objects.equals("0", k.getGroupPayType()) ? "个人" : "单位")
                        .append("</span>】、");
                }
                if(ObjectUtil.equal(k.getGroupType(), GroupTypeEnum.PRICE.getCode()) &&
                    k.getPrice().compareTo(s.getPrice()) != 0) {
                    isPrompt = Boolean.TRUE;
                    BigDecimal difference = k.getPrice().subtract(s.getPrice());
                    buffer.append("【金额调整为<span style='color:red'>").append(k.getPrice())
                        .append("</span>，较原分组").append(difference.signum() > 0 ? "增加" : "减少").append("<span style='color:red'>")
                        .append(difference.abs()).append("</span>").append(count > 0 ? "，同步分组内预约状态人员后，总差额<span style='color:red'>"
                        .concat(difference.abs().multiply(new BigDecimal(count)).toPlainString()).concat("</span>元】、") : "】、");
                }
                if(!isPrompt && ObjectUtil.notEqual(k.getIsSyncProject(), s.getIsSyncProject())) {
                    buffer.append(ObjectUtil.equal("1", k.getIsSyncProject()) ?
                        "确认后将此分组信息同步本至分组内预约状态的未检人员<span style='color:red'>".concat(String.valueOf(count))
                            .concat("</span>人。") : "确认后新入组人员将按照此规则执行。").append("</br>");
                }else if(isPrompt) {
                    buffer.deleteCharAt(buffer.length() - 1).append("，")
                        .append(ObjectUtil.equal("1", k.getIsSyncProject()) ?
                            "确认后将同步本分组内预约状态的未检人员<span style='color:red'>".concat(String.valueOf(count))
                                .concat("</span>人。") : "新入组人员将按照此规则执行。").append("</br>");
                }
            }
        }));
        if(buffer.length() > 0) {
            vo.setPromptMessage(buffer.toString());
            vo.setIsPrompt(Boolean.TRUE);
        }
        return vo;
    }

    @Override
    public VerifyMessageVo verifyGroupPackageData(List<VerifyGroupPackageBo> list) {
        VerifyMessageVo vo = new VerifyMessageVo();
        List<Long> idList = StreamUtils.toList(list, VerifyGroupPackageBo::getId);
        List<TjTeamGroup> groupList = tjTeamGroupMapper.selectBatchIds(idList);
        List<TjRegister> tjRegisterList = tjRegisterMapper.selectList(Wrappers.lambdaQuery(TjRegister.class)
            .in(TjRegister::getTeamGroupId, idList)
            .eq(TjRegister::getHealthyCheckStatus, HealthyCheckTypeEnum.预约.getCode()));
        Map<Long, Long> tjRegisterMap = tjRegisterList.stream().collect(Collectors.groupingBy(
            TjRegister::getTeamGroupId, Collectors.counting()));
        StringBuffer buffer = new StringBuffer();
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(k -> groupList.forEach(s -> {
            if(Objects.equals(k.getId(), s.getId())) {
                boolean isPrompt = Boolean.FALSE;
                Long count = tjRegisterMap.getOrDefault(k.getId(), 0L);
                buffer.append(index.getAndIncrement()).append(StrUtil.DOT).append("【").append(k.getGroupName()).append("】");
                if(k.getItemDiscount().compareTo(s.getItemDiscount()) != 0) {
                    isPrompt = Boolean.TRUE;
                    buffer.append("【折扣调整为<span style='color:red'>").append(k.getItemDiscount())
                        .append("</span>】、");
                }
                if(k.getAddDiscount().compareTo(s.getAddDiscount()) != 0) {
                    isPrompt = Boolean.TRUE;
                    buffer.append("【加项折扣调整为<span style='color:red'>").append(k.getAddDiscount())
                        .append("</span>】、");
                }
                if(k.getActualPrice().compareTo(s.getActualPrice()) != 0) {
                    isPrompt = Boolean.TRUE;
                    BigDecimal difference = k.getActualPrice().subtract(s.getActualPrice());
                    buffer.append("【实际价格调整为<span style='color:red'>").append(k.getActualPrice())
                        .append("</span>，较原分组").append(difference.signum() > 0 ? "增加" : "减少").append("<span style='color:red'>")
                        .append(difference.abs()).append("</span>").append(count > 0 ? "，同步分组内预约状态人员后，总差额<span style='color:red'>"
                        .concat(difference.abs().multiply(new BigDecimal(count)).toPlainString()).concat("</span>元】、") : "】、");
                }
                if(!isPrompt && ObjectUtil.notEqual(k.getIsSyncProject(), s.getIsSyncProject())) {
                    buffer.append(ObjectUtil.equal("1", k.getIsSyncProject()) ?
                        "确认后将此分组信息同步本至分组内预约状态的未检人员<span style='color:red'>".concat(String.valueOf(count))
                            .concat("</span>人。") : "确认后新入组人员将按照此规则执行。").append("</br>");
                }else if(isPrompt) {
                    buffer.deleteCharAt(buffer.length() - 1).append("，")
                        .append(ObjectUtil.equal("1", k.getIsSyncProject()) ?
                            "确认后将同步本分组内预约状态的未检人员<span style='color:red'>".concat(String.valueOf(count))
                                .concat("</span>人。") : "新入组人员将按照此规则执行。").append("</br>");
                }
            }
        }));
        if(buffer.length() > 0) {
            vo.setPromptMessage(buffer.toString());
            vo.setIsPrompt(Boolean.TRUE);
        }
        return vo;
    }
}
